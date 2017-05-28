package org.wing4j.litebatis.type;

/**
 * Created by wing4j on 2017/5/22.
 */
public interface TypeHandlerRegistry {
    TypeHandler<?> getMappingTypeHandler(Class<? extends TypeHandler<?>> handlerType);
    <T> TypeHandler<T> getInstance(Class<?> javaTypeClass, Class<?> typeHandlerClass);
    boolean hasTypeHandler(Class<?> javaType);
    <T> TypeHandler<T> getTypeHandler(Class<T> type);
    TypeHandler<?> getTypeHandler(JdbcType jdbcType);
    <T> TypeHandler<T> getTypeHandler(Class<T> type, JdbcType jdbcType);
}
