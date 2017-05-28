package org.wing4j.litebatis.executor.resultset;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * 结果集处理器
 */
public interface ResultSetHandler {
    /**
     * 从已执行的JDBC语句对象获取结果集，并将其处理为列表对象
     * @param stmt JDBC语句对象
     * @param <E> 结果对象
     * @return 结果对象列表
     * @throws SQLException 异常
     */
    <E> List<E> handleResultSets(Statement stmt) throws SQLException;

    /**
     * 调用存储过程时，处理输出参数
     * @param cs JDBC语句对象
     * @throws SQLException 异常
     */
    void handleOutputParameters(CallableStatement cs) throws SQLException;
}