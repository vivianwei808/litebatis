package org.wing4j.litebatis.reflection;

import org.junit.Test;
import org.wing4j.litebatis.reflection.MetaObjectFactory;
import org.wing4j.litebatis.reflection.ObjectWrapper;
import org.wing4j.litebatis.reflection.property.DemoBean;
import org.wing4j.litebatis.reflection.property.PropertyTokenizer;
import org.wing4j.litebatis.reflection.wrapper.BeanWrapper;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by wing4j on 2017/7/12.
 */
public class BeanWrapperTest {

    @Test
    public void testGet() throws Exception {
        DemoBean demoBean = new DemoBean();
        demoBean.setName("wwwww");
        ObjectWrapper objectWrapper = new BeanWrapper(MetaObjectFactory.forObject(demoBean), demoBean);
        System.out.println(Arrays.asList(objectWrapper.getGetterNames()));
        System.out.println(Arrays.asList(objectWrapper.getSetterNames()));
        PropertyTokenizer prop = new PropertyTokenizer("name");
        Object value = objectWrapper.get(prop);
        System.out.println(value);
        objectWrapper.set(prop, "yyyy");
        objectWrapper.set(new PropertyTokenizer("age"), 20);
        value = objectWrapper.get(prop);
        System.out.println(value);
        System.out.println(demoBean.getName());
        System.out.println(demoBean.getAge());
    }

    @Test
    public void testSet() throws Exception {

    }
}