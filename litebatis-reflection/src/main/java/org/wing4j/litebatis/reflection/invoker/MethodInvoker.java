package org.wing4j.litebatis.reflection.invoker;

import org.wing4j.litebatis.reflection.Invoker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodInvoker implements Invoker {

  private Class<?> type;
  private Method method;

  public MethodInvoker(Method method) {
    this.method = method;

    if (method.getParameterTypes().length == 1) {
      type = method.getParameterTypes()[0];
    } else {
      type = method.getReturnType();
    }
  }

  @Override
  public Object invoke(Object target, Object... args) throws IllegalAccessException, InvocationTargetException {
    return method.invoke(target, args);
  }

  @Override
  public Class<?> getType() {
    return type;
  }
}
