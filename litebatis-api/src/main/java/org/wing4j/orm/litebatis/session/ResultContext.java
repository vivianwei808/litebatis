package org.wing4j.orm.litebatis.session;

public interface ResultContext<T> {
    T getResultObject();
    int getResultCount();
    boolean isStopped();
    void stop();
}