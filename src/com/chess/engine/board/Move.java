package com.chess.engine.board;

import com.chess.engine.exceptions.InstantiationNotAllowedException;
import com.chess.engine.exceptions.NullMoveExecutionException;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;
import java.util.Objects;

public abstract class Move {

  public static final Move NULL_MOVE = new NullMove();
  protected final Piece piece;
  protected final Board board;
  private final int destinationIndex;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Move move)) return false;
    return getDestinationIndex() == move.getDestinationIndex()
        && Objects.equals(getPiece(), move.getPiece());
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int res = 1;
    res = prime * res + this.destinationIndex;
    res = prime * res + this.piece.hashCode();
    return res;
  }

  public boolean isAttackMove() {
    return false;
  }

  public boolean isCastlingMove() {
    return false;
  }

  public Piece getAttackedPiece() {
    return null;
  }

  public static final class MajorMove extends Move {
    public MajorMove(final Piece piece, final int destinationIndex, final Board board) {
      super(piece, destinationIndex, board);
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

    @Override
    public Board execute() {
      final Board.BoardBuilder boardBuilder = new Board.BoardBuilder();
      this.board.getCurrentPlayer().getActivePieces().stream()
          .filter(currentPlayerPiece -> !this.piece.equals(currentPlayerPiece))
          .forEach(boardBuilder::setPiece);
      this.board.getCurrentPlayer().getOpponent().getActivePieces().stream()
          .forEach(boardBuilder::setPiece);
      final Pawn movedPawn = (Pawn) this.piece;
      boardBuilder.setPiece(movedPawn.movePiece(this));
      boardBuilder.setEnPassantPawn(movedPawn);
      boardBuilder.setMoveMaker(this.board.getCurrentPlayer().getOpponent().getAlliance());
      return boardBuilder.build();
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
    protected final Rook castleRook;
    protected final int castleRookStartIndex;
    protected final int castleRookDestinationIndex;

    private CastleMove(
        final Piece piece,
        final int destinationIndex,
        final Board board,
        final Rook castleRook,
        final int castleRookStartIndex,
        final int castleRookDestinationIndex) {
      super(piece, destinationIndex, board);
      this.castleRook = castleRook;
      this.castleRookStartIndex = castleRookStartIndex;
      this.castleRookDestinationIndex = castleRookDestinationIndex;
    }

    public Rook getCastleRook() {
      return this.castleRook;
    }

    @Override
    public boolean isCastlingMove() {
      return true;
    }

    @Override
    public Board execute() {
      final Board.BoardBuilder boardBuilder = new Board.BoardBuilder();
      this.board.getCurrentPlayer().getActivePieces().stream()
          .filter(
              currentPlayerPiece ->
                  !this.piece.equals(currentPlayerPiece)
                      && !this.castleRook.equals(currentPlayerPiece))
          .forEach(boardBuilder::setPiece);
      this.board.getCurrentPlayer().getOpponent().getActivePieces().stream()
          .forEach(boardBuilder::setPiece);
      boardBuilder.setPiece(this.piece.movePiece(this));
      // TODO: this new rook should have first_move=false
      boardBuilder.setPiece(
          new Rook(this.castleRookDestinationIndex, this.castleRook.getAlliance()));
      boardBuilder.setMoveMaker(this.board.getCurrentPlayer().getOpponent().getAlliance());
      return boardBuilder.build();
    }
  }

  public static final class KingSideCastleMove extends CastleMove {
    public KingSideCastleMove(
        final Piece piece,
        final int destinationIndex,
        final Board board,
        final Rook castleRook,
        final int castleRookStartIndex,
        final int castleRookDestinationIndex) {
      super(
          piece,
          destinationIndex,
          board,
          castleRook,
          castleRookStartIndex,
          castleRookDestinationIndex);
    }

    @Override
    public String toString() {
      return "O-O";
    }
  }

  public static final class QueenSideCastleMove extends CastleMove {
    public QueenSideCastleMove(
        final Piece piece,
        final int destinationIndex,
        final Board board,
        final Rook castleRook,
        final int castleRookStartIndex,
        final int castleRookDestinationIndex) {
      super(
          piece,
          destinationIndex,
          board,
          castleRook,
          castleRookStartIndex,
          castleRookDestinationIndex);
    }

    @Override
    public String toString() {
      return "O-O-O";
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

    @Override
    public boolean isAttackMove() {
      return true;
    }

    @Override
    public Piece getAttackedPiece() {
      return this.attackedPiece;
    }

    @Override
    public int hashCode() {
      return super.hashCode() + this.attackedPiece.hashCode();
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof AttackMove move)) return false;
      return super.equals(move) && this.attackedPiece.equals(move.getAttackedPiece());
    }
  }

  public static final class MoveFactory {

    private MoveFactory() {
      throw new InstantiationNotAllowedException("Instantiation of MoveFactory not allowed!");
    }

    public static Move createMove(
        final int currentIndex, final int destinationIndex, final Board board) {
      for (final Move move : board.getAllLegalMoves()) {
        if (move.getCurrentIndex() == currentIndex
            && move.getDestinationIndex() == destinationIndex) {
          return move;
        }
      }
      return NULL_MOVE;
    }
  }
}
