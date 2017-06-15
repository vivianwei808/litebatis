package org.wing4j.litebatis.binding;

import org.wing4j.litebatis.session.SqlSession;

import java.util.Collection;

/**
 * Created by wing4j on 2017/6/5.
 */
public interface MapperRegistry {
    <T> T getMapper(Class<T> type, SqlSession sqlSession);

    <T> boolean hasMapper(Class<T> type);

    <T> void addMapper(Class<T> type);

    void addMappers(String packageName, Class<?> superType);

    void addMappers(String packageName);

    Collection<Class<?>> getMappers();
}
