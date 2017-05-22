package org.wing4j.litebatis.executor.statement;

import org.wing4j.litebatis.executor.parameter.ParameterHandler;
import org.wing4j.litebatis.mapping.BoundSql;
import org.wing4j.litebatis.session.ResultHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface StatementHandler {

  Statement prepare(Connection connection) throws SQLException;

  void parameterize(Statement statement) throws SQLException;

  void batch(Statement statement) throws SQLException;

  int update(Statement statement) throws SQLException;

  <E> List<E> query(Statement statement, ResultHandler resultHandler) throws SQLException;

  BoundSql getBoundSql();

  ParameterHandler getParameterHandler();

}
