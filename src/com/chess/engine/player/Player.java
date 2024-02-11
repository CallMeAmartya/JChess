package com.chess.engine.player;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.exceptions.KingNotEstablishedException;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;
import java.util.Collection;

public abstract class Player {
  protected final Board board;

  protected final King playerKing;

  protected final Collection<Move> playerMoves;

  protected Player(
      final Board board, final Collection<Move> playerMoves, final Collection<Move> opponentMoves) {
    this.board = board;
    this.playerMoves = playerMoves;
    this.playerKing = establishKing();
  }

  private King establishKing() {
    for (final Piece piece : getActivePieces()) {
      if (piece.getPieceType().isKing()) {
        return (King) piece;
      }
    }
    throw new KingNotEstablishedException("A valid chess board can't be formed without it's King!");
  }

  protected abstract Collection<Piece> getActivePieces();
}
