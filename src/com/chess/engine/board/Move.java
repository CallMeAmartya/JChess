package com.chess.engine.board;

import com.chess.engine.exceptions.InstantiationNotAllowedException;
import com.chess.engine.exceptions.NullMoveExecutionException;
import com.chess.engine.pieces.Piece;

public abstract class Move {

  protected final Piece piece;
  private final int destinationIndex;
  protected final Board board;
  public static final Move NULL_MOVE = new NullMove();

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

  private int getCurrentIndex() {
    return this.piece.getPieceIndex();
  }

  public static final class MajorMove extends Move {
    public MajorMove(final Piece piece, final int destinationIndex, final Board board) {
      super(piece, destinationIndex, board);
    }
  }

  public static class AttackMove extends Move {
    private final Piece attackedPiece;

    public AttackMove(
        final Piece piece,
        final int destinationIndex,
        final Board board,
        final Piece attackedPiece) {
      super(piece, destinationIndex, board);
      this.attackedPiece = attackedPiece;
    }

    @Override
    public Board execute() {
      return null;
    }
  }

  public static final class PawnMove extends Move {
    public PawnMove(final Piece piece, final int destinationIndex, final Board board) {
      super(piece, destinationIndex, board);
    }
  }

  public static final class PawnJump extends Move {
    public PawnJump(final Piece piece, final int destinationIndex, final Board board) {
      super(piece, destinationIndex, board);
    }
  }

  public static class PawnAttackMove extends AttackMove {
    public PawnAttackMove(
        final Piece piece,
        final int destinationIndex,
        final Board board,
        final Piece attackedPiece) {
      super(piece, destinationIndex, board, attackedPiece);
    }
  }

  public static final class PawnEnPassantAttackMove extends PawnAttackMove {
    public PawnEnPassantAttackMove(
        final Piece piece,
        final int destinationIndex,
        final Board board,
        final Piece attackedPiece) {
      super(piece, destinationIndex, board, attackedPiece);
    }
  }

  abstract static class CastleMove extends Move {
    private CastleMove(final Piece piece, final int destinationIndex, final Board board) {
      super(piece, destinationIndex, board);
    }
  }

  public static final class KingSideCastleMove extends CastleMove {
    public KingSideCastleMove(final Piece piece, final int destinationIndex, final Board board) {
      super(piece, destinationIndex, board);
    }
  }

  public static final class QueenSideCastleMove extends CastleMove {
    public QueenSideCastleMove(final Piece piece, final int destinationIndex, final Board board) {
      super(piece, destinationIndex, board);
    }
  }

  public static final class NullMove extends Move {
    private NullMove() {
      super(null, -1, null);
    }

    @Override
    public Board execute() {
      throw new NullMoveExecutionException("Execution of null move is prohibited!");
    }
  }

  public static final class MoveFactory {

    private MoveFactory() {
      throw new InstantiationNotAllowedException("Instantiation of MoveFactory not allowed!");
    }

    public static Move createMove(
        final int currentIndex, final int destinationIndex, final Board board) {
      for (final Move move : board.getAllLegalMoves()) {
        if (move.getCurrentIndex() == currentIndex && move.getDestinationIndex() == destinationIndex) {
          return move;
        }
      }
      return NULL_MOVE;
    }
  }
}
