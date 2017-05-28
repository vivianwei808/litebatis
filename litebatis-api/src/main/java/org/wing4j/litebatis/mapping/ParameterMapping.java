package org.wing4j.litebatis.mapping;

import org.wing4j.litebatis.type.JdbcType;
import org.wing4j.litebatis.type.TypeHandler;

/**
 * Created by wing4j on 2017/5/16.
 */
public interface ParameterMapping {
    String getProperty();
    ParameterMode getMode();
    JdbcType getJdbcType();
    TypeHandler<?> getTypeHandler();
}
