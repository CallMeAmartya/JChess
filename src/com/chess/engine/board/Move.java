package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

public abstract class Move {

  protected final Piece piece;
  private final int destinationIndex;
  protected final Board board;

  private Move(final Piece piece, final int destinationIndex, final Board board) {
    this.piece = piece;
    this.destinationIndex = destinationIndex;
    this.board = board;
  }

  public int getDestinationIndex() {
    return destinationIndex;
  }

  public Piece getPiece() {
    return this.piece;
  }

  public abstract Board execute();

  public static final class MajorMove extends Move {
    public MajorMove(Piece piece, int destinationIndex, Board board) {
      super(piece, destinationIndex, board);
    }

    @Override
    public Board execute() {
      Board.BoardBuilder boardBuilder = new Board.BoardBuilder();
      // create a copy of the board with all pieces at exactly the same position as before
      // only the moved piece is moved
      // TODO: hashcode and equals for Piece
      this.board.getCurrentPlayer().getActivePieces().stream()
          .filter(currentPlayerPiece -> !this.piece.equals(currentPlayerPiece))
          .forEach(boardBuilder::setPiece);
      this.board.getCurrentPlayer().getOpponent().getActivePieces().stream()
              .forEach(boardBuilder::setPiece);
      // set the moved piece in its new position
      boardBuilder.setPiece(this.piece.movePiece(this));
      // set the next move maker
      boardBuilder.setMoveMaker(this.board.getCurrentPlayer().getOpponent().getAlliance());
      return boardBuilder.build();
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
