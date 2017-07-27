package org.wing4j.litebatis.dynamic.sqlnode;

import org.wing4j.litebatis.scripting.DynamicContext;
import org.wing4j.litebatis.scripting.SqlNode;

import java.util.List;

/**
 * Created by wing4j on 2017/5/21.
 * 将多个节点添加到一个块
 */
public class MixedSqlNode implements SqlNode {
  private List<SqlNode> contents;

  public MixedSqlNode(List<SqlNode> contents) {
    this.contents = contents;
  }

  @Override
  public boolean apply(DynamicContext context) {
    for (SqlNode sqlNode : contents) {
      sqlNode.apply(context);
    }
    return true;
  }
}
