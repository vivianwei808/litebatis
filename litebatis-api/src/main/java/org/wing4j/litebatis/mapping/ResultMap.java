package org.wing4j.litebatis.mapping;

import org.wing4j.litebatis.Configuration;

import java.util.List;
import java.util.Set;

/**
 * Created by wing4j on 2017/5/21.
 * 结果映射
 */
public interface ResultMap {
    void setConfiguration(Configuration configuration);
    /**
     * 编号
     * @return 编号
     */
    String getId();
    Class<?> getType();
    /**
     *
     * @return
     */
    Set<String> getMappedColumns();

    /**
     * 获取结果映射信息
     * @return 结果映射信息
     */
    List<ResultMapping> getResultMappings();

    Boolean getAutoMapping();
    List<ResultMapping> getPropertyResultMappings();
    List<ResultMapping> getIdResultMappings();
    boolean hasNestedResultMaps();
    List<ResultMapping> getConstructorResultMappings();
    Discriminator getDiscriminator();
}
