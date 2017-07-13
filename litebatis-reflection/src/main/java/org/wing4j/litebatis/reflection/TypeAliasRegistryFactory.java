package org.wing4j.litebatis.reflection;

/**
 * Created by wing4j on 2017/7/12.
 */
public class TypeAliasRegistryFactory {
    private static final TypeAliasRegistry TYPE_ALIAS_REGISTRY = new DefaultTypeAliasRegistry();
    public static TypeAliasRegistry getInstance(){
        return TYPE_ALIAS_REGISTRY;
    }
}
