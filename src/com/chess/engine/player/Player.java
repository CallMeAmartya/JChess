package com.chess.engine.player;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.enums.Alliance;
import com.chess.engine.exceptions.KingNotEstablishedException;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public abstract class Player {

  protected final Board board;
  protected final King playerKing;
  protected final Collection<Move> playerMoves;
  private final boolean inCheck;

  protected Player(
      final Board board, final Collection<Move> playerMoves, final Collection<Move> opponentMoves)
      throws KingNotEstablishedException {
    this.board = board;
    this.playerMoves = playerMoves;
    this.playerKing = establishKing();
    this.inCheck = !Player.getAttacksOnPiece(this.playerKing, opponentMoves).isEmpty();
  }

  private static Collection<Move> getAttacksOnPiece(Piece piece, Collection<Move> opponentMoves) {
    Set<Move> attackOnPieceMoves = new HashSet<>();
    for (Move opponentMove : opponentMoves) {
      if (opponentMove.getDestinationIndex() == piece.getPieceIndex()) {
        attackOnPieceMoves.add(opponentMove);
      }
    }
    return ImmutableSet.copyOf(attackOnPieceMoves);
  }

  private King establishKing() throws KingNotEstablishedException {
    for (final Piece piece : getActivePieces()) {
      if (piece.getPieceType().isKing()) {
        return (King) piece;
      }
    }
    throw new KingNotEstablishedException("A valid chess board can't be formed without it's King!");
  }

  public boolean isMoveLegal(Move move) {
    return this.playerMoves.contains(move);
  }

  public boolean isInCheck() {
    return this.inCheck;
  }

  public boolean isInCheckmate() {
    return isInCheck() && !hasEscapeMoves();
  }

  public boolean isInStalemate() {
    return !isInCheck() && !hasEscapeMoves();
  }

  // TODO: Implement these methods!!
  public boolean isCastled() {
    return false;
  }

  public MoveTransition makeMove(final Move move) {
    if (!isMoveLegal(move)) {
      return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
    }
    final Board transitionBoard = move.execute();
    final Collection<Move> kingAttacks =
        Player.getAttacksOnPiece(
            transitionBoard.getCurrentPlayer().getOpponent().playerKing,
            transitionBoard.getCurrentPlayer().playerMoves);
    if (!kingAttacks.isEmpty()) {
      return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
    }
    return new MoveTransition(this.board, move, MoveStatus.DONE);
  }

  private boolean hasEscapeMoves() {
    for (final Move move : this.playerMoves) {
      MoveTransition moveTransition = makeMove(move);
      if (moveTransition.getMoveStatus().isDone()) {
        return true;
      }
    }
    return false;
  }

  protected abstract Collection<Piece> getActivePieces();

  abstract Alliance getAlliance();

  abstract Player getOpponent();
}
