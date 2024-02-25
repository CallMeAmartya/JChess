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

public class BlackPlayer extends Player {
  public BlackPlayer(
      final Board board, final Collection<Move> playerMoves, final Collection<Move> opponentMoves)
      throws KingNotEstablishedException {
    super(board, playerMoves, opponentMoves);
  }

  @Override
  public Collection<Piece> getActivePieces() {
    return this.board.getBlackActivePieces();
  }

  @Override
  public Alliance getAlliance() {
    return Alliance.BLACK;
  }

  @Override
  public Player getOpponent() {
    return this.board.getWhitePlayer();
  }

  @Override
  public Collection<Move> calculateKingCastles(
      final Collection<Move> playerMoves, final Collection<Move> opponentMoves) {
    final Set<Move> kingCastleMoves = new HashSet<>();
    if (this.playerKing.isFirstMove() && !this.isInCheck()) {
      // white king side castle
      if (!this.board.getTile(6).isTileOccupied() && !this.board.getTile(5).isTileOccupied()) {
        final Tile rookTile = this.board.getTile(7);
        if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
          if (Player.getAttacksOnTile(6, opponentMoves).isEmpty()
              && Player.getAttacksOnTile(5, opponentMoves).isEmpty()
              && rookTile.getPiece().getPieceType().isRook()) {
            kingCastleMoves.add(
                new KingSideCastleMove(
                    this.playerKing,
                    6,
                    this.board,
                    (Rook) rookTile.getPiece(),
                    rookTile.getTileCoordinate(),
                    5));
          }
        }
      }
      // queen side castle
      if (!this.board.getTile(1).isTileOccupied()
          && !this.board.getTile(2).isTileOccupied()
          && !this.board.getTile(3).isTileOccupied()) {
        final Tile rookTile = this.board.getTile(0);
        if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
          if (Player.getAttacksOnTile(1, opponentMoves).isEmpty()
              && Player.getAttacksOnTile(2, opponentMoves).isEmpty()
              && Player.getAttacksOnTile(3, opponentMoves).isEmpty()
              && rookTile.getPiece().getPieceType().isRook()) {
            kingCastleMoves.add(
                new QueenSideCastleMove(
                    this.playerKing,
                    2,
                    this.board,
                    (Rook) rookTile.getPiece(),
                    rookTile.getTileCoordinate(),
                    3));
          }
        }
      }
    }
    return ImmutableSet.copyOf(kingCastleMoves);
  }
}
