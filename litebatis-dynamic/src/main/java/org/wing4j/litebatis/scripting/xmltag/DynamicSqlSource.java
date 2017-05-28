package org.wing4j.litebatis.scripting.xmltag;

import lombok.AllArgsConstructor;
import org.wing4j.litebatis.session.Configuration;
import org.wing4j.litebatis.builder.SqlSourceBuilder;
import org.wing4j.litebatis.mapping.BoundSql;
import org.wing4j.litebatis.mapping.SqlSource;
import org.wing4j.litebatis.scripting.DefaultDynamicContext;
import org.wing4j.litebatis.scripting.DynamicContext;
import org.wing4j.litebatis.scripting.SqlNode;

import java.util.Map;

/**
 * Created by wing4j on 2017/5/21.
 */
@AllArgsConstructor
public class DynamicSqlSource implements SqlSource {
     Configuration configuration;
     SqlNode rootSqlNode;

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        DynamicContext context = new DefaultDynamicContext(configuration, parameterObject);
        //将含有mybatis占位符的SQL语句添加到上下文，如果是动态SQL，会根据参数对象的值进行动态添加
        rootSqlNode.apply(context);
        SqlSourceBuilder sqlSourceParser = new SqlSourceBuilder(configuration);
        Class<?> parameterType = parameterObject == null ? Object.class : parameterObject.getClass();
        SqlSource sqlSource = sqlSourceParser.parse(context.getSql(), parameterType, context.getBindings());
        BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
        for (Map.Entry<String, Object> entry : context.getBindings().entrySet()) {
            boundSql.setAdditionalParameter(entry.getKey(), entry.getValue());
        }
        return boundSql;
    }
}
