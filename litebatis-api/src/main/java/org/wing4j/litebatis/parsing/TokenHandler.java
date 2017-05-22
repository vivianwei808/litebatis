package org.wing4j.litebatis.parsing;

public interface TokenHandler {
  /**
   * 对字符串进行处理
   * @param content
   * @return
   */
  String handleToken(String content);
}
