package org.wing4j.litebatis.reflection;


import org.wing4j.litebatis.reflection.MetaObject;
import org.wing4j.litebatis.reflection.ObjectWrapper;

public interface ObjectWrapperFactory {

    boolean hasWrapperFor(Object object);

    ObjectWrapper getWrapperFor(MetaObject metaObject, Object object);

}
