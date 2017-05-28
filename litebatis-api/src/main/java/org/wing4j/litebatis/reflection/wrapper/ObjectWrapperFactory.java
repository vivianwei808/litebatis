package org.wing4j.litebatis.reflection.wrapper;

import org.wing4j.litebatis.reflection.MetaObject;

public interface ObjectWrapperFactory {

  boolean hasWrapperFor(Object object);
  
  ObjectWrapper getWrapperFor(MetaObject metaObject, Object object);
  
}
