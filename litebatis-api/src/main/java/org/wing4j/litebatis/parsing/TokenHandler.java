package org.wing4j.litebatis.parsing;

/**
 * 符号处理器
 */
public interface TokenHandler {
    /**
     * 对字符串进行处理
     *
     * @param content 内容
     * @return 处理结果
     */
    String handleToken(String content);
}
