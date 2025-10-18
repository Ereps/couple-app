package org.example.exception;

/**
 * Llm_Exception
 */
public final class LlmExceptions {

  private LlmExceptions() {
  };

  public static final class LlmException extends RuntimeException {
    public LlmException(String message) {
      super(message);
    }
  }

  public static final class InvalidPromtException extends RuntimeException {
    public InvalidPromtException(String message) {
      super(message);
    }
  }

}
