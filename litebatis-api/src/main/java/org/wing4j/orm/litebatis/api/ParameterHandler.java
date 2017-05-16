package org.wing4j.orm.litebatis.api;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ParameterHandler {
  Object getParameterObject();
  void setParameters(PreparedStatement ps)throws SQLException;
}