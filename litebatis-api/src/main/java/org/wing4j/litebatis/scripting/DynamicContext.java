package org.wing4j.litebatis.scripting;

import java.util.Map;

/**
 * Created by wing4j on 2017/5/21.
 * 动态上下文
 */
public interface DynamicContext {
    /**
     * 绑定参数
     * @return
     */
    Map<String, Object> getBindings();

    /**
     * 绑定参数
     * @param name
     * @param value
     */
    void bind(String name, Object value);

    /**
     * 追加SQL
     * @param sql
     */
    void appendSql(String sql);

    /**
     * 输出SQL
     * @return
     */
    String getSql();
    int getUniqueNumber();
}
