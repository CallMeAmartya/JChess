package com.chess.engine.pieces;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.enums.Alliance;
import java.util.Collection;

public abstract class Piece {

  protected final PieceType pieceType;
  protected final int pieceIndex;
  protected final Alliance alliance;
  protected boolean firstMove;
  private final int cachedHashCode;

  protected Piece(final PieceType pieceType, final int pieceIndex, final Alliance alliance) {
      this.pieceType = pieceType;
      this.pieceIndex = pieceIndex;
    this.alliance = alliance;
    // TODO: implementation pending here!
    this.firstMove = false;
    this.cachedHashCode = calculateHashCode();
  }

  private int calculateHashCode() {
    int result = this.pieceType.hashCode();
    result = 31 * result + this.pieceIndex;
    result = 31 * result + this.alliance.hashCode();
    result = 31 * result + (this.firstMove ? 1 : 0);
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Piece piece)) return false;
    return this.pieceIndex == piece.getPieceIndex()
        && this.firstMove == piece.isFirstMove()
        && this.pieceType == piece.getPieceType()
        && this.alliance == piece.getAlliance();
  }

  @Override
  public int hashCode() {
    return this.cachedHashCode;
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

  public abstract Piece movePiece(Move move);

  public abstract Collection<Move> calculateLegalMoves(Board board);

  public PieceType getPieceType() {
    return this.pieceType;
  }

  public enum PieceType {
    BISHOP("B") {
      @Override
      public boolean isKing() {
        return false;
      }
    },
    KING("K") {
      @Override
      public boolean isKing() {
        return true;
      }
    },
    KNIGHT("N") {
      @Override
      public boolean isKing() {
        return false;
      }
    },
    PAWN("P") {
      @Override
      public boolean isKing() {
        return false;
      }
    },
    QUEEN("Q") {
      @Override
      public boolean isKing() {
        return false;
      }
    },
    ROOK("R") {
      @Override
      public boolean isKing() {
        return false;
      }
    };

    private final String pieceName;

    PieceType(String pieceName) {
      this.pieceName = pieceName;
    }

    @Override
    public String toString() {
      return this.pieceName;
    }

    public abstract boolean isKing();
  }
}
