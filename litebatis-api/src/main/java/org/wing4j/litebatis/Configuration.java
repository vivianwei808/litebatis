package org.wing4j.litebatis;

import org.wing4j.litebatis.executor.Executor;
import org.wing4j.litebatis.executor.parameter.ParameterHandler;
import org.wing4j.litebatis.executor.resultset.ResultSetHandler;
import org.wing4j.litebatis.executor.statement.StatementHandler;
import org.wing4j.litebatis.mapping.*;
import org.wing4j.litebatis.reflection.ReflectorFactory;
import org.wing4j.litebatis.reflection.ObjectFactory;
import org.wing4j.litebatis.session.*;
import org.wing4j.litebatis.transaction.Transaction;
import org.wing4j.litebatis.type.TypeHandlerRegistry;

import java.util.Collection;

/**
 * Created by wing4j on 2017/5/16.
 */
public interface Configuration {
    int DEFAULT_STATEMENT_TIMEOUT = 60 * 1000;
//    Environment getEnvironment();

    Executor newExecutor(Transaction transaction, ExecutorType executorType);

    Executor newExecutor(Transaction transaction);

    void addMappedStatement(MappedStatement ms);

    Collection<MappedStatement> getMappedStatements();
    /**
     * 根据语句ID获取已映射的语句对象
     * @param id 语句ID，格式为id或者namespace.id
     * @return 已映射的语句对象
     */
    MappedStatement getMappedStatement(String id);
    MappedStatement getMappedStatement(String id, boolean validateIncompleteStatements);


    <T> void addMapper(Class<T> type);

    <T> T getMapper(Class<T> type, SqlSession sqlSession);

//    Properties getVariables();

//    Cache getCache(String id);

//    void addCache(Cache cache);

    void addParameterMap(ParameterMap pm);

    boolean isUseColumnLabel();

    Integer getDefaultStatementTimeout();

    Integer getDefaultFetchSize();

//    ResultMap getResultMap(String id);

    ObjectFactory getObjectFactory();

    /**
     * 构建一个参数处理器
     * @param mappedStatement 已映射的语句对象
     * @param parameterObject 参数对象
     * @param boundSql 受限制的SQL对象
     * @return 参数处理器
     */
    ParameterHandler newParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql);

    /**
     * 构建一个结果集处理器
     * @param executor 执行器
     * @param mappedStatement 已映射的语句对象
     * @param rowBounds 逻辑行限制对象
     * @param parameterHandler 参数处理器
     * @param resultHandler 结果处理器
     * @param boundSql 受限制的SQL对象
     * @return 结果集处理器
     */
    ResultSetHandler newResultSetHandler(Executor executor, MappedStatement mappedStatement, RowBounds rowBounds, ParameterHandler parameterHandler, ResultHandler resultHandler, BoundSql boundSql);

    /**
     * 构建一个语句处理器
     * @param executor 执行器
     * @param mappedStatement 已映射的语句对象
     * @param parameterObject 参数对象
     * @param rowBounds 逻辑行限制对象
     * @param resultHandler 结果处理器
     * @param boundSql 受限制的SQL对象
     * @return
     */
    StatementHandler newStatementHandler(Executor executor, MappedStatement mappedStatement, Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql);

//    MetaObject newMetaObject(Object object);

    LocalCacheScope getLocalCacheScope();

//        TypeAliasRegistry getTypeAliasRegistry();
    ReflectorFactory getReflectorFactory();

    TypeHandlerRegistry getTypeHandlerRegistry();

    AutoMappingBehavior getAutoMappingBehavior();

    boolean isSafeRowBoundsEnabled();

    boolean isSafeResultHandlerEnabled();

    Class<?> getConfigurationFactory();

    boolean isCallSettersOnNulls();

    boolean isMapUnderscoreToCamelCase();

    boolean hasResultMap(String id);
}
