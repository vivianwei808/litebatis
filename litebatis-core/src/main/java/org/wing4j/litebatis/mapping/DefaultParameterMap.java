package org.wing4j.litebatis.mapping;

import lombok.Data;
import org.wing4j.litebatis.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wing4j on 2017/5/28.
 */
@Data
public class DefaultParameterMap implements ParameterMap {
    String id;
    Class<?> type;
    final List<ParameterMapping> parameterMappings = new ArrayList<>();

    public static class Builder {
        private ParameterMap parameterMap = new DefaultParameterMap();

        public Builder(Configuration configuration, String id, Class<?> type, List<ParameterMapping> parameterMappings) {
            parameterMap.setId(id);
            parameterMap.setType(type);
            parameterMap.getParameterMappings().addAll(parameterMappings);
        }

        public ParameterMap build() {
            //lock down collections
//            parameterMap.parameterMappings = Collections.unmodifiableList(parameterMap.parameterMappings);
            return parameterMap;
        }
    }
}
