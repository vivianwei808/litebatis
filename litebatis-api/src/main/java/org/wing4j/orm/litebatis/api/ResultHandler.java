package org.wing4j.orm.litebatis.api;

public interface ResultHandler<T> {
  void handleResult(ResultContext<? extends T> resultContext);
}
