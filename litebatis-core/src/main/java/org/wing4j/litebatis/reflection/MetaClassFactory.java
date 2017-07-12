package org.wing4j.litebatis.reflection;

/**
 * Created by wing4j on 2017/7/12.
 */
public class MetaClassFactory {
    static ReflectorFactory REFLECTOR_FACTORY = new DefaultReflectorFactory();

    public static MetaClass forClass(Class<?> type){
        return new DefaultMetaClass(type, REFLECTOR_FACTORY);
    }
}
