package org.wing4j.litebatis.reflection;

/**
 * Created by wing4j on 2017/5/28.
 */
public abstract class MetaClass {
    public static MetaClass forClass(Class<?> type, ReflectorFactory reflectorFactory) {
//        return new MetaClass(type, reflectorFactory);
        return null;
    }
    public abstract boolean hasGetter(String name);
    public abstract Class<?> getGetterType(String name);
}
