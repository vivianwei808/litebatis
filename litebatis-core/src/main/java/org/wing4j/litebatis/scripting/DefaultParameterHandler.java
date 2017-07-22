package org.wing4j.litebatis.scripting;

import org.wing4j.litebatis.executor.ErrorContext;
import org.wing4j.litebatis.Configuration;
import org.wing4j.litebatis.executor.parameter.ParameterHandler;
import org.wing4j.litebatis.mapping.BoundSql;
import org.wing4j.litebatis.mapping.MappedStatement;
import org.wing4j.litebatis.mapping.ParameterMapping;
import org.wing4j.litebatis.mapping.ParameterMode;
import org.wing4j.litebatis.reflection.MetaObject;
import org.wing4j.litebatis.reflection.factory.MetaObjectFactory;
import org.wing4j.litebatis.type.JdbcType;
import org.wing4j.litebatis.reflection.exception.TypeException;
import org.wing4j.litebatis.type.TypeHandler;
import org.wing4j.litebatis.type.TypeHandlerRegistry;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DefaultParameterHandler implements ParameterHandler {

  private final TypeHandlerRegistry typeHandlerRegistry;

  private final MappedStatement mappedStatement;
  private final Object parameterObject;
  private BoundSql boundSql;
  private Configuration configuration;

  public DefaultParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql) {
    this.mappedStatement = mappedStatement;
    this.configuration = mappedStatement.getConfiguration();
    this.typeHandlerRegistry = mappedStatement.getConfiguration().getTypeHandlerRegistry();
    this.parameterObject = parameterObject;
    this.boundSql = boundSql;
  }

  @Override
  public Object getParameterObject() {
    return parameterObject;
  }

  /**
   * 对某一个Statement进行设置参数 
   */
  @Override
  public void setParameters(PreparedStatement ps) {
    ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
    List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
    if (parameterMappings != null) {
      for (int i = 0; i < parameterMappings.size(); i++) {
        ParameterMapping parameterMapping = parameterMappings.get(i);
        if (parameterMapping.getMode() != ParameterMode.OUT) {
          Object value = null;
          String propertyName = parameterMapping.getProperty();
          if (boundSql.hasParameter(propertyName)) { // issue #448 ask first for additional params
            value = boundSql.getParameter(propertyName);
          } else if (parameterObject == null) {
            value = null;
          } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
            value = parameterObject;
          } else {
            MetaObject metaObject = MetaObjectFactory.forObject(parameterObject);
            value = metaObject.getValue(propertyName);
          }
          //每一个Mapping都有一个TypeHandler，根据TypeHandler来对preparedStatement进行设置参数
          TypeHandler typeHandler = parameterMapping.getTypeHandler();
          JdbcType jdbcType = parameterMapping.getJdbcType();
          if (value == null && jdbcType == null) {
//            jdbcType = configuration.getJdbcTypeForNull();
          }
          try {
            //设置参数
            typeHandler.setParameter(ps, i + 1, value, jdbcType);
          } catch (TypeException e) {
            throw new TypeException("Could not set parameters for mapping: " + parameterMapping + ". Cause: " + e, e);
          } catch (SQLException e) {
            throw new TypeException("Could not set parameters for mapping: " + parameterMapping + ". Cause: " + e, e);
          }
        }
      }
    }
  }

}