package org.wing4j.litebatis.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RowBounds {
  public static final int NO_ROW_OFFSET = 0;
  public static final int NO_ROW_LIMIT = Integer.MAX_VALUE;
  public static final RowBounds DEFAULT = new RowBounds();
   int offset = NO_ROW_OFFSET;
   int limit = NO_ROW_LIMIT;
}