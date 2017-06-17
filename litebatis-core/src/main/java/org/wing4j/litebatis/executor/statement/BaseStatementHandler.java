package org.wing4j.litebatis.executor.statement;

import org.wing4j.litebatis.exception.ExecutorException;
import org.wing4j.litebatis.executor.ErrorContext;
import org.wing4j.litebatis.executor.Executor;
import org.wing4j.litebatis.executor.keygen.KeyGenerator;
import org.wing4j.litebatis.executor.parameter.ParameterHandler;
import org.wing4j.litebatis.executor.resultset.ResultSetHandler;
import org.wing4j.litebatis.mapping.BoundSql;
import org.wing4j.litebatis.mapping.MappedStatement;
import org.wing4j.litebatis.reflection.factory.ObjectFactory;
import org.wing4j.litebatis.Configuration;
import org.wing4j.litebatis.session.ResultHandler;
import org.wing4j.litebatis.session.RowBounds;
import org.wing4j.litebatis.type.TypeHandlerRegistry;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class BaseStatementHandler implements StatementHandler {
    /**
     * 配置对象
     */
    protected final Configuration configuration;
    /**
     * 对象工厂
     */
    protected final ObjectFactory objectFactory;
    /**
     * 类型处理器注册器
     */
    protected final TypeHandlerRegistry typeHandlerRegistry;
    /**
     * 结果集处理器
     */
    protected final ResultSetHandler resultSetHandler;
    /**
     * 参数处理器
     */
    protected final ParameterHandler parameterHandler;
    /**
     * 执行器
     */
    protected final Executor executor;
    /**
     * 已映射的语句对象
     */
    protected final MappedStatement mappedStatement;
    protected final RowBounds rowBounds;
    /**
     * 受限制的SQL对象
     */
    protected BoundSql boundSql;

    protected BaseStatementHandler(Executor executor, MappedStatement mappedStatement, Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        this.configuration = mappedStatement.getConfiguration();
        this.executor = executor;
        this.mappedStatement = mappedStatement;
        this.rowBounds = rowBounds;

        this.typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        this.objectFactory = configuration.getObjectFactory();

        if (boundSql == null) {
            generateKeys(parameterObject);
            boundSql = mappedStatement.getBoundSql(parameterObject);
        }

        this.boundSql = boundSql;
        this.parameterHandler = configuration.newParameterHandler(mappedStatement, parameterObject, boundSql);
        this.resultSetHandler = configuration.newResultSetHandler(executor, mappedStatement, rowBounds, parameterHandler, resultHandler, boundSql);
    }

    @Override
    public BoundSql getBoundSql() {
        return boundSql;
    }

    @Override
    public ParameterHandler getParameterHandler() {
        return parameterHandler;
    }

    @Override
    public Statement prepare(Connection connection) throws SQLException {
//        ErrorContext.instance().sql(boundSql.getSql());
        Statement statement = null;
        try {
            statement = instantiateStatement(connection);
            setStatementTimeout(statement);
            setFetchSize(statement);
            return statement;
        } catch (SQLException e) {
            closeStatement(statement);
            throw e;
        } catch (Exception e) {
            closeStatement(statement);
            throw new ExecutorException("Error preparing statement.  Cause: " + e, e);
        }
    }

    protected abstract Statement instantiateStatement(Connection connection) throws SQLException;

    protected void setStatementTimeout(Statement stmt) throws SQLException {
        Integer timeout = mappedStatement.getTimeout();
        Integer defaultTimeout = configuration.getDefaultStatementTimeout();
        if (timeout != null) {
            stmt.setQueryTimeout(timeout);
        } else if (defaultTimeout != null) {
            stmt.setQueryTimeout(defaultTimeout);
        }
    }

    protected void setFetchSize(Statement stmt) throws SQLException {
        Integer fetchSize = mappedStatement.getFetchSize();
        Integer defaultFetchSize = configuration.getDefaultFetchSize();
        if (fetchSize != null) {
            stmt.setFetchSize(fetchSize);
        }else if (defaultFetchSize != null) {
            stmt.setFetchSize(defaultFetchSize);
        }
    }

    protected void closeStatement(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            //ignore
        }
    }

    protected void generateKeys(Object parameter) {
        KeyGenerator keyGenerator = mappedStatement.getKeyGenerator();
        ErrorContext.instance().store();
        keyGenerator.processBefore(executor, mappedStatement, null, parameter);
        ErrorContext.instance().recall();
    }

}