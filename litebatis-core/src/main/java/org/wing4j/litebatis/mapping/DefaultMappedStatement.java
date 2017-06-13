package org.wing4j.litebatis.mapping;

import lombok.Data;
import org.wing4j.litebatis.Configuration;
import org.wing4j.litebatis.executor.keygen.KeyGenerator;

import java.util.List;

/**
 * Created by wing4j on 2017/6/13.
 */
@Data
public class DefaultMappedStatement implements MappedStatement{
    String id;
    String resource;
    StatementType statementType;
    KeyGenerator keyGenerator;
    Configuration configuration;
    ParameterMap parameterMap;
    int fetchSize;
    int timeout;

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        return null;
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
