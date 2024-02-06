package com.chess.engine.pieces;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.enums.Alliance;
import java.util.Collection;

public abstract class Piece {

  protected final int piecePosition;
  protected final Alliance alliance;

  public Piece(int piecePosition, Alliance alliance) {
    this.piecePosition = piecePosition;
    this.alliance = alliance;
  }

  public Alliance getAlliance() {
    return alliance;
  }

  public abstract Collection<Move> calculateMoves(Board board);
}
