package org.wing4j.litebatis.mapping;

import org.wing4j.litebatis.executor.keygen.KeyGenerator;
import org.wing4j.litebatis.Configuration;

import java.util.List;

/**
 * Created by wing4j on 2017/5/15.
 * 已映射的语句对象
 */
public interface MappedStatement {
    /**
     *
     * @return
     */
    String getId();
    /**
     * 传入参数对象，进行SQL的绑定，获取受限制的SQL对象
     * @param parameterObject 参数对象
     * @return 受限制的SQL对象
     */
    BoundSql getBoundSql(Object parameterObject);

    /**
     * 获取绑定的配置对象
     * @return 配置对象
     */
    Configuration getConfiguration();

    /**
     * 获取绑定参数映射信息对象
     * @return 参数映射信息对象
     */
    ParameterMap getParameterMap();
    String[] getKeyProperties();
    String[] getKeyColumns();

    /**
     * 获取语句执行的超时时间
     * @return 超时时间
     */
    Integer getTimeout();


    Integer getFetchSize();

    /**
     * 获取该语句绑定的主键生成器
     * @return 主键生成器
     */
    KeyGenerator getKeyGenerator();

    /**
     * 获取语句结果集类型
     * @return 结果集类型
     */
    ResultSetType getResultSetType();

    /**
     * 获取语句的语句类型
     * @return 语句类型
     */
    StatementType getStatementType();

    /**
     * 获取构建该映射语句对象的资源路径，可以是xml，java类等
     * @return 资源路径
     */
    String getResource();

    boolean isFlushCacheRequired();

    List<ResultMap> getResultMaps();

    String[] getResulSets();

    boolean isResultOrdered();
}
