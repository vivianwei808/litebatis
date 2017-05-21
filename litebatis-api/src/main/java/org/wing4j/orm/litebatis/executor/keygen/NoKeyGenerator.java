package org.wing4j.orm.litebatis.executor.keygen;

import org.wing4j.orm.litebatis.executor.Executor;
import org.wing4j.orm.litebatis.mapping.MappedStatement;

import java.sql.Statement;

public class NoKeyGenerator implements KeyGenerator {

    @Override
    public void processBefore(Executor executor, MappedStatement ms, Statement stmt, Object parameter) {
        // Do Nothing
    }

    @Override
    public void processAfter(Executor executor, MappedStatement ms, Statement stmt, Object parameter) {
        // Do Nothing
    }

}