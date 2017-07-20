package org.wing4j.litebatis.mapping;

import org.wing4j.litebatis.Configuration;
import org.wing4j.litebatis.reflection.MetaObject;
import org.wing4j.litebatis.reflection.MetaObjectFactory;

import java.util.List;

/**
 * Created by wing4j on 2017/5/15.
 */
public class DefaultBoundSql implements BoundSql{

    private String sql;
    private List<ParameterMapping> parameterMappings;
    private Object parameterObject;
    private MetaObject metaParameters;

    public DefaultBoundSql(Configuration configuration, String sql, List<ParameterMapping> parameterMappings, Object parameterObject) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
        this.parameterObject = parameterObject;
        this.metaParameters = MetaObjectFactory.forObject(this.parameterObject);
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

    public boolean hasParameter(String name) {
        return metaParameters.hasGetter(name);
    }

    public void setParameter(String name, Object value) {
        metaParameters.setValue(name, value);
    }

    public <T> T getParameter(String name) {
        return metaParameters.getValue(name);
    }
}
