package com.chess.engine.utils;

import com.chess.engine.board.TwoDimensionalCoordinate;
import com.chess.engine.exceptions.InstantiationNotAllowedException;
import com.chess.engine.exceptions.PositionOutOfBoundsException;

public final class BoardUtils {

  public static final int BOARD_SIZE = 64;

  public static final int BOARD_SIDE_SIZE = 8;

  private BoardUtils() {
    throw new InstantiationNotAllowedException("Instantiation of utility classes is prohibited!");
  }

  public static boolean isInBounds(int nextMove) {
    return nextMove >= 0 && nextMove < BOARD_SIZE;
  }

  public static boolean isInBounds(TwoDimensionalCoordinate coordinate) {
    return coordinate.getX() >= 0
        && coordinate.getX() < BOARD_SIDE_SIZE
        && coordinate.getY() >= 0
        && coordinate.getY() < BOARD_SIDE_SIZE;
  }

  public static TwoDimensionalCoordinate get2DCoordinateFromPosition(int position) {
    if (!isInBounds(position))
      throw new PositionOutOfBoundsException(
          "Position "
              + position
              + " is not a valid coordinate for a "
              + BOARD_SIDE_SIZE
              + "x"
              + BOARD_SIDE_SIZE
              + " chess board!");
    int x = position / BOARD_SIDE_SIZE;
    int y = position % BOARD_SIDE_SIZE;
    return new TwoDimensionalCoordinate(x, y);
  }

  public static int getPositionFrom2DCoordinate(TwoDimensionalCoordinate coordinate) {
    return (coordinate.getX() * BOARD_SIDE_SIZE) + coordinate.getY();
  }
}
