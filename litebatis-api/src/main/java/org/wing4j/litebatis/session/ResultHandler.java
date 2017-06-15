package org.wing4j.litebatis.session;

public interface ResultHandler<T> {
    void handleResult(ResultContext<? extends T> resultContext);
}
