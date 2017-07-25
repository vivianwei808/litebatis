package org.wing4j.litebatis.reflection;

import org.wing4j.litebatis.reflection.factory.DefaultObjectFactory;
import org.wing4j.litebatis.reflection.factory.DefaultObjectWrapperFactory;
import org.wing4j.litebatis.reflection.factory.DefaultReflectorFactory;
import org.wing4j.litebatis.reflection.factory.MetaObjectFactory;

public final class SystemMetaObject {

  public static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
  public static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
  public static final MetaObject NULL_META_OBJECT = MetaObjectFactory.forObject(NullObject.class, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());

  private SystemMetaObject() {
    // Prevent Instantiation of Static Class
  }

  private static class NullObject {
  }

  public static MetaObject forObject(Object object) {
    return MetaObjectFactory.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
  }

}