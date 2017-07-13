package org.wing4j.litebatis.reflection.invoker;

import org.wing4j.litebatis.reflection.Invoker;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class SetFieldInvoker implements Invoker {
  private Field field;

  public SetFieldInvoker(Field field) {
    this.field = field;
  }

  @Override
  public Object invoke(Object target, Object... args) throws IllegalAccessException, InvocationTargetException {
    if(args.length != 1){
      throw new IllegalArgumentException("参数只能为1个值!");
    }
    field.set(target, args[0]);
    return null;
  }

  @Override
  public Class<?> getType() {
    return field.getType();
  }
}
