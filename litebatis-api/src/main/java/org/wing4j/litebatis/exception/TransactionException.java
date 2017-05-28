package org.wing4j.litebatis.exception;

public class TransactionException extends RuntimeException {

  private static final long serialVersionUID = -433589569461084605L;

  public TransactionException() {
    super();
  }

  public TransactionException(String message) {
    super(message);
  }

  public TransactionException(String message, Throwable cause) {
    super(message, cause);
  }

  public TransactionException(Throwable cause) {
    super(cause);
  }

}
