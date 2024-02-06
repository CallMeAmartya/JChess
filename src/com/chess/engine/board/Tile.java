package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;

public abstract class Tile {

  private static final Map<Integer, EmptyTile> EMPTY_TILE_CACHE = createAllPossibleEmptyTiles();
  protected final int tileCoordinate;

  private Tile(int tileCoordinate) {
    this.tileCoordinate = tileCoordinate;
  }

  public static Tile createTile(int tileCoordinate, Piece pieceOnTile) {
    return pieceOnTile == null
        ? EMPTY_TILE_CACHE.get(tileCoordinate)
        : new OccupiedTile(tileCoordinate, pieceOnTile);
  }

  private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
    Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
    for (int i = 0; i < 64; i++) {
      emptyTileMap.put(i, new EmptyTile(i));
    }
    return ImmutableMap.copyOf(emptyTileMap);
  }

  public abstract boolean isTileOccupied();

  public abstract Piece getPiece();

  public static final class EmptyTile extends Tile {

    private EmptyTile(int tileCoordinate) {
      super(tileCoordinate);
    }

    @Override
    public boolean isTileOccupied() {
      return false;
    }

    @Override
    public Piece getPiece() {
      return null;
    }

    @Override
    public String toString() {
      return "*";
    }
  }

  public static final class OccupiedTile extends Tile {

    private final Piece pieceOnTile;

    private OccupiedTile(int tileCoordinate, Piece pieceOnTile) {
      super(tileCoordinate);
      this.pieceOnTile = pieceOnTile;
    }

    @Override
    public boolean isTileOccupied() {
      return true;
    }

    @Override
    public Piece getPiece() {
      return this.pieceOnTile;
    }

    @Override
    public String toString() {
      return this.pieceOnTile.toString();
    }
  }
}
