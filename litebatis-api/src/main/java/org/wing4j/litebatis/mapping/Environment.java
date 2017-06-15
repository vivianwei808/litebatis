package org.wing4j.litebatis.mapping;

import org.wing4j.litebatis.transaction.TransactionFactory;

import javax.sql.DataSource;

/**
 * Created by wing4j on 2017/5/28.
 */
public interface Environment {
    String getId();

    TransactionFactory getTransactionFactory();

    DataSource getDataSource();
}
