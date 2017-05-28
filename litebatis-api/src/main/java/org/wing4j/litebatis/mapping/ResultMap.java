package org.wing4j.litebatis.mapping;

import java.util.Set;

/**
 * Created by wing4j on 2017/5/21.
 */
public interface ResultMap {
    String getId();
    Set<String> getMappedColumns();
}
