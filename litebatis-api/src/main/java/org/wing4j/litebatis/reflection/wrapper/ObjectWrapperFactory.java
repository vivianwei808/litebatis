package org.wing4j.litebatis.reflection.wrapper;

public interface ObjectWrapperFactory {

  boolean hasWrapperFor(Object object);
  
  ObjectWrapper getWrapperFor(Object object);
  
}
