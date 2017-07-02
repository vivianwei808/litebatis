package org.wing4j.litebatis.mapping;

import lombok.Data;
import org.wing4j.litebatis.type.JdbcType;
import org.wing4j.litebatis.type.TypeHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by wing4j on 2017/7/2.
 */
@Data
public class DefaultResultMapping implements ResultMapping{
    String property;
    String column;
    Class javaType;
    JdbcType jdbcType;
    TypeHandler typeHandler;
    boolean lazy;
    String nestedResultMapId;
    String resultSet;
    String nestedQueryId;
    boolean compositeResult;
    String foreignColumn;
    String columnPrefix;
    final Set<String> notNullColumns = new HashSet();
    final List<ResultMapping> composites = new ArrayList();
}
