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

public class Knight extends Piece {

  private static final int[][] CANDIDATE_MOVE_OFFSET = {
    {1, 2}, {1, -2}, {-1, 2}, {-1, -2}, {2, 1}, {2, -1}, {-2, 1}, {-2, -1}
  };

  public Knight(int piecePosition, Alliance alliance) {
    super(piecePosition, alliance);
  }

  @Override
  public Collection<Move> calculateMoves(Board board) {
    final Set<Move> legalMoves = new HashSet<>();

    for (final int candidate : nextCoordinates(this.pieceIndex)) {
      final Tile candidateDestinationTile = board.getTile(candidate);
      if (!candidateDestinationTile.isTileOccupied()) {
        // todo -> move class needs to be implemented
        legalMoves.add(new MajorMove(this, candidate, board));
      } else {
        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
        final Alliance pieceAlliance = pieceAtDestination.getAlliance();
        if (this.alliance != pieceAlliance) {
          legalMoves.add(new AttackMove(this, candidate, board, pieceAtDestination));
        }
      }
    }

    return ImmutableSet.copyOf(legalMoves);
  }

  private Set<Integer> nextCoordinates(int position) {
    TwoDimensionalCoordinate twoDimensionalCoordinate =
        BoardUtils.get2DCoordinateFromPosition(position);
    final Set<Integer> nextCoordinates = new HashSet<>();
    for (int i = 0; i < CANDIDATE_MOVE_OFFSET.length; i++) {
      final int x = CANDIDATE_MOVE_OFFSET[i][0];
      final int y = CANDIDATE_MOVE_OFFSET[i][1];
      TwoDimensionalCoordinate next2DCoordinate = twoDimensionalCoordinate.offsetCoordinate(x, y);
      if (!BoardUtils.isInBounds(next2DCoordinate)) {
        continue;
      }
      nextCoordinates.add(BoardUtils.getPositionFrom2DCoordinate(next2DCoordinate));
    }
    return nextCoordinates;
  }
}
