package com.chess.engine.player;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.KingSideCastleMove;
import com.chess.engine.board.Move.QueenSideCastleMove;
import com.chess.engine.board.Tile;
import com.chess.engine.enums.Alliance;
import com.chess.engine.exceptions.KingNotEstablishedException;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;
import com.google.common.collect.ImmutableSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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

  @Override
  public Collection<Move> calculateKingCastles(
      final Collection<Move> playerMoves, final Collection<Move> opponentMoves) {
    final Set<Move> kingCasteMoves = new HashSet<>();
    if (this.playerKing.isFirstMove() && !this.isInCheck()) {
      // white king side castle
      if (!this.board.getTile(62).isTileOccupied() && !this.board.getTile(61).isTileOccupied()) {
        final Tile rookTile = this.board.getTile(63);
        if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
          if (Player.getAttacksOnTile(62, opponentMoves).isEmpty()
              && Player.getAttacksOnTile(61, opponentMoves).isEmpty()
              && rookTile.getPiece().getPieceType().isRook()) {
            kingCasteMoves.add(
                new KingSideCastleMove(
                    this.playerKing,
                    62,
                    this.board,
                    (Rook) rookTile.getPiece(),
                    rookTile.getTileCoordinate(),
                    61));
          }
        }
      }
      // queen side castle
      if (!this.board.getTile(59).isTileOccupied()
          && !this.board.getTile(58).isTileOccupied()
          && !this.board.getTile(57).isTileOccupied()) {
        final Tile rookTile = this.board.getTile(56);
        if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
          if (Player.getAttacksOnTile(59, opponentMoves).isEmpty()
              && Player.getAttacksOnTile(58, opponentMoves).isEmpty()
              && Player.getAttacksOnTile(57, opponentMoves).isEmpty()
              && rookTile.getPiece().getPieceType().isRook()) {
            kingCasteMoves.add(
                new QueenSideCastleMove(
                    this.playerKing,
                    58,
                    this.board,
                    (Rook) rookTile.getPiece(),
                    rookTile.getTileCoordinate(),
                    59));
          }
        }
      }
    }
    return ImmutableSet.copyOf(kingCasteMoves);
  }
}
