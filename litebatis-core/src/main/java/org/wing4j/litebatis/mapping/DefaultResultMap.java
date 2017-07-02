package org.wing4j.litebatis.mapping;

import lombok.Data;
import org.wing4j.litebatis.Configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by wing4j on 2017/7/2.
 */
@Data
public class DefaultResultMap implements ResultMap{
    Configuration configuration;
    String id;
    Class type;
    final Set<String> mappedColumns  = new HashSet();
    final List<ResultMapping> resultMappings = new ArrayList();
    Boolean autoMapping;
    final List<ResultMapping> propertyResultMappings = new ArrayList();
    final List<ResultMapping> idResultMappings = new ArrayList();
    final List<ResultMapping> constructorResultMappings = new ArrayList();

    @Override
    public boolean hasNestedResultMaps() {
        return false;
    }
}
