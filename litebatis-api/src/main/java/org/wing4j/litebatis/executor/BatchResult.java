package org.wing4j.litebatis.executor;

import org.wing4j.litebatis.mapping.MappedStatement;

import java.util.List;

/**
 * Created by wing4j on 2017/5/28.
 *
 */
public interface BatchResult {
    MappedStatement getMappedStatement();

    String getSql();

    List<Object> getParameterObjects();

    int[] getUpdateCounts();

    void setUpdateCounts(int[] updateCounts);

    void addParameterObject(Object parameterObject);

}
