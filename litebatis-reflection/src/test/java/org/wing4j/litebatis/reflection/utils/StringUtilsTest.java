package org.wing4j.litebatis.reflection.utils;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by wing4j on 2017/7/28.
 */
public class StringUtilsTest {

    @Test
    public void testToFirstLowerCase() throws Exception {
        Assert.assertEquals("a", StringUtils.toFirstLowerCase("A"));
        Assert.assertEquals("a", StringUtils.toFirstLowerCase("a"));
        Assert.assertEquals("1", StringUtils.toFirstLowerCase("1"));
        Assert.assertEquals("ab", StringUtils.toFirstLowerCase("ab"));
        Assert.assertEquals("ab", StringUtils.toFirstLowerCase("Ab"));
        Assert.assertEquals("aB", StringUtils.toFirstLowerCase("AB"));
    }

    @Test
    public void testToFirstUpperCase() throws Exception {
        Assert.assertEquals("A", StringUtils.toFirstUpperCase("A"));
        Assert.assertEquals("A", StringUtils.toFirstUpperCase("a"));
        Assert.assertEquals("1", StringUtils.toFirstUpperCase("1"));
        Assert.assertEquals("Ab", StringUtils.toFirstUpperCase("ab"));
        Assert.assertEquals("Ab", StringUtils.toFirstUpperCase("Ab"));
        Assert.assertEquals("AB", StringUtils.toFirstUpperCase("aB"));
    }

    @Test
    public void testToCamelCase() throws Exception {
        Assert.assertEquals("thisIsTest", StringUtils.toCamelCase("this_is_test"));
    }
}