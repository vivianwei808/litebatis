package org.wing4j.orm.litebatis.executor;

public class ExecutorException extends RuntimeException {

  private static final long serialVersionUID = 4060977051977364820L;

  public ExecutorException() {
    super();
  }

  public ExecutorException(String message) {
    super(message);
  }

  public ExecutorException(String message, Throwable cause) {
    super(message, cause);
  }

  public ExecutorException(Throwable cause) {
    super(cause);
  }

}