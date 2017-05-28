package org.wing4j.litebatis.scripting.xmltag;

import ognl.Ognl;
import ognl.OgnlException;
import org.wing4j.litebatis.builder.BuilderException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class OgnlCache {

  private static final Map<String, Object> expressionCache = new ConcurrentHashMap<String, Object>();

  private OgnlCache() {
    // Prevent Instantiation of Static Class
  }

  public static Object getValue(String expression, Object root) {
    try {
      Map<Object, OgnlClassResolver> context = Ognl.createDefaultContext(root, new OgnlClassResolver());
      return Ognl.getValue(parseExpression(expression), context, root);
    } catch (OgnlException e) {
      throw new BuilderException("Error evaluating expression '" + expression + "'. Cause: " + e, e);
    }
  }

  private static Object parseExpression(String expression) throws OgnlException {
    Object node = expressionCache.get(expression);
    if (node == null) {
      node = Ognl.parseExpression(expression);
      expressionCache.put(expression, node);
    }
    return node;
  }

}