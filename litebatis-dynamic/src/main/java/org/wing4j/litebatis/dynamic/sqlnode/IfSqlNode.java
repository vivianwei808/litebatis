package org.wing4j.litebatis.dynamic.sqlnode;

import org.wing4j.litebatis.scripting.DynamicContext;
import org.wing4j.litebatis.scripting.SqlNode;
import org.wing4j.litebatis.dynamic.ExpressionEvaluator;

/**
 * Created by wing4j on 2017/5/28.
 * IF表达式满足条件则添加内容到上下文
 */
public class IfSqlNode implements SqlNode {
    ExpressionEvaluator evaluator;
    String test;
    SqlNode contents;

    public IfSqlNode(SqlNode contents, String test) {
        this.test = test;
        this.contents = contents;
        this.evaluator = new ExpressionEvaluator();
    }

    @Override
    public boolean apply(DynamicContext context) {
        if (evaluator.evaluateBoolean(test, context.getBindings())) {
            contents.apply(context);
            return true;
        }
        return false;
    }
}
