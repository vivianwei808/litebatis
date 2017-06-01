package org.wing4j.litebatis.reflection;

import org.wing4j.litebatis.reflection.factory.ObjectFactory;
import org.wing4j.litebatis.reflection.wrapper.ObjectWrapperFactory;

import java.util.List;

/**
 * Created by wing4j on 2017/5/16.
 * 用于对实体类进行包装，提供字段、方法的元信息
 */
public abstract class MetaObject {
    public abstract boolean hasGetter(String name);

    public abstract void setValue(String name, Object value);

    public abstract Object getValue(String name);
    public abstract Class<?> getSetterType(String name);
    public abstract boolean hasSetter(String name);
    public abstract Class<?> getGetterType(String name);
    public static MetaObject forObject(Object object, ObjectFactory objectFactory, ObjectWrapperFactory objectWrapperFactory, ReflectorFactory reflectorFactory) {
        return null;
    }
    public abstract Object getOriginalObject();
    public abstract <E> void addAll(List<E> list);
}
