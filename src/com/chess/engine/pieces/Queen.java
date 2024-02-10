package com.chess.engine.pieces;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.board.Tile;
import com.chess.engine.board.TwoDimensionalCoordinate;
import com.chess.engine.enums.Alliance;
import com.chess.engine.utils.BoardUtils;
import com.google.common.collect.ImmutableSet;
import java.util.*;

public class Queen extends Piece {

  private static final int[][] DIRECTION_VECTORS = {
    {1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
  };

  public Queen(int pieceIndex, Alliance alliance) {
    super(pieceIndex, alliance);
  }

  @Override
  public Collection<Move> calculateMoves(final Board board) {
    final Set<Move> legalMoves = new HashSet<>();
    for (final int[] vector : DIRECTION_VECTORS) {
      List<TwoDimensionalCoordinate> movesForVector = getMovesForVector(vector);
      for (final TwoDimensionalCoordinate twoDimensionalCoordinate : movesForVector) {
        final int currentIndex = BoardUtils.getPositionFrom2DCoordinate(twoDimensionalCoordinate);
        final Tile tileForCurrentIndex = board.getTile(currentIndex);
        if (!tileForCurrentIndex.isTileOccupied()) {
          legalMoves.add(new MajorMove(this, currentIndex, board));
        } else {
          final Piece pieceAtCurrentIndex = tileForCurrentIndex.getPiece();
          if (pieceAtCurrentIndex.getAlliance() != this.alliance) {
            legalMoves.add(new AttackMove(this, currentIndex, board, pieceAtCurrentIndex));
          }
          break;
        }
      }
    }
    return ImmutableSet.copyOf(legalMoves);
  }

  private List<TwoDimensionalCoordinate> getMovesForVector(int[] vector) {
    final List<TwoDimensionalCoordinate> moves = new ArrayList<>();
    TwoDimensionalCoordinate next2DCoordinate =
        BoardUtils.get2DCoordinateFromPosition(this.pieceIndex)
            .offsetCoordinate(vector[0], vector[1]);
    while (BoardUtils.isInBounds(next2DCoordinate)) {
      moves.add(next2DCoordinate);
      next2DCoordinate = next2DCoordinate.offsetCoordinate(vector[0], vector[1]);
    }
    return moves;
  }

  @Override
  public String toString() {
    return "Q";
  }
}
