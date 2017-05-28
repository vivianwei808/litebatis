package org.wing4j.litebatis.scripting;

public class ScriptingException extends RuntimeException {

  private static final long serialVersionUID = 7642570221267566591L;

  public ScriptingException() {
    super();
  }

  public ScriptingException(String message) {
    super(message);
  }

  public ScriptingException(String message, Throwable cause) {
    super(message, cause);
  }

  public ScriptingException(Throwable cause) {
    super(cause);
  }

}