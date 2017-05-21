package org.wing4j.orm.litebatis.session;

import java.util.List;

/**
 * Created by wing4j on 2017/5/15.
 */
public interface SqlSession {
    <E> List<E> selectList(String statement);

    /**
     * 查询数据库结果
     * @param statement 语句编号
     * @param parameter 参数
     * @param <E> 返回结果类型
     * @return 结果列表
     */
    <E> List<E> selectList(String statement, Object parameter);
    int insert(String statement, Object parameter);
    int update(String statement);
    int update(String statement, Object parameter);
    int delete(String statement);
    int delete(String statement, Object parameter);
}
