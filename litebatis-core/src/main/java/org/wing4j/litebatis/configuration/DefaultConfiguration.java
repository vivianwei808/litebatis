package org.wing4j.litebatis.configuration;

import org.wing4j.litebatis.cache.Cache;
import org.wing4j.litebatis.executor.Executor;
import org.wing4j.litebatis.executor.parameter.ParameterHandler;
import org.wing4j.litebatis.executor.resultset.ResultSetHandler;
import org.wing4j.litebatis.executor.statement.StatementHandler;
import org.wing4j.litebatis.mapping.*;
import org.wing4j.litebatis.Configuration;
import org.wing4j.litebatis.reflection.ReflectorFactory;
import org.wing4j.litebatis.reflection.factory.ObjectFactory;
import org.wing4j.litebatis.session.*;
import org.wing4j.litebatis.transaction.Transaction;
import org.wing4j.litebatis.type.JdbcType;
import org.wing4j.litebatis.type.TypeHandlerRegistry;

import java.util.Collection;
import java.util.Properties;

/**
 * Created by wing4j on 2017/5/17.
 */
public class DefaultConfiguration implements Configuration {

    @Override
    public Environment getEnvironment() {
        return null;
    }

    @Override
    public Executor newExecutor(Transaction transaction, ExecutorType executorType) {
        return null;
    }

    @Override
    public Executor newExecutor(Transaction transaction) {
        return null;
    }

    @Override
    public void addMappedStatement(MappedStatement ms) {

    }

    @Override
    public Collection<MappedStatement> getMappedStatements() {
        return null;
    }

    @Override
    public MappedStatement getMappedStatement(String id) {
        return null;
    }

    @Override
    public <T> void addMapper(Class<T> type) {

    }

    @Override
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return null;
    }


    @Override
    public Properties getVariables() {
        return null;
    }


    @Override
    public Cache getCache(String id) {
        return null;
    }

    @Override
    public void addCache(Cache cache) {

    }

    @Override
    public void addParameterMap(ParameterMap pm) {

    }

    @Override
    public boolean isUseColumnLabel() {
        return false;
    }

    @Override
    public Integer getDefaultStatementTimeout() {
        return null;
    }

    @Override
    public Integer getDefaultFetchSize() {
        return null;
    }

    @Override
    public ResultMap getResultMap(String id) {
        return null;
    }

    @Override
    public ObjectFactory getObjectFactory() {
        return null;
    }

    @Override
    public ParameterHandler newParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql) {
        return null;
    }

    @Override
    public ResultSetHandler newResultSetHandler(Executor executor, MappedStatement mappedStatement, RowBounds rowBounds, ParameterHandler parameterHandler, ResultHandler resultHandler, BoundSql boundSql) {
        return null;
    }

    @Override
    public StatementHandler newStatementHandler(Executor executor, MappedStatement mappedStatement, Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        return null;
    }

    @Override
    public LocalCacheScope getLocalCacheScope() {
        return null;
    }

    @Override
    public ReflectorFactory getReflectorFactory() {
        return null;
    }


    @Override
    public TypeHandlerRegistry getTypeHandlerRegistry() {
        return null;
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
