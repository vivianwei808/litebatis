package org.wing4j.litebatis.builder;

import org.wing4j.litebatis.Configuration;
import org.wing4j.litebatis.mapping.SqlSource;
import org.wing4j.litebatis.scripting.StaticSqlSource;

import java.util.Map;

/**
 * Created by wing4j on 2017/6/17.
 */
public class SqlSourceBuilder extends BaseBuilder{
    public SqlSourceBuilder(Configuration configuration) {
        super(configuration);
    }

    public SqlSource parse(String originalSql, Class<?> parameterType, Map<String, Object> additionalParameters) {
//        ParameterMappingTokenHandler handler = new ParameterMappingTokenHandler(configuration, parameterType, additionalParameters);
//        GenericTokenParser parser = new GenericTokenParser("#{", "}", handler);
//        String sql = parser.parse(originalSql);
        String sql = originalSql;
//        return new StaticSqlSource(configuration, sql, handler.getParameterMappings());
        return new StaticSqlSource(configuration, sql);
    }
}
