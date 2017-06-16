package org.wing4j.litebatis.scripting.xmltag;

import org.wing4j.litebatis.Configuration;
import org.wing4j.litebatis.scripting.SqlNode;

import java.util.Arrays;
import java.util.List;

public class SetSqlNode extends TrimSqlNode {

  private static List<String> suffixList = Arrays.asList(",");

  public SetSqlNode(Configuration configuration,SqlNode contents) {
    super(configuration, contents, "SET", null, null, suffixList);
  }

}