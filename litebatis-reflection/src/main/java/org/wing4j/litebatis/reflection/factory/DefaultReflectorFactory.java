package org.wing4j.litebatis.reflection.factory;

import org.wing4j.litebatis.reflection.DefaultReflector;
import org.wing4j.litebatis.reflection.Reflector;
import org.wing4j.litebatis.reflection.ReflectorFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DefaultReflectorFactory implements ReflectorFactory {
  private boolean classCacheEnabled = true;
  private final ConcurrentMap<Class<?>, Reflector> reflectorMap = new ConcurrentHashMap<Class<?>, Reflector>();

  public DefaultReflectorFactory() {
  }

  @Override
  public boolean isClassCacheEnabled() {
    return classCacheEnabled;
  }

  @Override
  public void setClassCacheEnabled(boolean classCacheEnabled) {
    this.classCacheEnabled = classCacheEnabled;
  }

  @Override
  public Reflector findForClass(Class<?> type) {
    if (classCacheEnabled) {
            // synchronized (type) removed see issue #461
      Reflector cached = reflectorMap.get(type);
      if (cached == null) {
        cached = new DefaultReflector(type);
        reflectorMap.put(type, cached);
      }
      return cached;
    } else {
      return new DefaultReflector(type);
    }
  }

}
