package org.wing4j.litebatis.cache;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * Created by wing4j on 2017/5/15.
 * 缓存
 */
public interface Cache {
    /**
     * 缓存编号
     *
     * @return
     */
    String getId();

    /**
     * 想缓存放入键值
     *
     * @param key   键
     * @param value 值
     */
    void putObject(Object key, Object value);

    /**
     * 按键获取缓存的值
     *
     * @param key 键
     * @return 值
     */
    Object getObject(Object key);

    /**
     * 移除缓存
     *
     * @param key 键
     * @return 值
     */
    Object removeObject(Object key);

    /**
     * 清空缓存
     */
    void clear();

    /**
     * 获取缓存大小
     *
     * @return 缓存大小
     */
    int getSize();

    /**
     * 获取读写锁
     *
     * @return 读写锁
     */
    ReadWriteLock getReadWriteLock();
}