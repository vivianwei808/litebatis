package org.wing4j.litebatis.session;

/**
 * 配置和设定执行器
 * SIMPLE 执行器执行其它语句。
 * REUSE 执行器可能重复使用prepared statements 语句。
 * BATCH执行器可以重复执行语句和批量更新。
 *
 * @author Clinton Begin
 */
public enum ExecutorType {
    SIMPLE, REUSE, BATCH
}
