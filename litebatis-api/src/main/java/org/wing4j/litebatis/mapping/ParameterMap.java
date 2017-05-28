package org.wing4j.litebatis.mapping;

import java.util.List;

/**
 * Created by wing4j on 2017/5/21.
 */
public interface ParameterMap {
    String getId();
    Class<?> getType();
    void setId(String id);
    void setType( Class<?> clazz);
    List<ParameterMapping> getParameterMappings();
}
