package org.wing4j.litebatis.type;

import org.junit.Assert;
import org.junit.Test;
import org.wing4j.litebatis.reflection.TypeAliasRegistry;

import static org.junit.Assert.*;

/**
 * Created by wing4j on 2017/7/12.
 */
public class DefaultTypeAliasRegistryTest {

    @Test
    public void testGetInstance() throws Exception {
        TypeAliasRegistry typeAliasRegistry = TypeAliasRegistryFactory.getInstance();
        Assert.assertNotNull(typeAliasRegistry);
    }

    @Test
    public void testResolveAlias() throws Exception {
        TypeAliasRegistry typeAliasRegistry = TypeAliasRegistryFactory.getInstance();
        Class clazz = typeAliasRegistry.resolveAlias("int");
        Assert.assertEquals(Integer.class, clazz);
    }
}