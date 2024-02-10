package com.chess.engine.pieces;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.enums.Alliance;
import java.util.Collection;

public abstract class Piece {

  protected final int pieceIndex;
  protected final Alliance alliance;
  protected boolean firstMove;

  public Piece(int pieceIndex, Alliance alliance) {
    this.pieceIndex = pieceIndex;
    this.alliance = alliance;
    // TODO: implementation pending here!
    this.firstMove = false;
  }

  public Alliance getAlliance() {
    return this.alliance;
  }

  public boolean isFirstMove() {
    return this.firstMove;
  }

  public abstract Collection<Move> calculateMoves(Board board);
}
