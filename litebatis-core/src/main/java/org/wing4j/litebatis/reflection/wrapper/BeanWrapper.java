package org.wing4j.litebatis.reflection.wrapper;

import org.wing4j.litebatis.exception.ReflectionException;
import org.wing4j.litebatis.reflection.Invoker;
import org.wing4j.litebatis.reflection.MetaClass;
import org.wing4j.litebatis.reflection.MetaClassFactory;
import org.wing4j.litebatis.reflection.MetaObject;
import org.wing4j.litebatis.reflection.property.PropertyTokenizer;

import java.util.List;

public class BeanWrapper extends BaseWrapper {

    private Object object;
    private MetaClass metaClass;

    public BeanWrapper(MetaObject metaObject, Object object) {
        super(metaObject);
        this.object = object;
        this.metaClass = MetaClassFactory.forClass(object.getClass());
    }

    @Override
    public Object get(PropertyTokenizer prop) {
        if (prop.getIndex() != null) {
            Object collection = resolveCollection(prop, object);
            return getCollectionValue(prop, collection);
        } else {
            return getBeanProperty(prop, object);
        }
    }

    private Object getBeanProperty(PropertyTokenizer prop, Object object) {
        try {
            Invoker method = metaClass.getGetInvoker(prop.getName());
            try {
                return method.invoke(object, NO_ARGUMENTS);
            } catch (Throwable t) {
                throw t;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Throwable t) {
            throw new ReflectionException("Could not get property '" + prop.getName() + "' from " + object.getClass() + ".  Cause: " + t.toString(), t);
        }
    }

    @Override
    public void set(PropertyTokenizer prop, Object value) {
        if (prop.getIndex() != null) {
            Object collection = resolveCollection(prop, object);
            setCollectionValue(prop, collection, value);
        } else {
//            setBeanProperty(prop, object, value);
        }
    }

    @Override
    public String findProperty(String name, boolean useCamelCaseMapping) {
        return metaClass.findProperty(name, useCamelCaseMapping);
    }

    @Override
    public String[] getGetterNames() {
        return metaClass.getGetterNames();
    }

    @Override
    public String[] getSetterNames() {
        return metaClass.getSetterNames();
    }

    @Override
    public Class<?> getSetterType(String name) {
//        PropertyTokenizer prop = new PropertyTokenizer(name);
//        if (prop.hasNext()) {
//            MetaObject metaValue = metaObject.metaObjectForProperty(prop.getIndexedName());
//            if (metaValue == SystemMetaObject.NULL_META_OBJECT) {
//                return metaClass.getSetterType(name);
//            } else {
//                return metaValue.getSetterType(prop.getChildren());
//            }
//        } else {
//            return metaClass.getSetterType(name);
//        }
        return null;
    }

    @Override
    public Class<?> getGetterType(String name) {
//    PropertyTokenizer prop = new PropertyTokenizer(name);
//    if (prop.hasNext()) {
//      MetaObject metaValue = metaObject.metaObjectForProperty(prop.getIndexedName());
//      if (metaValue == SystemMetaObject.NULL_META_OBJECT) {
//        return metaClass.getGetterType(name);
//      } else {
//        return metaValue.getGetterType(prop.getChildren());
//      }
//    } else {
//      return metaClass.getGetterType(name);
//    }
        return null;
    }

    @Override
    public boolean hasSetter(String name) {
//    PropertyTokenizer prop = new PropertyTokenizer(name);
//    if (prop.hasNext()) {
//      if (metaClass.hasSetter(prop.getIndexedName())) {
//        MetaObject metaValue = metaObject.metaObjectForProperty(prop.getIndexedName());
//        if (metaValue == SystemMetaObject.NULL_META_OBJECT) {
//          return metaClass.hasSetter(name);
//        } else {
//          return metaValue.hasSetter(prop.getChildren());
//        }
//      } else {
//        return false;
//      }
//    } else {
//      return metaClass.hasSetter(name);
//    }
        return false;
    }

    @Override
    public boolean hasGetter(String name) {
//    PropertyTokenizer prop = new PropertyTokenizer(name);
//    if (prop.hasNext()) {
//      if (metaClass.hasGetter(prop.getIndexedName())) {
//        MetaObject metaValue = metaObject.metaObjectForProperty(prop.getIndexedName());
//        if (metaValue == SystemMetaObject.NULL_META_OBJECT) {
//          return metaClass.hasGetter(name);
//        } else {
//          return metaValue.hasGetter(prop.getChildren());
//        }
//      } else {
//        return false;
//      }
//    } else {
//      return metaClass.hasGetter(name);
//    }
        return false;
    }

    @Override
    public boolean isCollection() {
        return false;
    }

    @Override
    public void add(Object element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <E> void addAll(List<E> list) {
        throw new UnsupportedOperationException();
    }

}