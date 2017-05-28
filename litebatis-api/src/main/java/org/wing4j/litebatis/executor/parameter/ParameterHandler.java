package org.wing4j.litebatis.executor.parameter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by wing4j on 2017/5/15.
 * 参数处理器
 * 用于将提取的参数值设置到JDBC预编译对象中
 */
public interface ParameterHandler {
    /**
     * 获取参数对象
     *
     * @return 参数对象
     */
    Object getParameterObject();

    /**
     * 设置参数到JDBC预编译对象中
     *
     * @param ps JDBC预编译对象中
     * @throws SQLException 异常
     */
    void setParameters(PreparedStatement ps) throws SQLException;
}