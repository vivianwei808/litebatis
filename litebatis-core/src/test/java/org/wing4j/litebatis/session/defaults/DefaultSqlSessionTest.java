package org.wing4j.litebatis.session.defaults;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Ignore;
import org.junit.Test;
import org.wing4j.litebatis.Configuration;
import org.wing4j.litebatis.configuration.DefaultConfiguration;
import org.wing4j.litebatis.executor.SimpleExecutor;
import org.wing4j.litebatis.mapping.*;
import org.wing4j.litebatis.scripting.RawSqlSource;
import org.wing4j.litebatis.session.SqlSession;
import org.wing4j.litebatis.transaction.TransactionIsolationLevel;
import org.wing4j.litebatis.transaction.jdbc.JdbcTransaction;
import org.wing4j.litebatis.type.JdbcType;
import org.wing4j.litebatis.type.StringTypeHandler;
import org.wing4j.litebatis.type.UnknownTypeHandler;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by wing4j on 2017/6/13.
 */
public class DefaultSqlSessionTest {

    @Test
    public void testInsert() throws Exception {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
        dataSource.setUrl("jdbc:log4jdbc:h2:mem:;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        Configuration configuration = new DefaultConfiguration();
        {
            DefaultMappedStatement ms = new DefaultMappedStatement();
            ms.setId("createTable");
            ms.setConfiguration(configuration);
            ms.setStatementType(StatementType.PREPARED);
            RawSqlSource sqlSource = new RawSqlSource(configuration, "create table a(serial_no varchar(10), PRIMARY KEY (serial_no))", String.class);
            ms.setSqlSource(sqlSource);
            configuration.addMappedStatement(ms);
        }
        {
            DefaultMappedStatement ms = new DefaultMappedStatement();
            ms.setId("insert");
            ms.setConfiguration(configuration);
            ms.setStatementType(StatementType.PREPARED);
            RawSqlSource sqlSource = new RawSqlSource(configuration, "insert into a(serial_no) values(?)", String.class);
            ms.setSqlSource(sqlSource);
            DefaultParameterMapping parameterMapping = new DefaultParameterMapping();
            parameterMapping.setConfiguration(configuration);
            parameterMapping.setJavaType(String.class);
            parameterMapping.setJdbcType(JdbcType.VARCHAR);
            parameterMapping.setProperty("serialNo");
            parameterMapping.setTypeHandler(new StringTypeHandler());
            ms.getParameterMap().getParameterMappings().add(parameterMapping);
            configuration.addMappedStatement(ms);
        }
        SqlSession session = new DefaultSqlSession(configuration, new SimpleExecutor(configuration, new JdbcTransaction(dataSource, TransactionIsolationLevel.READ_COMMITTED, true )));
        session.update("createTable");
        session.update("insert", new TestEntity("1111"));
        session.update("insert", new TestEntity("1111"));
    }

    @Test
    public void testSelect(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
        dataSource.setUrl("jdbc:log4jdbc:h2:mem:;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        Configuration configuration = new DefaultConfiguration();
        {
            DefaultMappedStatement ms = new DefaultMappedStatement();
            ms.setId("createTable");
            ms.setConfiguration(configuration);
            ms.setStatementType(StatementType.PREPARED);
            RawSqlSource sqlSource = new RawSqlSource(configuration, "create table a(serial_no varchar(10), PRIMARY KEY (serial_no))", String.class);
            ms.setSqlSource(sqlSource);
            configuration.addMappedStatement(ms);
        }
        {
            DefaultMappedStatement ms = new DefaultMappedStatement();
            ms.setId("insert");
            ms.setConfiguration(configuration);
            ms.setStatementType(StatementType.PREPARED);
            RawSqlSource sqlSource = new RawSqlSource(configuration, "insert into a(serial_no) values(?)", String.class);
            ms.setSqlSource(sqlSource);
            DefaultParameterMapping parameterMapping = new DefaultParameterMapping();
            parameterMapping.setConfiguration(configuration);
            parameterMapping.setJavaType(String.class);
            parameterMapping.setJdbcType(JdbcType.VARCHAR);
            parameterMapping.setProperty("serialNo");
            parameterMapping.setTypeHandler(new StringTypeHandler());
            ms.getParameterMap().getParameterMappings().add(parameterMapping);
            configuration.addMappedStatement(ms);
        }
        {
            DefaultMappedStatement ms = new DefaultMappedStatement();
            ms.setId("select");
            ms.setConfiguration(configuration);
            ms.setStatementType(StatementType.PREPARED);
            RawSqlSource sqlSource = new RawSqlSource(configuration, "select * from a where serial_no = ?", String.class);
            ms.setSqlSource(sqlSource);
            DefaultParameterMapping parameterMapping = new DefaultParameterMapping();
            parameterMapping.setConfiguration(configuration);
            parameterMapping.setJavaType(String.class);
            parameterMapping.setJdbcType(JdbcType.VARCHAR);
            parameterMapping.setProperty("serialNo");
            parameterMapping.setTypeHandler(new StringTypeHandler());
            ms.getParameterMap().getParameterMappings().add(parameterMapping);
            ResultMap resultMap = new DefaultResultMap();
            resultMap.setType(String.class);
            resultMap.getMappedColumns().add("serial_no");
            ResultMapping resultMapping = new DefaultResultMapping();
            resultMap.getResultMappings().add(resultMapping);
            ms.getResultMaps().add(resultMap);
            configuration.addMappedStatement(ms);
        }
        SqlSession session = new DefaultSqlSession(configuration, new SimpleExecutor(configuration, new JdbcTransaction(dataSource, TransactionIsolationLevel.READ_COMMITTED, true )));
        session.update("createTable");
        session.update("insert", new TestEntity("1111"));
        List list = session.selectList("select", new TestEntity("1111"));
        System.out.println(list);
    }
}