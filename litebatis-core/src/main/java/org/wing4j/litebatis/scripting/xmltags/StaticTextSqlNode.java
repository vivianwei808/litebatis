package org.wing4j.litebatis.scripting.xmltags;

public class StaticTextSqlNode implements SqlNode {
  private String text;

  public StaticTextSqlNode(String text) {
    this.text = text;
  }

  @Override
  public boolean apply(DynamicContext context) {
    context.appendSql(text);
    return true;
  }

}