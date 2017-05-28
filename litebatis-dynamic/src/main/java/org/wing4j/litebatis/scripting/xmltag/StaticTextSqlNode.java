package org.wing4j.litebatis.scripting.xmltag;

import lombok.AllArgsConstructor;
import org.wing4j.litebatis.scripting.DynamicContext;
import org.wing4j.litebatis.scripting.SqlNode;

/**
 * Created by wing4j on 2017/5/28.
 */
@AllArgsConstructor
public class StaticTextSqlNode implements SqlNode {
    String text;
    @Override
    public boolean apply(DynamicContext context) {
        context.appendSql(text);
        return true;
    }
}
