package org.wing4j.litebatis.mapping;

import lombok.Data;
import org.wing4j.litebatis.Configuration;
import org.wing4j.litebatis.executor.keygen.KeyGenerator;
import org.wing4j.litebatis.executor.keygen.NoKeyGenerator;

import java.util.List;

/**
 * Created by wing4j on 2017/6/13.
 */
@Data
public class DefaultMappedStatement implements MappedStatement{
    String id;
    String resource;
    StatementType statementType;
    KeyGenerator keyGenerator = new NoKeyGenerator();
    Configuration configuration;
    final ParameterMap parameterMap = new DefaultParameterMap();
    SqlSource sqlSource;
    boolean hasNestedResultMaps;
    int fetchSize;
    int timeout;

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings == null || parameterMappings.isEmpty()) {
            boundSql = new DefaultBoundSql(configuration, boundSql.getSql(), parameterMap.getParameterMappings(), parameterObject);
        }

        // check for nested result maps in parameter mappings (issue #30)
        for (ParameterMapping pm : boundSql.getParameterMappings()) {
            String rmId = pm.getResultMapId();
            if (rmId != null) {
                ResultMap rm = configuration.getResultMap(rmId);
                if (rm != null) {
                    hasNestedResultMaps |= rm.hasNestedResultMaps();
                }
            }
        }

        return boundSql;
    }

    @Override
    public String[] getKeyProperties() {
        return new String[0];
    }

    @Override
    public String[] getKeyColumns() {
        return new String[0];
    }

    @Override
    public ResultSetType getResultSetType() {
        return null;
    }

    @Override
    public boolean isFlushCacheRequired() {
        return false;
    }

    @Override
    public List<ResultMap> getResultMaps() {
        return null;
    }

    @Override
    public String[] getResulSets() {
        return new String[0];
    }

    @Override
    public boolean isResultOrdered() {
        return false;
    }
}
