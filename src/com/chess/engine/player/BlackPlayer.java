package com.chess.engine.player;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;
import java.util.Collection;

public class BlackPlayer extends Player {
  public BlackPlayer(Board board, Collection<Move> playerMoves, Collection<Move> opponentMoves) {
    super(board, playerMoves, opponentMoves);
  }

  @Override
  protected Collection<Piece> getActivePieces() {
    return this.board.getBlackActivePieces();
  }
}
