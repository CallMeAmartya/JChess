package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

public abstract class Move {
  private final Piece piece;

  private final int destinationIndex;

  private final Board board;

  private Move(Piece piece, int destinationIndex, Board board) {
    this.piece = piece;
    this.destinationIndex = destinationIndex;
    this.board = board;
  }

  public int getDestinationIndex() {
    return destinationIndex;
  }

  public abstract Board execute();

  public static final class MajorMove extends Move {
    public MajorMove(Piece piece, int destinationIndex, Board board) {
      super(piece, destinationIndex, board);
    }

    @Override
    public Board execute() {
      return null;
    }
  }

  public static final class AttackMove extends Move {
    private final Piece attackedPiece;

    public AttackMove(Piece piece, int destinationIndex, Board board, Piece attackedPiece) {
      super(piece, destinationIndex, board);
      this.attackedPiece = attackedPiece;
    }

    @Override
    public Board execute() {
      return null;
    }
  }
}
