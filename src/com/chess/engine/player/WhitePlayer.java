package com.chess.engine.player;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.enums.Alliance;
import com.chess.engine.exceptions.KingNotEstablishedException;
import com.chess.engine.pieces.Piece;
import java.util.Collection;

public class WhitePlayer extends Player {
  public WhitePlayer(
      final Board board, final Collection<Move> playerMoves, final Collection<Move> opponentMoves)
      throws KingNotEstablishedException {
    super(board, playerMoves, opponentMoves);
  }

  @Override
  public Collection<Piece> getActivePieces() {
    return this.board.getWhiteActivePieces();
  }

  @Override
  public Alliance getAlliance() {
    return Alliance.WHITE;
  }

  @Override
  public Player getOpponent() {
    return this.board.getBlackPlayer();
  }
}
