package org.wing4j.litebatis.binding;

import org.wing4j.litebatis.session.SqlSession;

/**
 * Created by 面试1 on 2017/6/5.
 */
public class MapperProxyFactory<T> {
    public T newInstance(SqlSession sqlSession) {
        final MapperProxy<T> mapperProxy = new MapperProxy<T>(sqlSession, mapperInterface, methodCache);
        return newInstance(mapperProxy);
    }
}
