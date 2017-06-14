package org.wing4j.litebatis.mapping;

import org.wing4j.litebatis.Configuration;
import org.wing4j.litebatis.mapping.ParameterMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wing4j on 2017/5/15.
 */
public class DefaultBoundSql implements BoundSql{

    private String sql;
    private List<ParameterMapping> parameterMappings;
    private Object parameterObject;
    private Map<String, Object> additionalParameters;
//    private MetaObject metaParameters;

    public DefaultBoundSql(Configuration configuration, String sql, List<ParameterMapping> parameterMappings, Object parameterObject) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
        this.parameterObject = parameterObject;
        this.additionalParameters = new HashMap<String, Object>();
//        this.metaParameters = configuration.newMetaObject(additionalParameters);
    }

    public String getSql() {
        return sql;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public Object getParameterObject() {
        return parameterObject;
    }

    public boolean hasAdditionalParameter(String name) {
//        return metaParameters.hasGetter(name);
        return false;
    }

    public void setAdditionalParameter(String name, Object value) {
//        metaParameters.setValue(name, value);
    }

    public Object getAdditionalParameter(String name) {
//        return metaParameters.getValue(name);
        return null;
    }
}
