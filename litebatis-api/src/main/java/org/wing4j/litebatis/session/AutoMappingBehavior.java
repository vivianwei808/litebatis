package org.wing4j.litebatis.session;

/**
 * 指定MyBatis 是否并且如何来自动映射数据表字段与对象的属性。PARTIAL将只自动映射简单的，没有嵌套的结果。FULL 将自动映射所有复杂的结果。
 */
public enum AutoMappingBehavior {

    /**
     * Disables auto-mapping.
     */
    NONE,

    /**
     * Will only auto-map results with no nested result mappings defined inside.
     */
    PARTIAL,

    /**
     * Will auto-map result mappings of any complexity (containing nested or otherwise).
     */
    FULL
}