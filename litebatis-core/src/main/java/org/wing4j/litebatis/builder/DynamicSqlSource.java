package org.wing4j.litebatis.builder;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.wing4j.orm.litebatis.Configuration;
import org.wing4j.orm.litebatis.mapping.BoundSql;
import org.wing4j.orm.litebatis.mapping.SqlSource;
import org.wing4j.orm.litebatis.scripting.xmltags.DynamicContext;
import org.wing4j.orm.litebatis.scripting.xmltags.SqlNode;

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
        DynamicContext context = new DynamicContext(configuration, parameterObject);
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
