package org.wing4j.litebatis;

import org.wing4j.litebatis.cache.Cache;
import org.wing4j.litebatis.executor.Executor;
import org.wing4j.litebatis.executor.loader.ProxyFactory;
import org.wing4j.litebatis.executor.parameter.ParameterHandler;
import org.wing4j.litebatis.executor.resultset.ResultSetHandler;
import org.wing4j.litebatis.executor.statement.StatementHandler;
import org.wing4j.litebatis.mapping.*;
import org.wing4j.litebatis.reflection.MetaObject;
import org.wing4j.litebatis.reflection.ReflectorFactory;
import org.wing4j.litebatis.reflection.factory.ObjectFactory;
import org.wing4j.litebatis.session.*;
import org.wing4j.litebatis.transaction.Transaction;
import org.wing4j.litebatis.type.JdbcType;
import org.wing4j.litebatis.type.TypeHandlerRegistry;

import java.util.Collection;
import java.util.Properties;

/**
 * Created by wing4j on 2017/5/16.
 */
public interface Configuration {
    Environment getEnvironment();

    Executor newExecutor(Transaction transaction, ExecutorType executorType);

    Executor newExecutor(Transaction transaction);

    void addMappedStatement(MappedStatement ms);

    public Collection<MappedStatement> getMappedStatements();

    MappedStatement getMappedStatement(String id);

    <T> void addMapper(Class<T> type);

    <T> T getMapper(Class<T> type, SqlSession sqlSession);

    MetaObject newMetaObject(Object object);

    String getDatabaseId();

    Properties getVariables();

    JdbcType getJdbcTypeForNull();

    Cache getCache(String id);

    void addCache(Cache cache);

    void addParameterMap(ParameterMap pm);

    boolean isUseColumnLabel();

    Integer getDefaultStatementTimeout();

    Integer getDefaultFetchSize();

    ResultMap getResultMap(String id);

    ObjectFactory getObjectFactory();

    ParameterHandler newParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql);

    ResultSetHandler newResultSetHandler(Executor executor, MappedStatement mappedStatement, RowBounds rowBounds, ParameterHandler parameterHandler, ResultHandler resultHandler, BoundSql boundSql);

    StatementHandler newStatementHandler(Executor executor, MappedStatement mappedStatement, Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql);

    LocalCacheScope getLocalCacheScope();

    //    TypeAliasRegistry getTypeAliasRegistry();
    ReflectorFactory getReflectorFactory();

    TypeHandlerRegistry getTypeHandlerRegistry();

    AutoMappingBehavior getAutoMappingBehavior();

    boolean isSafeRowBoundsEnabled();

    boolean isSafeResultHandlerEnabled();

    Class<?> getConfigurationFactory();
    boolean isCallSettersOnNulls();
    boolean isMapUnderscoreToCamelCase();
    ProxyFactory getProxyFactory();
    boolean hasResultMap(String id);
}
