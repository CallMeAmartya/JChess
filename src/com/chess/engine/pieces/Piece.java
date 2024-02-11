package com.chess.engine.pieces;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.enums.Alliance;
import java.util.Collection;

public abstract class Piece {

  protected final int pieceIndex;
  protected final Alliance alliance;
  protected boolean firstMove;

  protected Piece(final int pieceIndex, final Alliance alliance) {
    this.pieceIndex = pieceIndex;
    this.alliance = alliance;
    // TODO: implementation pending here!
    this.firstMove = false;
  }

  public int getPieceIndex() {
    return pieceIndex;
  }

  public Alliance getAlliance() {
    return this.alliance;
  }

  public boolean isFirstMove() {
    return this.firstMove;
  }

  public abstract Collection<Move> calculateLegalMoves(Board board);

  public enum PieceType {
    BISHOP("B"),
    KING("K"),
    KNIGHT("N"),
    PAWN("P"),
    QUEEN("Q"),
    ROOK("R");

    private final String pieceName;

    PieceType(String pieceName) {
      this.pieceName = pieceName;
    }

    @Override
    public String toString() {
      return this.pieceName;
    }
  }
}
