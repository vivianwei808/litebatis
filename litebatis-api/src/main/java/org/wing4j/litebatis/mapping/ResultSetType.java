package org.wing4j.litebatis.mapping;

import java.sql.ResultSet;

/**
 * 结果集类型
 */
public enum ResultSetType {
    /**
     * 游标允许向前访问
     */
    FORWARD_ONLY(ResultSet.TYPE_FORWARD_ONLY),
    /**
     * 双向滚动，并及时跟踪
     */
    SCROLL_INSENSITIVE(ResultSet.TYPE_SCROLL_INSENSITIVE),
    /**
     * 双向滚动
     */
    SCROLL_SENSITIVE(ResultSet.TYPE_SCROLL_SENSITIVE);

    private int value;

    ResultSetType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
