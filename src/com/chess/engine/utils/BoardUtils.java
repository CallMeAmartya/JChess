package com.chess.engine.utils;

import com.chess.engine.board.TwoDimensionalCoordinate;
import com.chess.engine.exceptions.InstantiationNotAllowedException;
import com.chess.engine.exceptions.PositionOutOfBoundsException;

public final class BoardUtils {

  public static final int NUM_TILES = 64;

  public static final int NUM_TILES_PER_ROW = 8;

  private BoardUtils() {
    throw new InstantiationNotAllowedException("Instantiation of utility classes is prohibited!");
  }

  public static boolean isInBounds(int nextMove) {
    return nextMove >= 0 && nextMove < NUM_TILES;
  }

  public static boolean isInBounds(TwoDimensionalCoordinate coordinate) {
    return coordinate.getX() >= 0
        && coordinate.getX() < NUM_TILES_PER_ROW
        && coordinate.getY() >= 0
        && coordinate.getY() < NUM_TILES_PER_ROW;
  }

  public static TwoDimensionalCoordinate get2DCoordinateFromPosition(int position) {
    if (!isInBounds(position))
      throw new PositionOutOfBoundsException(
          "Position "
              + position
              + " is not a valid coordinate for a "
              + NUM_TILES_PER_ROW
              + "x"
              + NUM_TILES_PER_ROW
              + " chess board!");
    int x = position / NUM_TILES_PER_ROW;
    int y = position % NUM_TILES_PER_ROW;
    return new TwoDimensionalCoordinate(x, y);
  }

  public static int getPositionFrom2DCoordinate(TwoDimensionalCoordinate coordinate) {
    return (coordinate.getX() * NUM_TILES_PER_ROW) + coordinate.getY();
  }
}
