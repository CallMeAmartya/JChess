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

public class Bishop extends Piece {

  private static final int[][] DIRECTION_VECTORS = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

  public Bishop(int pieceIndex, Alliance alliance) {
    super(PieceType.BISHOP, pieceIndex, alliance, true);
  }

  public Bishop(int pieceIndex, Alliance alliance, boolean firstMove) {
    super(PieceType.BISHOP, pieceIndex, alliance, firstMove);
  }

  @Override
  public Bishop movePiece(Move move) {
    return new Bishop(move.getDestinationIndex(), move.getPiece().getAlliance(), false);
  }

  @Override
  public Collection<Move> calculateLegalMoves(final Board board) {
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
    return PieceType.BISHOP.toString();
  }
}
