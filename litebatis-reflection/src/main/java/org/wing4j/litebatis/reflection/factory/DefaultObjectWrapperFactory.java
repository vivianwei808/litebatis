package org.wing4j.litebatis.reflection.factory;

import org.wing4j.litebatis.reflection.MetaObject;
import org.wing4j.litebatis.reflection.ObjectWrapper;
import org.wing4j.litebatis.reflection.ObjectWrapperFactory;
import org.wing4j.litebatis.reflection.exception.ReflectionException;

public class DefaultObjectWrapperFactory implements ObjectWrapperFactory {

  @Override
  public boolean hasWrapperFor(Object object) {
    return false;
  }

  @Override
  public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
    throw new ReflectionException("The DefaultObjectWrapperFactory should never be called to provide an ObjectWrapper.");
  }

}