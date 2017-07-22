package org.wing4j.litebatis.reflection;

import org.wing4j.litebatis.reflection.Reflector;

public interface ReflectorFactory {

    boolean isClassCacheEnabled();

    void setClassCacheEnabled(boolean classCacheEnabled);

    Reflector findForClass(Class<?> type);
}