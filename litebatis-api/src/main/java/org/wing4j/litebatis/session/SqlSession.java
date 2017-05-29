package org.wing4j.litebatis.session;

import org.wing4j.litebatis.Configuration;
import org.wing4j.litebatis.executor.BatchResult;

import java.sql.Connection;
import java.util.List;

/**
 * Created by wing4j on 2017/5/15.
 */
public interface SqlSession {
    Configuration getConfiguration();
    <E> List<E> selectList(String statement);

    /**
     * 查询数据库结果
     * @param statement 语句编号
     * @param parameter 参数
     * @param <E> 返回结果类型
     * @return 结果列表
     */
    <E> List<E> selectList(String statement, Object parameter);
    <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds);
    int insert(String statement, Object parameter);
    int update(String statement);
    int update(String statement, Object parameter);
    int delete(String statement);
    int delete(String statement, Object parameter);
    void commit();
    void commit(boolean force);
    void rollback();
    void rollback(boolean force);
    List<BatchResult> flushStatements();

    /**
     * 释放掉一级缓存PerpetualCache对象，一级缓存将不可用；
     */
    void close();

    /**
     * 清空PerpetualCache对象中的数据，但是该对象仍可使用；
     */
    void clearCache();
    <T> T getMapper(Class<T> type);
    Connection getConnection();
}
