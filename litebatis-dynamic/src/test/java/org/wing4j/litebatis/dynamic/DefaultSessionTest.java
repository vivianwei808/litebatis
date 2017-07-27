package org.wing4j.litebatis.dynamic;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;
import org.wing4j.litebatis.Configuration;
import org.wing4j.litebatis.configuration.DefaultConfiguration;
import org.wing4j.litebatis.executor.SimpleExecutor;
import org.wing4j.litebatis.mapping.DefaultMappedStatement;
import org.wing4j.litebatis.mapping.StatementType;
import org.wing4j.litebatis.scripting.RawSqlSource;
import org.wing4j.litebatis.scripting.SqlNode;
import org.wing4j.litebatis.dynamic.sqlnode.MixedSqlNode;
import org.wing4j.litebatis.dynamic.sqlnode.StaticTextSqlNode;
import org.wing4j.litebatis.session.SqlSession;
import org.wing4j.litebatis.session.defaults.DefaultSqlSession;
import org.wing4j.litebatis.transaction.TransactionIsolationLevel;
import org.wing4j.litebatis.transaction.jdbc.JdbcTransaction;

import java.util.Arrays;

/**
 * Created by wing4j on 2017/7/19.
 */
public class DefaultSessionTest {
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
            MixedSqlNode mixedSqlNode = new MixedSqlNode(Arrays.asList(new SqlNode[]{new StaticTextSqlNode("insert into a(serial_no) values(?)")}));
            DynamicSqlSource sqlSource = new DynamicSqlSource(configuration, mixedSqlNode);
            ms.setSqlSource(sqlSource);
            configuration.addMappedStatement(ms);
        }

        SqlSession session = new DefaultSqlSession(configuration, new SimpleExecutor(configuration, new JdbcTransaction(dataSource, TransactionIsolationLevel.READ_COMMITTED, true )));
        session.update("createTable");
        session.update("insert", new TestEntity("1111"));
        session.update("insert", new TestEntity("1111"));
    }
}
