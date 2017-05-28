package org.wing4j.litebatis.executor.keygen;

import org.wing4j.litebatis.executor.Executor;
import org.wing4j.litebatis.mapping.MappedStatement;

import java.sql.Statement;

/**
 * Created by wing4j on 2017/5/15.
 * 主键生成器
 */
public interface KeyGenerator {
    /**
     * 处理前
     *
     * @param executor  执行器
     * @param ms        映射语句
     * @param stmt      JDBC语句
     * @param parameter 参数对象
     */
    void processBefore(Executor executor, MappedStatement ms, Statement stmt, Object parameter);

    /**
     * 处理后
     *
     * @param executor  执行器
     * @param ms        映射语句
     * @param stmt      JDBC语句
     * @param parameter 参数对象
     */
    void processAfter(Executor executor, MappedStatement ms, Statement stmt, Object parameter);

}
