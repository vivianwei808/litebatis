package org.wing4j.litebatis.executor;

import lombok.extern.slf4j.Slf4j;
import org.wing4j.litebatis.cache.CacheKey;
import org.wing4j.litebatis.cache.impl.PerpetualCache;
import org.wing4j.litebatis.exception.ExecutorException;
import org.wing4j.litebatis.mapping.*;
import org.wing4j.litebatis.Configuration;
import org.wing4j.litebatis.reflection.MetaObject;
import org.wing4j.litebatis.session.LocalCacheScope;
import org.wing4j.litebatis.session.ResultHandler;
import org.wing4j.litebatis.session.RowBounds;
import org.wing4j.litebatis.transaction.Transaction;
import org.wing4j.litebatis.type.TypeHandlerRegistry;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Slf4j
public abstract class BaseExecutor implements Executor {
    /**
     * 绑定的事务
     */
    protected Transaction transaction;
    /**
     * 真实的执行器
     */
    protected Executor wrapper;

//    protected ConcurrentLinkedQueue<DeferredLoad> deferredLoads;
    /**
     * 本地缓存（一级缓存）
     */
    protected PerpetualCache localCache;
    protected PerpetualCache localOutputParameterCache;
    /**
     * 配置对象
     */
    protected Configuration configuration;
    /**
     * 查询栈深度
     */
    protected int queryStack = 0;
    /**
     * 是否关闭
     */
    boolean closed;

    protected BaseExecutor(Configuration configuration, Transaction transaction) {
        this.transaction = transaction;
//        this.deferredLoads = new ConcurrentLinkedQueue<DeferredLoad>();
        this.localCache = new PerpetualCache("LocalCache");
        this.localOutputParameterCache = new PerpetualCache("LocalOutputParameterCache");
        this.closed = false;
        this.configuration = configuration;
        this.wrapper = this;
    }

    @Override
    public Transaction getTransaction() {
        if (closed) {
            throw new ExecutorException("Executor was closed.");
        }
        return transaction;
    }

    @Override
    public void close(boolean forceRollback) {
        try {
            try {
                rollback(forceRollback);
            } finally {
                if (transaction != null) {
                    transaction.close();
                }
            }
        } catch (SQLException e) {
            // Ignore.  There's nothing that can be done at this point.
            log.warn("Unexpected exception on closing transaction.  Cause: " + e);
        } finally {
            transaction = null;
//            deferredLoads = null;
            //如果SqlSession调用了close()方法，会释放掉一级缓存PerpetualCache对象，一级缓存将不可用；
            localCache = null;
            localOutputParameterCache = null;
            closed = true;
        }
    }

    @Override
    public boolean isClosed() {
        return closed;
    }

    @Override
    public int update(MappedStatement ms, Object parameter) throws SQLException {
//        ErrorContext.instance()
//                .resource(ms.getResource())
//                .activity("executing an update")
//                .object(ms.getId());
        if (closed) {
            throw new ExecutorException("Executor was closed.");
        }
        //清除一级缓存
        clearLocalCache();
        //执行更新操作
        return doUpdate(ms, parameter);
    }

    @Override
    public List<BatchResult> flushStatements() throws SQLException {
        return flushStatements(false);
    }

    public List<BatchResult> flushStatements(boolean isRollBack) throws SQLException {
        if (closed) {
            throw new ExecutorException("Executor was closed.");
        }
        return doFlushStatements(isRollBack);
    }

    /**
     * SqlSession 一级缓存的查询工作流程
     * 1.对于某个查询，根据statementId,params,rowBounds来构建一个key值，根据这个key值去缓存Cache中取出对应的key值存储的缓存结果；
     * 2. 判断从Cache中根据特定的key值取的数据数据是否为空，即是否命中；
     * 3. 如果命中，则直接将缓存结果返回；
     * 4. 如果没命中：
     * 4.1  去数据库中查询数据，得到查询结果；
     * 4.2  将key和查询到的结果分别作为key,value对存储到Cache中；
     * 4.3. 将查询结果返回；
     * 5. 结束。
     */
    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException {
        //1.根据具体传入的参数，动态地生成需要执行的SQL语句，用BoundSql对象表示
        BoundSql boundSql = ms.getBoundSql(parameter);
        //2.为当前的查询创建一个缓存Key
        CacheKey key = createCacheKey(ms, parameter, rowBounds, boundSql);
        return query(ms, parameter, rowBounds, resultHandler, key, boundSql);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey key, BoundSql boundSql) throws SQLException {
        ErrorContext.instance().resource(ms.getResource()).activity("executing a query").object(ms.getId());
        if (closed) {
            throw new ExecutorException("Executor was closed.");
        }
        if (queryStack == 0 && ms.isFlushCacheRequired()) {
            //清除本地缓存数据，会话缓存还可用
            clearLocalCache();
        }
        List<E> list;
        try {
            queryStack++;
            list = resultHandler == null ? (List<E>) localCache.getObject(key) : null;
            if (list != null) {
                //从本地缓存（一级缓存）中读取数据（当服务器宕机后数据消失）
                handleLocallyCachedOutputParameters(ms, key, parameter, boundSql);
            } else {
                //3.缓存中没有值，直接从数据库中读取数据
                list = queryFromDatabase(ms, parameter, rowBounds, resultHandler, key, boundSql);
            }
        } finally {
            queryStack--;
        }
        if (queryStack == 0) {
            // issue #601
//            deferredLoads.clear();
            if (configuration.getLocalCacheScope() == LocalCacheScope.STATEMENT) {
                // issue #482
                clearLocalCache();
            }
        }
        return list;
    }


    /**
     * MyBatis认为，对于两次查询，如果以下条件都完全一样，那么就认为它们是完全相同的两次查询：
     * 1. 传入的 statementId
     * 2. 查询时要求的结果集中的结果范围 （结果的范围通过rowBounds.offset和rowBounds.limit表示）；
     * 3. 这次查询所产生的最终要传递给JDBC java.sql.Preparedstatement的Sql语句字符串（boundSql.getSql() ）
     * 4. 传递给java.sql.Statement要设置的参数值
     * 现在分别解释上述四个条件：
     * 1. 传入的statementId，对于MyBatis而言，你要使用它，必须需要一个statementId，它代表着你将执行什么样的Sql；
     * 2. MyBatis自身提供的分页功能是通过RowBounds来实现的，它通过rowBounds.offset和rowBounds.limit来过滤查询出来的结果集，这种分页功能是基于查询结果的再过滤，而不是进行数据库的物理分页；
     * 由于MyBatis底层还是依赖于JDBC实现的，那么，对于两次完全一模一样的查询，MyBatis要保证对于底层JDBC而言，也是完全一致的查询才行。而对于JDBC而言，两次查询，只要传入给JDBC的SQL语句完全一致，传入的参数也完全一致，就认为是两次查询是完全一致的。
     * 上述的第3个条件正是要求保证传递给JDBC的SQL语句完全一致；第4条则是保证传递给JDBC的参数也完全一致；
     * 即3、4两条MyBatis最本质的要求就是：
     * 调用JDBC的时候，传入的SQL语句要完全相同，传递给JDBC的参数值也要完全相同。
     * CacheKey由以下条件决定：
     * statementId  + rowBounds  + 传递给JDBC的SQL  + 传递给JDBC的参数值 + EnvironmentId参数
     */
    @Override
    public CacheKey createCacheKey(MappedStatement ms, Object parameterObject, RowBounds rowBounds, BoundSql boundSql) {
        if (closed) {
            throw new ExecutorException("Executor was closed.");
        }
        CacheKey cacheKey = new CacheKey();
        //1. 传入的 statementId
        cacheKey.update(ms.getId());
        //2. 查询时要求的结果集中的结果范围 （结果的范围通过rowBounds.offset和rowBounds.limit表示）；
        cacheKey.update(Integer.valueOf(rowBounds.getOffset()));
        cacheKey.update(Integer.valueOf(rowBounds.getLimit()));
        //3. 这次查询所产生的最终要传递给JDBC java.sql.Preparedstatement的Sql语句字符串（boundSql.getSql() ）
        cacheKey.update(boundSql.getSql());
        // 将每一个要传递给JDBC的参数值也更新到CacheKey中
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        TypeHandlerRegistry typeHandlerRegistry = ms.getConfiguration().getTypeHandlerRegistry();
        // mimic DefaultParameterHandler logic
        for (int i = 0; i < parameterMappings.size(); i++) {
            ParameterMapping parameterMapping = parameterMappings.get(i);
            if (parameterMapping.getMode() != ParameterMode.OUT) {
                Object value = null;
                String propertyName = parameterMapping.getProperty();
                if (boundSql.hasAdditionalParameter(propertyName)) {
                    value = boundSql.getAdditionalParameter(propertyName);
                } else if (parameterObject == null) {
                    value = null;
                } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                    value = parameterObject;
                } else {
                    MetaObject metaObject = configuration.newMetaObject(parameterObject);
                    value = metaObject.getValue(propertyName);
                }
                //4. 传递给java.sql.Statement要设置的参数值
                cacheKey.update(value);
            }
        }
        if (configuration.getEnvironment() != null) {
            // issue #176
            //5.传递数据源ID
            cacheKey.update(configuration.getEnvironment().getId());
        }
        return cacheKey;
    }

    @Override
    public boolean isCached(MappedStatement ms, CacheKey key) {
        return localCache.getObject(key) != null;
    }

    @Override
    public void commit(boolean required) throws SQLException {
        if (closed) {
            throw new ExecutorException("Cannot commit, transaction is already closed");
        }
        clearLocalCache();
        flushStatements();
        if (required) {
            transaction.commit();
        }
    }

    @Override
    public void rollback(boolean required) throws SQLException {
        if (!closed) {
            try {
                clearLocalCache();
                flushStatements(true);
            } finally {
                if (required) {
                    transaction.rollback();
                }
            }
        }
    }

    /**
     * 如果SqlSession调用了clearCache()，会清空PerpetualCache对象中的数据，但是该对象仍可使用；
     */
    @Override
    public void clearLocalCache() {
        if (!closed) {
            localCache.clear();
            localOutputParameterCache.clear();
        }
    }

    /**
     * 执行更新操作
     * @param ms 已映射的语句对象
     * @param parameter 参数对象
     * @return 影响记录条数
     * @throws SQLException 异常
     */
    protected abstract int doUpdate(MappedStatement ms, Object parameter) throws SQLException;

    protected abstract List<BatchResult> doFlushStatements(boolean isRollback) throws SQLException;

    protected abstract <E> List<E> doQuery(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException;

    protected void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    private void handleLocallyCachedOutputParameters(MappedStatement ms, CacheKey key, Object parameter, BoundSql boundSql) {
        if (ms.getStatementType() == StatementType.CALLABLE) {
            final Object cachedParameter = localOutputParameterCache.getObject(key);
            if (cachedParameter != null && parameter != null) {
                final MetaObject metaCachedParameter = configuration.newMetaObject(cachedParameter);
                final MetaObject metaParameter = configuration.newMetaObject(parameter);
                for (ParameterMapping parameterMapping : boundSql.getParameterMappings()) {
                    if (parameterMapping.getMode() != ParameterMode.IN) {
                        final String parameterName = parameterMapping.getProperty();
                        final Object cachedValue = metaCachedParameter.getValue(parameterName);
                        metaParameter.setValue(parameterName, cachedValue);
                    }
                }
            }
        }
    }

    private <E> List<E> queryFromDatabase(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey key, BoundSql boundSql) throws SQLException {
        List<E> list;
        localCache.putObject(key, ExecutionPlaceholder.EXECUTION_PLACEHOLDER);
        try {
            //4. 执行查询，返回List 结果，然后将查询的结果放入缓存之中
            list = doQuery(ms, parameter, rowBounds, resultHandler, boundSql);
        } finally {
            localCache.removeObject(key);
        }
        localCache.putObject(key, list);
        if (ms.getStatementType() == StatementType.CALLABLE) {
            localOutputParameterCache.putObject(key, parameter);
        }
        return list;
    }

    protected Connection getConnection() throws SQLException {
        Connection connection = transaction.getConnection();
        return connection;
    }

    public void setExecutorWrapper(Executor wrapper) {
        this.wrapper = wrapper;
    }

}
