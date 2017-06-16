package org.wing4j.litebatis.executor.statement;

import org.wing4j.litebatis.executor.parameter.ParameterHandler;
import org.wing4j.litebatis.mapping.BoundSql;
import org.wing4j.litebatis.session.ResultHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * 语句处理器
 */
public interface StatementHandler {
    /**
     * 创建JDBC语句对象，进行预处理
     *
     * @param connection 连接信息
     * @return JDBC语句对象
     * @throws SQLException 异常
     */
    Statement prepare(Connection connection) throws SQLException;

    /**
     * 向JDBC语句对象设置参数
     *
     * @param statement JDBC语句对象
     * @throws SQLException 异常
     */
    void parameterize(Statement statement) throws SQLException;

    /**
     * 执行批量操作
     *
     * @param statement JDBC语句对象
     * @throws SQLException 异常
     */
    void batch(Statement statement) throws SQLException;

    /**
     * 执行更新/删除/新增操作
     *
     * @param statement JDBC语句对象
     * @return 影响记录条数
     * @throws SQLException 异常
     */
    int update(Statement statement) throws SQLException;

    /**
     * 执行查询操作
     *
     * @param statement     JDBC语句对象
     * @param resultHandler 结果处理器
     * @param <E>           结果类型
     * @return 结果对象列表
     * @throws SQLException 异常
     */
    <E> List<E> query(Statement statement, ResultHandler resultHandler) throws SQLException;

    /**
     * 获取该处理器绑定的受限制SQL语句对象
     *
     * @return 受限制SQL语句对象
     */
    BoundSql getBoundSql();

    /**
     * 获取该处理器绑定的参数处理器
     *
     * @return 参数处理器
     */
    ParameterHandler getParameterHandler();

}
