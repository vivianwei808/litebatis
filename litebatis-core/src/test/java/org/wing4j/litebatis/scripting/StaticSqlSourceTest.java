package org.wing4j.litebatis.scripting;

import lombok.Data;
import org.junit.Test;
import org.wing4j.litebatis.Configuration;
import org.wing4j.litebatis.configuration.DefaultConfiguration;
import org.wing4j.litebatis.mapping.BoundSql;
import org.wing4j.litebatis.mapping.DefaultParameterMapping;
import org.wing4j.litebatis.mapping.ParameterMapping;
import org.wing4j.litebatis.mapping.SqlSource;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by wing4j on 2017/7/10.
 */
public class StaticSqlSourceTest {
    @Data
    static class Bean1 {
        String serialNo;
        String name;
    }

    @Test
    public void testGetBoundSql() throws Exception {
        Configuration configuration = new DefaultConfiguration();
        List<ParameterMapping> parameterMappings = new ArrayList<>();
        ParameterMapping parameterMapping1 = DefaultParameterMapping.builder(configuration, "serialNo", String.class)
                .build();
        ParameterMapping parameterMapping2 = DefaultParameterMapping.builder(configuration, "name", String.class)
                .build();
        parameterMappings.add(parameterMapping1);
        parameterMappings.add(parameterMapping2);
        SqlSource sqlSource = new StaticSqlSource(configuration, "select * form tab1 where serialNo = ? and name = ?", parameterMappings);
        Bean1 bean1 = new Bean1();
        bean1.setSerialNo(UUID.randomUUID().toString());
        bean1.setName("this is a test");
        BoundSql boundSql = sqlSource.getBoundSql(bean1);
        boundSql.getSql();
        String name = boundSql.getParameter("name");
        System.out.println(name);
    }
}