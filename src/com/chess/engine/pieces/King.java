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

public class King extends Piece {

  private static final int[][] CANDIDATE_MOVE_OFFSETS = {
    {1, 0}, {1, 1}, {1, -1}, {-1, 0}, {-1, 1}, {-1, -1}, {0, 1}, {0, -1}
  };

  public King(int pieceIndex, Alliance alliance) {
    super(PieceType.KING, pieceIndex, alliance);
  }

  @Override
  public Collection<Move> calculateLegalMoves(final Board board) {
    Set<Move> legalMoves = new HashSet<>();
    for (final int[] offset : CANDIDATE_MOVE_OFFSETS) {
      final TwoDimensionalCoordinate currentCoordinate =
          BoardUtils.get2DCoordinateFromPosition(this.pieceIndex);
      final TwoDimensionalCoordinate destinationCoordinate =
          currentCoordinate.offsetCoordinate(offset[0], offset[1]);
      if (!BoardUtils.isInBounds(destinationCoordinate)) {
        continue;
      }
      final int destinationIndex = BoardUtils.getPositionFrom2DCoordinate(destinationCoordinate);
      final Tile destinationTile = board.getTile(destinationIndex);
      if (!destinationTile.isTileOccupied()) {
        legalMoves.add(new MajorMove(this, destinationIndex, board));
      } else {
        final Piece pieceAtDestination = destinationTile.getPiece();
        if (this.alliance != pieceAtDestination.getAlliance()) {
          legalMoves.add(new AttackMove(this, destinationIndex, board, pieceAtDestination));
        }
      }
    }
    return ImmutableSet.copyOf(legalMoves);
  }

  @Override
  public String toString() {
    return PieceType.KING.toString();
  }
}
