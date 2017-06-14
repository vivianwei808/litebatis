package org.wing4j.litebatis.executor;

import org.wing4j.litebatis.cache.CacheKey;
import org.wing4j.litebatis.mapping.BoundSql;
import org.wing4j.litebatis.mapping.MappedStatement;
import org.wing4j.litebatis.session.ResultHandler;
import org.wing4j.litebatis.session.RowBounds;
import org.wing4j.litebatis.transaction.Transaction;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by wing4j on 2017/5/15.
 * 执行器
 */
public interface Executor {
    /**
     * 无结果处理器常量
     */
    ResultHandler NO_RESULT_HANDLER = null;

    /**
     * 执行更新操作
     * @param ms 已映射的语句对象
     * @param parameter 参数对象
     * @return 影响记录条数
     * @throws SQLException 异常
     */
    int update(MappedStatement ms, Object parameter) throws SQLException;

    /**
     *
     * @param ms 已映射的语句对象
     * @param parameter 参数对象
     * @param rowBounds
     * @param resultHandler 结果处理器
     * @param cacheKey 缓存键对象
     * @param boundSql 受限制的SQL对象
     * @param <E> 结果类型
     * @return 结果列表
     * @throws SQLException 异常
     */
    <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey cacheKey, BoundSql boundSql) throws SQLException;

    /**
     *
     * @param ms
     * @param parameter
     * @param rowBounds
     * @param resultHandler
     * @param <E>
     * @return
     * @throws SQLException
     */
    <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException;
    /**
     * 批量执行时刷新语句
     * @return 结果列表
     * @throws SQLException 异常
     */
    List<BatchResult> flushStatements() throws SQLException;
    /**
     * 提交操作
     * @param required 是否提交事务
     * @throws SQLException 异常
     */
    void commit(boolean required) throws SQLException;

    /**
     * 回滚操作
     * @param required 是否回滚事务
     * @throws SQLException 异常
     */
    void rollback(boolean required) throws SQLException;

    /**
     * 关闭执行器
     * @param forceRollback 是否强制回滚
     */
    void close(boolean forceRollback);

    /**
     * 获取是否已关闭执行器
     * @return 是否已关闭执行器
     */
    boolean isClosed();

    /**
     * 获取绑定的事务对象
     * @return 事务对象
     */
    Transaction getTransaction();

    /**
     * 创建缓存主键
     * @param ms 已映射语句
     * @param parameterObject 参数对象
     * @param rowBounds
     * @param boundSql 受限制的SQL对象
     * @return 缓存主键
     */
    CacheKey createCacheKey(MappedStatement ms, Object parameterObject, RowBounds rowBounds, BoundSql boundSql);

    /**
     * 检查映射语句对应的缓存主键结果是否被缓存
     * @param ms 已映射语句
     * @param key 缓存主键
     * @return 如果被缓存返回真
     */
    boolean isCached(MappedStatement ms, CacheKey key);

    /**
     * 清空本地缓存
     */
    void clearLocalCache();

}
