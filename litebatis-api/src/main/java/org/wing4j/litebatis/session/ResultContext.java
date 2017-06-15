package org.wing4j.litebatis.session;

public interface ResultContext<T> {
    T getResultObject();

    int getResultCount();

    boolean isStopped();

    void stop();
}