package org.wing4j.litebatis.reflection;

import lombok.Getter;
import org.wing4j.litebatis.reflection.property.PropertyTokenizer;
import org.wing4j.litebatis.reflection.wrapper.BeanWrapper;
import org.wing4j.litebatis.reflection.wrapper.CollectionWrapper;
import org.wing4j.litebatis.reflection.wrapper.MapWrapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by wing4j on 2017/6/19.
 */
public class DefaultMetaObject implements MetaObject {
    @Getter
    Object originalObject;
    @Getter
    ObjectWrapper objectWrapper;
    @Getter
    ObjectFactory objectFactory;
    @Getter
    ObjectWrapperFactory objectWrapperFactory;
    @Getter
    ReflectorFactory reflectorFactory;

//    public static MetaObject forObject(Object object, ObjectFactory objectFactory, ObjectWrapperFactory objectWrapperFactory, ReflectorFactory reflectorFactory) {
//        if (object == null) {
//            return null;
//        } else {
//            return new DefaultMetaObject(object, objectFactory, objectWrapperFactory, reflectorFactory);
//        }
//    }

    public DefaultMetaObject(Object object, ObjectFactory objectFactory, ObjectWrapperFactory objectWrapperFactory, ReflectorFactory reflectorFactory) {
        this.originalObject = object;
        this.objectFactory = objectFactory;
        this.objectWrapperFactory = objectWrapperFactory;
        this.reflectorFactory = reflectorFactory;

        if (object instanceof ObjectWrapper) {
            this.objectWrapper = (ObjectWrapper) object;
        } else if (objectWrapperFactory.hasWrapperFor(object)) {
            this.objectWrapper = objectWrapperFactory.getWrapperFor(this, object);
        } else if (object instanceof Map) {
            this.objectWrapper = new MapWrapper(this, (Map) object);
        } else if (object instanceof Collection) {
            this.objectWrapper = new CollectionWrapper(this, (Collection) object);
        } else {
            this.objectWrapper = new BeanWrapper(this, object);
        }
    }

    @Override
    public boolean hasGetter(String fieldName) {
        return objectWrapper.hasGetter(fieldName);
    }

    @Override
    public boolean hasSetter(String fieldName) {
        return objectWrapper.hasSetter(fieldName);
    }

    @Override
    public void setValue(String fieldName, Object value) {
        PropertyTokenizer prop = new PropertyTokenizer(fieldName);
        if (prop.hasNext()) {
            MetaObject metaValue = metaObjectForProperty(prop.getIndexedName());
            if (metaValue == DefaultObjectFactory.NULL_META_OBJECT) {
                if (value == null && prop.getChildren() != null) {
                    // don't instantiate child path if value is null
                    return;
                } else {
                    metaValue = objectWrapper.instantiatePropertyValue(fieldName, prop, objectFactory);
                }
            }
            metaValue.setValue(prop.getChildren(), value);
        } else {
            objectWrapper.set(prop, value);
        }
    }

    @Override
    public <T> T getValue(String fieldName) {
        PropertyTokenizer prop = new PropertyTokenizer(fieldName);
        if (prop.hasNext()) {
            MetaObject metaValue = metaObjectForProperty(prop.getIndexedName());
            if (metaValue == null) {
                return null;
            } else {
                return metaValue.getValue(prop.getChildren());
            }
        } else {
            return (T) objectWrapper.get(prop);
        }
    }

    @Override
    public Class<?> getSetterType(String fieldName) {
        return null;
    }

    @Override
    public Class<?> getGetterType(String fieldName) {
        return null;
    }

    @Override
    public String[] getGetterNames() {
        return objectWrapper.getGetterNames();
    }

    @Override
    public String[] getSetterNames() {
        return objectWrapper.getSetterNames();
    }

    @Override
    public String findProperty(String propName, boolean useCamelCaseMapping) {
        return null;
    }

    public MetaObject metaObjectForProperty(String name) {
        Object value = getValue(name);
        return MetaObjectFactory.forObject(value, objectFactory, objectWrapperFactory, reflectorFactory);
    }

    @Override
    public Object getOriginalObject() {
        return null;
    }

    @Override
    public <E> void addAll(List<E> list) {

    }

    @Override
    public void add(Object element) {

    }
}
