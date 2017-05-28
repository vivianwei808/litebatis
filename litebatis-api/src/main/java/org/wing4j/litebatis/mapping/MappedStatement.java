package org.wing4j.litebatis.mapping;

import org.wing4j.litebatis.executor.keygen.KeyGenerator;
import org.wing4j.litebatis.session.Configuration;

/**
 * Created by wing4j on 2017/5/15.
 */
public interface MappedStatement {
    /**
     * 传入参数对象，进行SQL的绑定，获取受限制的SQL对象
     * @param parameterObject 参数对象
     * @return 受限制的SQL对象
     */
    BoundSql getBoundSql(Object parameterObject);
    Configuration getConfiguration();
    ParameterMap getParameterMap();
    String[] getKeyProperties();
    String[] getKeyColumns();
    Integer getTimeout();
    Integer getFetchSize();
    KeyGenerator getKeyGenerator();
    ResultSetType getResultSetType();
    StatementType getStatementType();
    String getResource();
}
