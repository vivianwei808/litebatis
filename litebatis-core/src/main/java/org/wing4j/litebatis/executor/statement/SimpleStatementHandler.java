package org.wing4j.litebatis.executor.statement;

import org.wing4j.litebatis.executor.Executor;
import org.wing4j.litebatis.executor.keygen.Jdbc3KeyGenerator;
import org.wing4j.litebatis.executor.keygen.KeyGenerator;
import org.wing4j.litebatis.executor.keygen.SelectKeyGenerator;
import org.wing4j.litebatis.mapping.BoundSql;
import org.wing4j.litebatis.mapping.MappedStatement;
import org.wing4j.litebatis.session.ResultHandler;
import org.wing4j.litebatis.session.RowBounds;

import java.sql.*;
import java.util.List;

public class SimpleStatementHandler extends BaseStatementHandler {

    public SimpleStatementHandler(Executor executor, MappedStatement mappedStatement, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        super(executor, mappedStatement, parameter, rowBounds, resultHandler, boundSql);
    }

    @Override
    public int update(Statement statement) throws SQLException {
        String sql = boundSql.getSql();
        Object parameterObject = boundSql.getParameterObject();
        KeyGenerator keyGenerator = mappedStatement.getKeyGenerator();
        int rows;
        if (keyGenerator instanceof Jdbc3KeyGenerator) {
            //执行SQL语句
            statement.execute(sql, Statement.RETURN_GENERATED_KEYS);
            //获取影响记录条数
            rows = statement.getUpdateCount();
            //获取物理主键
            keyGenerator.processAfter(executor, mappedStatement, statement, parameterObject);
        } else if (keyGenerator instanceof SelectKeyGenerator) {
            //执行SQL语句
            statement.execute(sql);
            //获取影响记录条数
            rows = statement.getUpdateCount();
            //获取物理主键
            keyGenerator.processAfter(executor, mappedStatement, statement, parameterObject);
        } else {
            //执行SQL语句
            statement.execute(sql);
            //获取影响记录条数
            rows = statement.getUpdateCount();
        }
        return rows;
    }

    @Override
    public void batch(Statement statement) throws SQLException {
        String sql = boundSql.getSql();
        statement.addBatch(sql);
    }

    @Override
    public <E> List<E> query(Statement statement, ResultHandler resultHandler) throws SQLException {
        String sql = boundSql.getSql();
        statement.execute(sql);
        return resultSetHandler.<E>handleResultSets(statement);
    }

    @Override
    protected Statement instantiateStatement(Connection connection) throws SQLException {
        if (mappedStatement.getResultSetType() != null) {
            return connection.createStatement(mappedStatement.getResultSetType().getValue(), ResultSet.CONCUR_READ_ONLY);
        } else {
            return connection.createStatement();
        }
    }

    @Override
    public void parameterize(Statement statement) throws SQLException {
        // N/A
    }

}