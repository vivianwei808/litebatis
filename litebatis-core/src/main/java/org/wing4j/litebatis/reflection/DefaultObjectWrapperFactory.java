package org.wing4j.litebatis.reflection;

import org.wing4j.litebatis.exception.ReflectionException;
import org.wing4j.litebatis.reflection.ObjectWrapper;
import org.wing4j.litebatis.reflection.ObjectWrapperFactory;

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