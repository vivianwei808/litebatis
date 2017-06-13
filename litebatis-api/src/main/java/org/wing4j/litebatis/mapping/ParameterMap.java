package org.wing4j.litebatis.mapping;

import org.wing4j.litebatis.Configuration;

import java.util.List;

/**
 * Created by wing4j on 2017/5/21.
 * 参数映射对象
 */
public interface ParameterMap {
    /**
     * 设置绑定的配置对象
     * @param configuration
     */
    void setConfiguration(Configuration configuration);
    /**
     * 设置编号
     * @param id 编号
     */
    void setId(String id);
    /**
     * 编号
     * @return 编号
     */
    String getId();
    /**
     * 参数映射对应的Java类型，可以是实体，也可以是Map
     * @return 参数映射对应的Java类型
     */
    Class<?> getType();

    /**
     * 设置映射的Java类型
     * @param clazz 映射的Java类型
     */
    void setType(Class<?> clazz);

    /**
     * 获取参数映射信息
     * @return 参数映射信息列表
     */
    List<ParameterMapping> getParameterMappings();
}
