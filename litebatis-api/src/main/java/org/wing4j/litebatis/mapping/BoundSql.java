package org.wing4j.litebatis.mapping;

import java.util.List;

/**
 * Created by wing4j on 2017/6/14.
 * 受限制的SQL对象
 */
public interface BoundSql {
    /**
     * 获取处理好的SQL语句
     *
     * @return SQL语句
     */
    String getSql();

    /**
     * 获取参数映射信息列表
     *
     * @return 参数映射信息列表
     */
    List<ParameterMapping> getParameterMappings();

    /**
     * 获取参数对象
     *
     * @return 参数对象
     */
    Object getParameterObject();

    /**
     * 是否存在附加参数
     *
     * @param name 参数名
     * @return 存在返回真
     */
    boolean hasAdditionalParameter(String name);

    /**
     * 设置附加参数值
     *
     * @param name  参数名
     * @param value 参数值
     */
    void setAdditionalParameter(String name, Object value);

    /**
     * 获取附加参数值
     *
     * @param name 参数名
     * @return 参数值
     */
    Object getAdditionalParameter(String name);
}
