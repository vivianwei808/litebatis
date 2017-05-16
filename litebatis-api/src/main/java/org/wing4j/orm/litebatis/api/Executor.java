package org.wing4j.orm.litebatis.api;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by wing4j on 2017/5/15.
 */
public interface Executor {
    int update(MappedStatement ms, Object parameter) throws SQLException;
    <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey cacheKey, BoundSql boundSql) throws SQLException;
    void commit(boolean required) throws SQLException;
    void rollback(boolean required) throws SQLException;
    void close(boolean forceRollback);
    boolean isClosed();
}
