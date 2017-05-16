package org.wing4j.orm.litebatis.api;

import java.util.Collection;

/**
 * Created by wing4j on 2017/5/16.
 */
public interface Configuration {
    Executor newExecutor(Transaction transaction);
    void addMappedStatement(MappedStatement ms);
    public Collection<MappedStatement> getMappedStatements();
    MappedStatement getMappedStatement(String id);
    <T> void addMapper(Class<T> type);
    <T> T getMapper(Class<T> type, SqlSession sqlSession);
    MetaObject newMetaObject(Object object);
}
