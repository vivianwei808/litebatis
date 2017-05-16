package org.wing4j.orm.litebatis.api;

/**
 * Created by wing4j on 2017/5/16.
 */
public interface MetaObject {
    boolean hasGetter(String name);
    void setValue(String name, Object value);
    Object getValue(String name);
}
