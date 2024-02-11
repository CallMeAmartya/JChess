package com.chess.engine.player;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.enums.Alliance;
import com.chess.engine.exceptions.KingNotEstablishedException;
import com.chess.engine.pieces.Piece;
import java.util.Collection;

public class BlackPlayer extends Player {
  public BlackPlayer(Board board, Collection<Move> playerMoves, Collection<Move> opponentMoves)
      throws KingNotEstablishedException {
    super(board, playerMoves, opponentMoves);
  }

  @Override
  protected Collection<Piece> getActivePieces() {
    return this.board.getBlackActivePieces();
  }

  @Override
  Alliance getAlliance() {
    return Alliance.BLACK;
  }

  @Override
  Player getOpponent() {
    return this.board.getWhitePlayer();
  }
}
