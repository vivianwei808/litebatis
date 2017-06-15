package org.wing4j.litebatis.session;

/**
 * 本地缓存作用范围
 */
public enum LocalCacheScope {
    /**
     * 会话范围
     */
    SESSION,
    /**
     * 语句范围
     */
    STATEMENT
}