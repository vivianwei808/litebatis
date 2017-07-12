package org.wing4j.litebatis.reflection;

import lombok.Builder;
import lombok.Getter;

/**
 * Created by wing4j on 2017/7/12.
 */
@Builder
public class MetaObjectFactory {
    @Getter
    final static ObjectFactory OBJECT_FACTORY = new DefaultObjectFactory();
    @Getter
    final static ObjectWrapperFactory OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    @Getter
    final static ReflectorFactory REFLECTOR_FACTORY = new DefaultReflectorFactory();

    public static MetaObject forObject(Object object){
        if (object == null) {
            return null;
        } else {
            return new DefaultMetaObject(object, OBJECT_FACTORY, OBJECT_WRAPPER_FACTORY, REFLECTOR_FACTORY);
        }
    }
    public static MetaObject forObject(Object object, ObjectFactory objectFactory, ObjectWrapperFactory objectWrapperFactory, ReflectorFactory reflectorFactory){
        if (object == null) {
            return null;
        } else {
            return new DefaultMetaObject(object, objectFactory, objectWrapperFactory, reflectorFactory);
        }
    }
}