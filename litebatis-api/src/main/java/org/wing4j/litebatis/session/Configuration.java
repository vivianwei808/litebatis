package org.wing4j.litebatis.session;

import org.wing4j.litebatis.cache.Cache;
import org.wing4j.litebatis.executor.Executor;
import org.wing4j.litebatis.executor.parameter.ParameterHandler;
import org.wing4j.litebatis.executor.resultset.ResultSetHandler;
import org.wing4j.litebatis.mapping.BoundSql;
import org.wing4j.litebatis.mapping.MappedStatement;
import org.wing4j.litebatis.mapping.ParameterMap;
import org.wing4j.litebatis.mapping.ResultMap;
import org.wing4j.litebatis.reflection.MetaObject;
import org.wing4j.litebatis.reflection.factory.ObjectFactory;
import org.wing4j.litebatis.session.SqlSession;
import org.wing4j.litebatis.transaction.Transaction;
import org.wing4j.litebatis.type.JdbcType;
import org.wing4j.litebatis.type.TypeAliasRegistry;
import org.wing4j.litebatis.type.TypeHandlerRegistry;

import java.util.Collection;
import java.util.Properties;

/**
 * Created by wing4j on 2017/5/16.
 */
public interface Configuration {
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

    TypeAliasRegistry getTypeAliasRegistry();

    TypeHandlerRegistry getTypeHandlerRegistry();
}
