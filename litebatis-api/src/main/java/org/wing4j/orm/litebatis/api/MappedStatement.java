package org.wing4j.orm.litebatis.api;

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
}
