package org.wing4j.litebatis.configuration;

import org.wing4j.litebatis.executor.Executor;
import org.wing4j.litebatis.mapping.MappedStatement;
import org.wing4j.litebatis.reflection.MetaObject;
import org.wing4j.litebatis.session.SqlSession;
import org.wing4j.litebatis.transaction.Transaction;

import java.util.Collection;

/**
 * Created by wing4j on 2017/5/17.
 */
public class DefaultConfiguration implements Configuration {
    @Override
    public Executor newExecutor(Transaction transaction) {
        return null;
    }

    @Override
    public void addMappedStatement(MappedStatement ms) {

    }

    @Override
    public Collection<MappedStatement> getMappedStatements() {
        return null;
    }

    @Override
    public MappedStatement getMappedStatement(String id) {
        return null;
    }

    @Override
    public <T> void addMapper(Class<T> type) {

    }

    @Override
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return null;
    }

    @Override
    public MetaObject newMetaObject(Object object) {
        return null;
    }
}
