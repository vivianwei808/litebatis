package org.wing4j.orm.litebatis.api;

public interface ResultContext<T> {
    T getResultObject();
    int getResultCount();
    boolean isStopped();
    void stop();
}