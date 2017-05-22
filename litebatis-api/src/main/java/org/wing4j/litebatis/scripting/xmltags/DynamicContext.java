package org.wing4j.litebatis.scripting.xmltags;

import java.util.Map;

/**
 * Created by wing4j on 2017/5/21.
 */
public interface DynamicContext {
    Map<String, Object> getBindings();
    void bind(String name, Object value);
    void appendSql(String sql);
    String getSql();
    int getUniqueNumber();
}
