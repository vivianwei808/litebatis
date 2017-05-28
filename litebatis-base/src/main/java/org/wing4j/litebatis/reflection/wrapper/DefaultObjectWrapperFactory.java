package org.wing4j.litebatis.reflection.wrapper;

import org.wing4j.litebatis.exception.ReflectionException;
import org.wing4j.litebatis.reflection.MetaObject;

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