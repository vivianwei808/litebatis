package org.wing4j.litebatis.reflection;

import org.wing4j.litebatis.reflection.property.PropertyTokenizer;

import java.util.List;

public interface ObjectWrapper {
    /**
     * 根据属性获取值
     * @param prop 属性符号迭代器
     * @return 值
     */
    Object get(PropertyTokenizer prop);

    /**
     * 设置属性值
     * @param prop 属性符号迭代器
     * @param value 值
     */
    void set(PropertyTokenizer prop, Object value);

    String findProperty(String name, boolean useCamelCaseMapping);

    String[] getGetterNames();

    String[] getSetterNames();

    Class<?> getSetterType(String name);

    Class<?> getGetterType(String name);

    boolean hasSetter(String name);

    boolean hasGetter(String name);

    boolean isCollection();

    void add(Object element);

    <E> void addAll(List<E> element);

}