package com.chess.engine.pieces;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.enums.Alliance;
import com.chess.engine.utils.BoardUtils;
import com.google.common.collect.ImmutableSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Bishop extends Piece {

  private static final int[][] DIRECTIONS = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

  public Bishop(int pieceIndex, Alliance alliance) {
    super(pieceIndex, alliance);
  }

  @Override
  public Collection<Move> calculateMoves(Board board) {
    Set<Move> legalMoves = new HashSet<>();
    for (int[] direction : DIRECTIONS) {
      int startingIndex = this.pieceIndex;
      while (!board
          .getTile(
              BoardUtils.getPositionFrom2DCoordinate(
                  BoardUtils.get2DCoordinateFromPosition(startingIndex)
                      .offsetCoordinate(direction[0], direction[1])))
          .isTileOccupied()) {
        int nextIndex =
            BoardUtils.getPositionFrom2DCoordinate(
                BoardUtils.get2DCoordinateFromPosition(startingIndex)
                    .offsetCoordinate(direction[0], direction[1]));
        legalMoves.add(new Move.MajorMove(this, nextIndex, board));
        startingIndex = nextIndex;
      }
      if (board.getTile(startingIndex).getPiece().alliance != this.alliance) {
        legalMoves.add(
            new Move.AttackMove(
                this, startingIndex, board, board.getTile(startingIndex).getPiece()));
      }
    }
    return ImmutableSet.copyOf(legalMoves);
  }
}
