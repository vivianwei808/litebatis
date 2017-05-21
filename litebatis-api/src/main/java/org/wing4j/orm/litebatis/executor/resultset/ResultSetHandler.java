package org.wing4j.orm.litebatis.executor.resultset;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface ResultSetHandler {
  <E> List<E> handleResultSets(Statement stmt) throws SQLException;
  void handleOutputParameters(CallableStatement cs) throws SQLException;
}