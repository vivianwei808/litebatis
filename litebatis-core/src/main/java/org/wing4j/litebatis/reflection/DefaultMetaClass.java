package org.wing4j.litebatis.reflection;

import org.wing4j.litebatis.reflection.invoker.GetFieldInvoker;
import org.wing4j.litebatis.reflection.invoker.MethodInvoker;
import org.wing4j.litebatis.reflection.property.PropertyTokenizer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Created by wing4j on 2017/6/19.
 */
public class DefaultMetaClass implements MetaClass{
    private ReflectorFactory reflectorFactory;
    private Reflector reflector;

    private DefaultMetaClass(Class<?> type, ReflectorFactory reflectorFactory) {
        this.reflectorFactory = reflectorFactory;
        this.reflector = reflectorFactory.findForClass(type);
    }
    public static MetaClass forClass(Class<?> type, ReflectorFactory reflectorFactory) {
        return new DefaultMetaClass(type, reflectorFactory);
    }
    @Override
    public boolean hasGetter(String fieldName) {
        PropertyTokenizer prop = new PropertyTokenizer(fieldName);
        if (prop.hasNext()) {
            if (reflector.hasSetter(prop.getName())) {
                MetaClass metaProp = metaClassForProperty(prop.getName());
                return metaProp.hasSetter(prop.getChildren());
            } else {
                return false;
            }
        } else {
            return reflector.hasSetter(prop.getName());
        }
    }

    @Override
    public boolean hasSetter(String fieldName) {
        PropertyTokenizer prop = new PropertyTokenizer(fieldName);
        if (prop.hasNext()) {
            if (reflector.hasGetter(prop.getName())) {
                MetaClass metaProp = metaClassForProperty(prop);
                return metaProp.hasGetter(prop.getChildren());
            } else {
                return false;
            }
        } else {
            return reflector.hasGetter(prop.getName());
        }
    }

    @Override
    public String[] getGetterNames() {
        return reflector.getGetablePropertyNames();
    }

    @Override
    public String[] getSetterNames() {
        return reflector.getSetablePropertyNames();
    }

    @Override
    public String findProperty(String name, boolean useCamelCaseMapping) {
        return null;
    }

    @Override
    public Invoker getGetInvoker(String fieldName) {
        return reflector.getGetInvoker(fieldName);
    }

    @Override
    public Invoker getSetInvoker(String fieldName) {
        return reflector.getSetInvoker(fieldName);
    }

    @Override
    public Class<?> getGetterType(String fieldName) {
        PropertyTokenizer prop = new PropertyTokenizer(fieldName);
        if (prop.hasNext()) {
            MetaClass metaProp = metaClassForProperty(prop);
            return metaProp.getGetterType(prop.getChildren());
        }
        // issue #506. Resolve the type inside a Collection Object
        return getGetterType(prop);
    }

    @Override
    public boolean hasDefaultConstructor() {
        return reflector.hasDefaultConstructor();
    }
    public MetaClass metaClassForProperty(String name) {
        Class<?> propType = reflector.getGetterType(name);
        return forClass(propType, reflectorFactory);
    }

    private MetaClass metaClassForProperty(PropertyTokenizer prop) {
        Class<?> propType = getGetterType(prop);
        return forClass(propType, reflectorFactory);
    }

    private Class<?> getGetterType(PropertyTokenizer prop) {
        Class<?> type = reflector.getGetterType(prop.getName());
        if (prop.getIndex() != null && Collection.class.isAssignableFrom(type)) {
            Type returnType = getGenericGetterType(prop.getName());
            if (returnType instanceof ParameterizedType) {
                Type[] actualTypeArguments = ((ParameterizedType) returnType).getActualTypeArguments();
                if (actualTypeArguments != null && actualTypeArguments.length == 1) {
                    returnType = actualTypeArguments[0];
                    if (returnType instanceof Class) {
                        type = (Class<?>) returnType;
                    } else if (returnType instanceof ParameterizedType) {
                        type = (Class<?>) ((ParameterizedType) returnType).getRawType();
                    }
                }
            }
        }
        return type;
    }

    private Type getGenericGetterType(String propertyName) {
        try {
            Invoker invoker = reflector.getGetInvoker(propertyName);
            if (invoker instanceof MethodInvoker) {
                Field _method = MethodInvoker.class.getDeclaredField("method");
                _method.setAccessible(true);
                Method method = (Method) _method.get(invoker);
                return method.getGenericReturnType();
            } else if (invoker instanceof GetFieldInvoker) {
                Field _field = GetFieldInvoker.class.getDeclaredField("field");
                _field.setAccessible(true);
                Field field = (Field) _field.get(invoker);
                return field.getGenericType();
            }
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        }
        return null;
    }

}
