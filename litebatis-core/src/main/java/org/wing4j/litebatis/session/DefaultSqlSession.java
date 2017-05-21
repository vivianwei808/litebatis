package org.wing4j.litebatis.session;

import org.wing4j.orm.litebatis.session.SqlSession;

import java.util.List;

/**
 * Created by wing4j on 2017/5/16.
 */
public class DefaultSqlSession implements SqlSession {
    @Override
    public <E> List<E> selectList(String statement) {
        return null;
    }

    @Override
    public <E> List<E> selectList(String statement, Object parameter) {
        return null;
    }

    @Override
    public int insert(String statement, Object parameter) {
        return 0;
    }

    @Override
    public int update(String statement) {
        return 0;
    }

    @Override
    public int update(String statement, Object parameter) {
        return 0;
    }

    @Override
    public int delete(String statement) {
        return 0;
    }

    @Override
    public int delete(String statement, Object parameter) {
        return 0;
    }
}
