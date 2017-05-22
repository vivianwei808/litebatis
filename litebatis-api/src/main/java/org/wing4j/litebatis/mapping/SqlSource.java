package org.wing4j.litebatis.mapping;

public interface SqlSource {
  BoundSql getBoundSql(Object parameterObject);
}
