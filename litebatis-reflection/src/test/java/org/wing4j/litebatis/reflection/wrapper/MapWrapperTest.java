package org.wing4j.litebatis.reflection.wrapper;

import org.junit.Test;
import org.wing4j.litebatis.reflection.MetaObjectFactory;
import org.wing4j.litebatis.reflection.property.PropertyTokenizer;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by wing4j on 2017/7/14.
 */
public class MapWrapperTest {

    @Test
    public void testGet() throws Exception {

    }

    @Test
    public void testSet() throws Exception {

    }

    @Test
    public void testFindProperty() throws Exception {

    }

    @Test
    public void testGetGetterNames() throws Exception {
        Map map = new HashMap();
        map.put("key1","val1");
        map.put("key2","val2");
        MapWrapper mapWrapper = new MapWrapper(MetaObjectFactory.forObject(map), map);
        System.out.println(mapWrapper.getGetterNames()[0]);
        System.out.println(mapWrapper.getSetterNames()[1]);
        PropertyTokenizer propertyTokenizer = new PropertyTokenizer("map[0]");
        Object val = mapWrapper.get(propertyTokenizer);
    }

    @Test
    public void testGetSetterNames() throws Exception {

    }

    @Test
    public void testGetSetterType() throws Exception {

    }

    @Test
    public void testGetGetterType() throws Exception {

    }

    @Test
    public void testHasSetter() throws Exception {

    }

    @Test
    public void testHasGetter() throws Exception {

    }

    @Test
    public void testIsCollection() throws Exception {

    }

    @Test
    public void testAdd1() throws Exception {

    }

    @Test
    public void testAddAll1() throws Exception {

    }

    @Test
    public void testGetNativeObject() throws Exception {

    }
}