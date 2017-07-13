package org.wing4j.litebatis.reflection.wrapper;

import org.wing4j.litebatis.reflection.MetaObject;
import org.wing4j.litebatis.reflection.property.PropertyTokenizer;

import java.util.List;
import java.util.Map;

public class MapWrapper extends BaseWrapper {

    private Map<String, Object> map;

    public MapWrapper(MetaObject metaObject, Map<String, Object> map) {
        super(metaObject);
        this.map = map;
    }

    @Override
    public Object get(PropertyTokenizer prop) {
        if (prop.getIndex() != null) {
            Object collection = resolveCollection(prop, map);
            return getCollectionValue(prop, collection);
        } else {
            return map.get(prop.getName());
        }
    }

    @Override
    public void set(PropertyTokenizer prop, Object value) {
        if (prop.getIndex() != null) {
            Object collection = resolveCollection(prop, map);
            setCollectionValue(prop, collection, value);
        } else {
            map.put(prop.getName(), value);
        }
    }

    @Override
    public String findProperty(String name, boolean useCamelCaseMapping) {
        return name;
    }

    @Override
    public String[] getGetterNames() {
        return map.keySet().toArray(new String[map.keySet().size()]);
    }

    @Override
    public String[] getSetterNames() {
        return map.keySet().toArray(new String[map.keySet().size()]);
    }

    @Override
    public Class<?> getSetterType(String name) {
        PropertyTokenizer prop = new PropertyTokenizer(name);
//    if (prop.hasNext()) {
//      MetaObject metaValue = metaObject.metaObjectForProperty(prop.getIndexedName());
//      if (metaValue == SystemMetaObject.NULL_META_OBJECT) {
//        return Object.class;
//      } else {
//        return metaValue.getSetterType(prop.getChildren());
//      }
//    } else {
//      if (map.get(name) != null) {
//        return map.get(name).getClass();
//      } else {
//        return Object.class;
//      }
//    }
        return null;
    }

    @Override
    public Class<?> getGetterType(String name) {
//    PropertyTokenizer prop = new PropertyTokenizer(name);
//    if (prop.hasNext()) {
//      MetaObject metaValue = metaObject.metaObjectForProperty(prop.getIndexedName());
//      if (metaValue == SystemMetaObject.NULL_META_OBJECT) {
//        return Object.class;
//      } else {
//        return metaValue.getGetterType(prop.getChildren());
//      }
//    } else {
//      if (map.get(name) != null) {
//        return map.get(name).getClass();
//      } else {
//        return Object.class;
//      }
//    }
        return null;
    }

    @Override
    public boolean hasSetter(String name) {
        return true;
    }

    @Override
    public boolean hasGetter(String name) {
//    PropertyTokenizer prop = new PropertyTokenizer(name);
//    if (prop.hasNext()) {
//      if (map.containsKey(prop.getIndexedName())) {
//        MetaObject metaValue = metaObject.metaObjectForProperty(prop.getIndexedName());
//        if (metaValue == SystemMetaObject.NULL_META_OBJECT) {
//          return true;
//        } else {
//          return metaValue.hasGetter(prop.getChildren());
//        }
//      } else {
//        return false;
//      }
//    } else {
//      return map.containsKey(prop.getName());
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
    public <E> void addAll(List<E> element) {
        throw new UnsupportedOperationException();
    }

}