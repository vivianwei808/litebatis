package org.wing4j.orm.litebatis.plugin;

import java.util.Properties;

public interface Interceptor {

  Object intercept(Invocation invocation) throws Throwable;

  Object plugin(Object target);

  void setProperties(Properties properties);

}