package org.wing4j.litebatis.reflection;

/**
 * Created by wing4j on 2017/5/28.
 */
public interface Reflector {
    boolean hasDefaultConstructor();
    Invoker getSetInvoker(String propertyName);
    Invoker getGetInvoker(String propertyName);
    Class<?> getSetterType(String propertyName);
    Class<?> getGetterType(String propertyName);
    String[] getGetablePropertyNames();
    String[] getSetablePropertyNames();
    boolean hasSetter(String propertyName);
    boolean hasGetter(String propertyName);
    String findPropertyName(String name);
}
