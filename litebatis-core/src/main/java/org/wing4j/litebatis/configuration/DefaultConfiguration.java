package org.wing4j.litebatis.configuration;

import org.wing4j.litebatis.cache.Cache;
import org.wing4j.litebatis.executor.Executor;
import org.wing4j.litebatis.executor.ResultExtractor;
import org.wing4j.litebatis.executor.SimpleExecutor;
import org.wing4j.litebatis.executor.parameter.ParameterHandler;
import org.wing4j.litebatis.executor.resultset.SimpleResultSetHandler;
import org.wing4j.litebatis.executor.resultset.ResultSetHandler;
import org.wing4j.litebatis.executor.statement.RoutingStatementHandler;
import org.wing4j.litebatis.executor.statement.StatementHandler;
import org.wing4j.litebatis.mapping.*;
import org.wing4j.litebatis.Configuration;
import org.wing4j.litebatis.plugin.InterceptorChain;
import org.wing4j.litebatis.reflection.*;
import org.wing4j.litebatis.scripting.DefaultParameterHandler;
import org.wing4j.litebatis.session.*;
import org.wing4j.litebatis.transaction.Transaction;
import org.wing4j.litebatis.type.DefaultTypeHandlerRegistry;
import org.wing4j.litebatis.type.TypeHandlerRegistry;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;

/**
 * Created by wing4j on 2017/5/17.
 */
public class DefaultConfiguration implements Configuration {
    protected final InterceptorChain interceptorChain = new InterceptorChain();
    protected final Map<String, MappedStatement> mappedStatements = new StrictMap<MappedStatement>("Mapped Statements collection");
    protected final TypeHandlerRegistry typeHandlerRegistry = new DefaultTypeHandlerRegistry();
    protected ReflectorFactory reflectorFactory = new DefaultReflectorFactory();
    protected ObjectFactory objectFactory = new DefaultObjectFactory();
    protected ObjectWrapperFactory objectWrapperFactory = new DefaultObjectWrapperFactory();
    int statementTimeout = DEFAULT_STATEMENT_TIMEOUT;
//    @Override
//    public Environment getEnvironment() {
//        return null;
//    }

    @Override
    public Executor newExecutor(Transaction transaction, ExecutorType executorType) {
        if(executorType == ExecutorType.SIMPLE){
            return new SimpleExecutor(this, transaction);
        }else if(executorType == ExecutorType.REUSE){
            return null;
        }else{
            return null;
        }
    }

    @Override
    public Executor newExecutor(Transaction transaction) {
        return newExecutor(transaction, ExecutorType.SIMPLE);
    }

    @Override
    public void addMappedStatement(MappedStatement ms) {
        mappedStatements.put(ms.getId(), ms);
    }

    @Override
    public Collection<MappedStatement> getMappedStatements() {
        return mappedStatements.values();
    }

    @Override
    public MappedStatement getMappedStatement(String id) {
        return getMappedStatement(id, true);
    }

    @Override
    public MappedStatement getMappedStatement(String id, boolean validateIncompleteStatements) {
        if (validateIncompleteStatements) {
            //TODO 执行语句编译
        }
        return mappedStatements.get(id);
    }

    @Override
    public <T> void addMapper(Class<T> type) {

    }

    @Override
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return null;
    }


//    @Override
//    public Properties getVariables() {
//        return null;
//    }


//    @Override
//    public Cache getCache(String id) {
//        return null;
//    }
//
//    @Override
//    public void addCache(Cache cache) {
//
//    }

    @Override
    public void addParameterMap(ParameterMap pm) {

    }
//    @Override
//    public ResultMap getResultMap(String id) {
//        return null;
//    }
    @Override
    public boolean isUseColumnLabel() {
        return false;
    }

    @Override
    public Integer getDefaultStatementTimeout() {
        return statementTimeout;
    }

    @Override
    public Integer getDefaultFetchSize() {
        return null;
    }



    @Override
    public ObjectFactory getObjectFactory() {
        ObjectFactory objectFactory = new DefaultObjectFactory();
        return objectFactory;
    }

    @Override
    public ParameterHandler newParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql) {
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        parameterHandler = (ParameterHandler) interceptorChain.pluginAll(parameterHandler);
        return parameterHandler;
    }

    @Override
    public ResultSetHandler newResultSetHandler(Executor executor, MappedStatement mappedStatement, RowBounds rowBounds, ParameterHandler parameterHandler, ResultHandler resultHandler, BoundSql boundSql) {
        ResultSetHandler resultSetHandler = new SimpleResultSetHandler(executor, mappedStatement, parameterHandler, resultHandler, boundSql, rowBounds);
        resultSetHandler = (ResultSetHandler) interceptorChain.pluginAll(resultSetHandler);
        return resultSetHandler;
    }

    @Override
    public StatementHandler newStatementHandler(Executor executor, MappedStatement mappedStatement, Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        //构建一个语句处理器的代理对象
        StatementHandler statementHandler = new RoutingStatementHandler(executor, mappedStatement, parameterObject, rowBounds, resultHandler, boundSql);
        statementHandler = (StatementHandler) interceptorChain.pluginAll(statementHandler);
        return statementHandler;
    }
    @Override
    public LocalCacheScope getLocalCacheScope() {
        return null;
    }

    @Override
    public ReflectorFactory getReflectorFactory() {
        ReflectorFactory reflectorFactory = new DefaultReflectorFactory();
        return reflectorFactory;
    }


    @Override
    public TypeHandlerRegistry getTypeHandlerRegistry() {
        return typeHandlerRegistry;
    }

    @Override
    public AutoMappingBehavior getAutoMappingBehavior() {
        return null;
    }

    @Override
    public boolean isSafeRowBoundsEnabled() {
        return false;
    }

    @Override
    public boolean isSafeResultHandlerEnabled() {
        return false;
    }

    @Override
    public Class<?> getConfigurationFactory() {
        return null;
    }

    @Override
    public boolean isCallSettersOnNulls() {
        return false;
    }

    @Override
    public boolean isMapUnderscoreToCamelCase() {
        return false;
    }

    @Override
    public boolean hasResultMap(String id) {
        return false;
    }
}
