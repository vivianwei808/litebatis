package org.wing4j.litebatis.reflection.property;

import lombok.*;

/**
 * Created by 面试1 on 2017/7/13.
 */
@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class DemoBean2 extends DemoBean{
    String target;
}
