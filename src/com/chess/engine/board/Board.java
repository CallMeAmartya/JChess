package com.chess.engine.board;

import static com.chess.engine.utils.BoardUtils.NUM_TILES;

import com.chess.engine.pieces.Piece;

public class Board {

  private final Tile[] tilesOnBoard = new Tile[NUM_TILES];

  public Board() {
    for (int i = 0; i < NUM_TILES; i++) {
      this.tilesOnBoard[i] = Tile.createTile(i, null);
    }
  }

  public Tile getTile(int tileCoordinate) {
    return this.tilesOnBoard[tileCoordinate];
  }

  public void setTile(int tileCoordinate, Piece piece) {
    this.tilesOnBoard[tileCoordinate] = Tile.createTile(tileCoordinate, piece);
  }

  public Tile[] getBoard() {
    return this.tilesOnBoard;
  }
}
