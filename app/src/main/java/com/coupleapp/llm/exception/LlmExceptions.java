package com.coupleapp.llm.exception;

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

  public static final class InvalidPromptException extends RuntimeException {
    public InvalidPromptException(String message) {
      super(message);
    }
  }

}
