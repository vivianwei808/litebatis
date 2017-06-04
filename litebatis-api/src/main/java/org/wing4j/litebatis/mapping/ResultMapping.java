package org.wing4j.litebatis.mapping;

import org.wing4j.litebatis.type.JdbcType;
import org.wing4j.litebatis.type.TypeHandler;

import java.util.List;
import java.util.Set;

/**
 * Created by wing4j on 2017/5/17.
 */
public interface ResultMapping {
    String getProperty();

    String getColumn();

    Class<?> getJavaType();

    JdbcType getJdbcType();

    TypeHandler<?> getTypeHandler();

    boolean isLazy();

    String getNestedResultMapId();

    String getResultSet();

    String getNestedQueryId();

    boolean isCompositeResult();
    String getForeignColumn();
    String getColumnPrefix();
    Set<String> getNotNullColumns();
    List<ResultMapping> getComposites();
}
