package org.wing4j.litebatis.reflection.factory;

import lombok.Builder;
import lombok.Getter;
import org.wing4j.litebatis.reflection.*;

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

    public static MetaObject forObject(Object object) {
        return forObject(object, OBJECT_FACTORY, OBJECT_WRAPPER_FACTORY, REFLECTOR_FACTORY);
    }

    public static MetaObject forObject(Object object, ObjectFactory objectFactory, ObjectWrapperFactory objectWrapperFactory, ReflectorFactory reflectorFactory) {
        if (object == null) {
            return SystemMetaObject.NULL_META_OBJECT;
        } else {
            return new DefaultMetaObject(object, objectFactory, objectWrapperFactory, reflectorFactory);
        }
    }
}