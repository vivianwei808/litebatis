package org.wing4j.litebatis.session.defaults;

import org.junit.Test;
import org.wing4j.litebatis.Configuration;
import org.wing4j.litebatis.configuration.DefaultConfiguration;
import org.wing4j.litebatis.executor.SimpleExecutor;
import org.wing4j.litebatis.mapping.DefaultMappedStatement;
import org.wing4j.litebatis.session.SqlSession;
import org.wing4j.litebatis.transaction.jdbc.JdbcTransaction;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.Assert.*;

/**
 * Created by wing4j on 2017/6/13.
 */
public class DefaultSqlSessionTest {

    @Test
    public void testSelectList() throws Exception {
        Class.forName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
        Connection connection = DriverManager.getConnection("jdbc:log4jdbc:h2:mem:;DB_CLOSE_DELAY=-1", "sa", "");
        Configuration configuration = new DefaultConfiguration();
        configuration.addMappedStatement(new DefaultMappedStatement());
        SqlSession session = new DefaultSqlSession(configuration, new SimpleExecutor(configuration, new JdbcTransaction(connection)));
        session.insert("", "");
    }
}