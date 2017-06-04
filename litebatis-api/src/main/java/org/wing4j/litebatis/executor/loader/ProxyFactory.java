package org.wing4j.litebatis.executor.loader;

import org.wing4j.litebatis.Configuration;
import org.wing4j.litebatis.reflection.factory.ObjectFactory;

import java.util.List;
import java.util.Properties;

public interface ProxyFactory {

  void setProperties(Properties properties);

  Object createProxy(Object target, ResultLoaderMap lazyLoader, Configuration configuration, ObjectFactory objectFactory, List<Class<?>> constructorArgTypes, List<Object> constructorArgs);
  
}
