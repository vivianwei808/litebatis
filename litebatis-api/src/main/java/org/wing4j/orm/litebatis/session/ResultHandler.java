package org.wing4j.orm.litebatis.session;

public interface ResultHandler<T> {
  void handleResult(ResultContext<? extends T> resultContext);
}
