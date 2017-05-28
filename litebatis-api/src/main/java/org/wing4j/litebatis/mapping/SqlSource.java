package org.wing4j.litebatis.mapping;

/**
 * 负责根据用户传递的parameterObject，动态地生成SQL语句，将信息封装到BoundSql对象中，并返回
 */
public interface SqlSource {
    /**
     * 根据送入的参数对象进行参数值绑定，绑定到BoundSql
     *
     * @param parameterObject 参数对象
     * @return 受限制的SQL对象
     */
    BoundSql getBoundSql(Object parameterObject);
}
