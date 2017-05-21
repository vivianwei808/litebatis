package org.wing4j.orm.litebatis.mapping;

public interface SqlSource {
  BoundSql getBoundSql(Object parameterObject);
}
