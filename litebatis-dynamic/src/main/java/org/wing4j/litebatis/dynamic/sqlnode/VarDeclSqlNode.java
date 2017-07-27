package org.wing4j.litebatis.dynamic.sqlnode;

import org.wing4j.litebatis.scripting.DynamicContext;
import org.wing4j.litebatis.scripting.SqlNode;
import org.wing4j.litebatis.dynamic.OgnlCache;

public class VarDeclSqlNode implements SqlNode {

  private final String name;
  private final String expression;

  public VarDeclSqlNode(String var, String exp) {
    name = var;
    expression = exp;
  }

  @Override
  public boolean apply(DynamicContext context) {
    final Object value = OgnlCache.getValue(expression, context.getBindings());
    context.bind(name, value);
    return true;
  }

}