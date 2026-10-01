package com.eazybytes.springai.exception;

import java.io.Serial;

public class InvalidAnswerException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -1555535747011162220L;

  public InvalidAnswerException(String question, String answer) {
    super("Answer check failed: The answer \"" + answer + "\" " +
        "is not correct for the question \"" + question + "\".");
  }
}
