package org.wing4j.litebatis.reflection.property;

import org.wing4j.litebatis.reflection.exception.ReflectionException;

import java.util.Locale;

/**
 *  这个类提供了几个用来判断属性特征和从方面名称中获取属性名称的函数，
 *  我们首先来看判断一个方法名称是否是操作的一个属性的方法，如注释中所
 *  讲的返回true并一定就是一个属性。
 */
public final class PropertyNamer {

  private PropertyNamer() {
    // Prevent Instantiation of Static Class
  }

  /**
   * 根据java常用语法规则将一个函数转化为属性，
   * 如果参数不符合java常用语法规则将会抛出ReflectionException
   * @param name
   * @return
   */
  public static String methodToProperty(String name) {
    if (name.startsWith("is")) {
      name = name.substring(2);
    } else if (name.startsWith("get") || name.startsWith("set")) {
      name = name.substring(3);
    } else {
      throw new ReflectionException("Error parsing property name '" + name + "'.  Didn't start with 'is', 'get' or 'set'.");
    }

//    if (name.length() == 1 || (name.length() > 1 && !Character.isUpperCase(name.charAt(1)))) {
//      name = name.substring(0, 1).toLowerCase(Locale.ENGLISH) + name.substring(1);
//    }
    name = firstLowerCase(name);
    return name;
  }

  static String firstLowerCase(String value){
    if(value == null || value.length() < 1){
      return value;
    }
    char[] chars = value.toCharArray();
    chars[0] = Character.toLowerCase(chars[0]);
    return new String(chars);
  }
  public static boolean isProperty(String name) {
    return name.startsWith("get") || name.startsWith("set") || name.startsWith("is");
  }

  public static boolean isGetter(String name) {
    return name.startsWith("get") || name.startsWith("is");
  }

  public static boolean isSetter(String name) {
    return name.startsWith("set");
  }

}
