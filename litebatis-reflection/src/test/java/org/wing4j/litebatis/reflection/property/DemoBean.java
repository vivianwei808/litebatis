package org.wing4j.litebatis.reflection.property;

import lombok.*;

import java.math.BigDecimal;

/**
 * Created by 面试1 on 2017/7/13.
 */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DemoBean {
    protected String name;
    protected int age;
    protected BigDecimal amt;
}
