package org.wing4j.litebatis.reflection.invoker;

import org.wing4j.litebatis.reflection.Invoker;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;


public class GetFieldInvoker implements Invoker {
  private Field field;

  public GetFieldInvoker(Field field) {
    this.field = field;
  }

  @Override
  public Object invoke(Object target, Object... args) throws IllegalAccessException, InvocationTargetException {
    return field.get(target);
  }

  @Override
  public Class<?> getType() {
    return field.getType();
  }
}
