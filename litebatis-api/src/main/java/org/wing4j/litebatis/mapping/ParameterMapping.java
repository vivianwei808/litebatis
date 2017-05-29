package org.wing4j.litebatis.mapping;

import org.wing4j.litebatis.Configuration;
import org.wing4j.litebatis.type.JdbcType;
import org.wing4j.litebatis.type.TypeHandler;

/**
 * Created by wing4j on 2017/5/16.
 * 参数映射信息
 */
public interface ParameterMapping {
    void setConfiguration(Configuration configuration);
    /**
     * 获取Java领域的字段名
     * @return 字段名
     */
    String getProperty();
    /**
     * 获取Java领域的字段数据类型
     * @return 数据类型
     */
    Class<?> getJavaType();
    /**
     * 获取参数模式
     * @return 参数模式
     */
    ParameterMode getMode();
    /**
     * 数据库领域的类型
     * @return 数据库领域的类型
     */
    JdbcType getJdbcType();

    /**
     * 该字段的类型处理器
     * @return 类型处理器
     */
    TypeHandler<?> getTypeHandler();

    /**
     * 数字型的精度
     * @return 小数精度
     */
    Integer getNumericScale();

    /**
     * 数据库领域的具体类型
     * 例如VARCHAR
     * @return 具体类型
     */
    String getJdbcTypeName();

    String getResultMapId();
}
