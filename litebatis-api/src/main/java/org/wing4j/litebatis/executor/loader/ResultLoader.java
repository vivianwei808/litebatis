package org.wing4j.litebatis.executor.loader;

import org.wing4j.litebatis.Configuration;
import org.wing4j.litebatis.cache.CacheKey;
import org.wing4j.litebatis.exception.ExecutorException;
import org.wing4j.litebatis.executor.Executor;
import org.wing4j.litebatis.executor.ResultExtractor;
import org.wing4j.litebatis.mapping.BoundSql;
import org.wing4j.litebatis.mapping.Environment;
import org.wing4j.litebatis.mapping.MappedStatement;
import org.wing4j.litebatis.reflection.factory.ObjectFactory;
import org.wing4j.litebatis.session.ExecutorType;
import org.wing4j.litebatis.session.RowBounds;
import org.wing4j.litebatis.transaction.Transaction;
import org.wing4j.litebatis.transaction.TransactionFactory;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

public class ResultLoader {

  protected final Configuration configuration;
  protected final Executor executor;
  protected final MappedStatement mappedStatement;
  protected final Object parameterObject;
  protected final Class<?> targetType;
  protected final ObjectFactory objectFactory;
  protected final CacheKey cacheKey;
  protected final BoundSql boundSql;
  protected final ResultExtractor resultExtractor;
  protected final long creatorThreadId;
  
  protected boolean loaded;
  protected Object resultObject;
  
  public ResultLoader(Configuration config, Executor executor, MappedStatement mappedStatement, Object parameterObject, Class<?> targetType, CacheKey cacheKey, BoundSql boundSql) {
    this.configuration = config;
    this.executor = executor;
    this.mappedStatement = mappedStatement;
    this.parameterObject = parameterObject;
    this.targetType = targetType;
    this.objectFactory = configuration.getObjectFactory();
    this.cacheKey = cacheKey;
    this.boundSql = boundSql;
    this.resultExtractor = new ResultExtractor(configuration, objectFactory);
    this.creatorThreadId = Thread.currentThread().getId();
  }

  public Object loadResult() throws SQLException {
    List<Object> list = selectList();
    resultObject = resultExtractor.extractObjectFromList(list, targetType);
    return resultObject;
  }

  private <E> List<E> selectList() throws SQLException {
    Executor localExecutor = executor;
    if (Thread.currentThread().getId() != this.creatorThreadId || localExecutor.isClosed()) {
      localExecutor = newExecutor();
    }
    try {
      return localExecutor.<E> query(mappedStatement, parameterObject, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER, cacheKey, boundSql);
    } finally {
      if (localExecutor != executor) {
        localExecutor.close(false);
      }
    }
  }

  private Executor newExecutor() {
    final Environment environment = configuration.getEnvironment();
    if (environment == null) {
      throw new ExecutorException("ResultLoader could not load lazily.  Environment was not configured.");
    }
    final DataSource ds = environment.getDataSource();
    if (ds == null) {
      throw new ExecutorException("ResultLoader could not load lazily.  DataSource was not configured.");
    }
    final TransactionFactory transactionFactory = environment.getTransactionFactory();
    final Transaction tx = transactionFactory.newTransaction(ds, null, false);
    return configuration.newExecutor(tx, ExecutorType.SIMPLE);
  }

  public boolean wasNull() {
    return resultObject == null;
  }

}
