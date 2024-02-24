package com.chess.engine.exceptions;

public class NullMoveExecutionException extends RuntimeException {
  public NullMoveExecutionException(String message) {
    super(message);
  }
}
