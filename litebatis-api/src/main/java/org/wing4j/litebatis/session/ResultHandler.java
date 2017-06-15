package org.wing4j.litebatis.session;

/**
 * 结果处理器
 * @param <T>
 */
public interface ResultHandler<T> {
    /**
     * 将结果上下文中的JDBC结果转换处理
     * @param resultContext 结果上下文
     */
    void handleResult(ResultContext<? extends T> resultContext);
}
