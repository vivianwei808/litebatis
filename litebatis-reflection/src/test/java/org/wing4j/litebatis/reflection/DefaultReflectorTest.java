package org.wing4j.litebatis.reflection;

import org.junit.Assert;
import org.junit.Test;
import org.wing4j.litebatis.reflection.invoker.SetFieldInvoker;
import org.wing4j.litebatis.reflection.wrapper.RichType;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * Created by 面试1 on 2017/7/27.
 */
public class DefaultReflectorTest {

    @Test
    public void testGetSetterType() throws Exception {
        Reflector reflector = new DefaultReflector(Section.class);
        Assert.assertEquals(Long.class, reflector.getSetterType("id"));
        System.out.println(Arrays.toString(reflector.getSettablePropertyNames()));
        Assert.assertEquals(2, reflector.getSettablePropertyNames().length);
    }

    @Test
    public void testGetGetterType() throws Exception {
        Reflector reflector = new DefaultReflector(Section.class);
        Assert.assertEquals(Long.class, reflector.getGetterType("id"));
        Assert.assertEquals(String.class, reflector.getGetterType("name"));
        Assert.assertEquals(2, reflector.getGettablePropertyNames().length);
    }

    @Test
    public void shouldNotGetClass() throws Exception {
        Reflector reflector = new DefaultReflector(Section.class);
        Assert.assertFalse(reflector.hasGetter("class"));
    }

    @Test
    public void testHasSetter() throws Exception {
        Reflector reflector = new DefaultReflector(Section.class);
        Assert.assertTrue(reflector.hasSetter("name"));
    }

    @Test
    public void testHasGetter() throws Exception {
        Reflector reflector = new DefaultReflector(Section.class);
        Assert.assertTrue(reflector.hasGetter("name"));
    }

    static interface Entity<T> {
        T getId();

        void setId(T id);
    }

    static abstract class AbstractEntity implements Entity<Long> {

        private Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        final String name;

        public AbstractEntity(String name, Long id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }
    }

    static class Section extends AbstractEntity implements Entity<Long> {
        public Section(String name, Long id) {
            super(name, id);
        }
    }

    @Test
    public void test1() throws InvocationTargetException, IllegalAccessException {
        Reflector reflector = new DefaultReflector(RichType.class);
        Invoker setFieldInvoker = reflector.getSetInvoker("richField");
        Invoker getFieldInvoker = reflector.getGetInvoker("richField");
        RichType richType = new RichType();
        String richField = getFieldInvoker.invoke(richType);
        Assert.assertEquals(null, richField);
        setFieldInvoker.invoke(richType, "123");
        richField = getFieldInvoker.invoke(richType);
        System.out.println(richField);
        Assert.assertEquals("123", richField);
    }
}