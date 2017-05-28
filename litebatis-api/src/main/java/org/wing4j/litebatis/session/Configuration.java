package org.wing4j.litebatis.session;

import org.wing4j.litebatis.cache.Cache;
import org.wing4j.litebatis.executor.Executor;
import org.wing4j.litebatis.mapping.MappedStatement;
import org.wing4j.litebatis.reflection.MetaObject;
import org.wing4j.litebatis.session.SqlSession;
import org.wing4j.litebatis.transaction.Transaction;
import org.wing4j.litebatis.type.JdbcType;
import org.wing4j.litebatis.type.TypeAliasRegistry;
import org.wing4j.litebatis.type.TypeHandlerRegistry;

import java.util.Collection;
import java.util.Properties;

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
    String getDatabaseId();
    Properties getVariables();
    JdbcType getJdbcTypeForNull();
    Cache getCache(String id);
    TypeAliasRegistry getTypeAliasRegistry();

    TypeHandlerRegistry getTypeHandlerRegistry();
}
