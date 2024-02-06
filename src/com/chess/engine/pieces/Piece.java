package com.chess.engine.pieces;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.enums.Alliance;
import java.util.Collection;

public abstract class Piece {

  protected final int pieceIndex;
  protected final Alliance alliance;

  public Piece(int pieceIndex, Alliance alliance) {
    this.pieceIndex = pieceIndex;
    this.alliance = alliance;
  }

  public Alliance getAlliance() {
    return alliance;
  }

  public abstract Collection<Move> calculateMoves(Board board);
}
