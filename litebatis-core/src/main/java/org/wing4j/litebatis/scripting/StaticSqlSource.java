package org.wing4j.litebatis.scripting;

import org.wing4j.litebatis.Configuration;
import org.wing4j.litebatis.mapping.BoundSql;
import org.wing4j.litebatis.mapping.DefaultBoundSql;
import org.wing4j.litebatis.mapping.ParameterMapping;
import org.wing4j.litebatis.mapping.SqlSource;

import java.util.List;

public class StaticSqlSource implements SqlSource {

  private String sql;
  private List<ParameterMapping> parameterMappings;
  private Configuration configuration;

  public StaticSqlSource(Configuration configuration, String sql) {
    this(configuration, sql, null);
  }

  public StaticSqlSource(Configuration configuration, String sql, List<ParameterMapping> parameterMappings) {
    this.sql = sql;
    this.parameterMappings = parameterMappings;
    this.configuration = configuration;
  }

  @Override
  public BoundSql getBoundSql(Object parameterObject) {
    return new DefaultBoundSql(configuration, sql, parameterMappings, parameterObject);
  }

}