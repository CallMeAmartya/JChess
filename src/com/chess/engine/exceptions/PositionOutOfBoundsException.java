package com.chess.engine.exceptions;

public class PositionOutOfBoundsException extends RuntimeException {
  public PositionOutOfBoundsException(String message) {
    super(message);
  }
}
