package com.chess.engine.pieces;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.TwoDimensionalCoordinate;
import com.chess.engine.enums.Alliance;
import com.chess.engine.utils.BoardUtils;
import com.google.common.collect.ImmutableSet;
import java.util.*;

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
      while (true) {
        TwoDimensionalCoordinate next2DCoordinate =
            BoardUtils.get2DCoordinateFromPosition(startingIndex)
                .offsetCoordinate(direction[0], direction[1]);
        int nextIndex = BoardUtils.getPositionFrom2DCoordinate(next2DCoordinate);
        if (!BoardUtils.isInBounds(next2DCoordinate)) {
          break;
        }
        if (board.getTile(nextIndex).isTileOccupied()) {
          if (board.getTile(nextIndex).getPiece().getAlliance() != this.alliance) {
            legalMoves.add(
                new Move.AttackMove(this, nextIndex, board, board.getTile(nextIndex).getPiece()));
          }
          break;
        }
        legalMoves.add(new Move.MajorMove(this, nextIndex, board));
        startingIndex = nextIndex;
      }
    }
    return ImmutableSet.copyOf(legalMoves);
  }
}
