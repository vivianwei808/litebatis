package org.wing4j.litebatis.type;

/**
 * Created by wing4j on 2017/5/22.
 */
public interface TypeHandlerRegistry {
    TypeHandler<?> getMappingTypeHandler(Class<? extends TypeHandler<?>> handlerType);
    <T> TypeHandler<T> getInstance(Class<?> javaTypeClass, Class<?> typeHandlerClass);
}
