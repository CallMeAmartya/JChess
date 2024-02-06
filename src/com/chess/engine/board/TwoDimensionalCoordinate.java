package com.chess.engine.board;

public final class TwoDimensionalCoordinate {
  private final int x;

  private final int y;

  public TwoDimensionalCoordinate(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  /**
   * Returns a new {@code TwoDimensionalCoordinate} object representing the point obtained by adding
   * the specified x and y offsets to the current coordinates.
   *
   * @param x The x-coordinate offset to be added.
   * @param y The y-coordinate offset to be added.
   * @return A new {@code TwoDimensionalCoordinate} object with updated coordinates.
   * @throws ArithmeticException if the resulting coordinates exceed the maximum or minimum values
   *     representable by integers.
   * @apiNote The original object remains unchanged; the returned object represents the updated
   *     coordinates.
   */
  public TwoDimensionalCoordinate offsetCoordinate(int x, int y) {
    return new TwoDimensionalCoordinate(this.x + x, this.y + y);
  }
}
