package org.wing4j.litebatis.scripting;

public interface SqlNode {
  boolean apply(DynamicContext context);
}