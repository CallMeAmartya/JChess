package com.chess.engine.pieces;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.*;
import com.chess.engine.board.TwoDimensionalCoordinate;
import com.chess.engine.enums.Alliance;
import com.chess.engine.utils.BoardUtils;
import com.google.common.collect.ImmutableSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Pawn extends Piece {

  private static final int[][] CANDIDATE_MOVE_OFFSET = {{1, 0}, {1, 1}, {1, -1}, {2, 0}};

  public Pawn(int pieceIndex, Alliance alliance) {
    super(PieceType.PAWN, pieceIndex, alliance, true);
  }

  public Pawn(int pieceIndex, Alliance alliance, boolean firstMove) {
    super(PieceType.PAWN, pieceIndex, alliance, firstMove);
  }

  @Override
  public Pawn movePiece(Move move) {
    return new Pawn(move.getDestinationIndex(), move.getPiece().getAlliance(), false);
  }

  @Override
  public Collection<Move> calculateLegalMoves(final Board board) {
    Set<Move> legalMoves = new HashSet<>();
    for (final int[] offset : CANDIDATE_MOVE_OFFSET) {
      final TwoDimensionalCoordinate pieceCoordinate =
          BoardUtils.get2DCoordinateFromPosition(this.pieceIndex);
      final TwoDimensionalCoordinate destinationCoordinate =
          pieceCoordinate.offsetCoordinate(
              offset[0] * this.alliance.getDirection(), offset[1] * this.alliance.getDirection());
      if (!BoardUtils.isInBounds(destinationCoordinate)) {
        continue;
      }
      final int destinationIndex = BoardUtils.getPositionFrom2DCoordinate(destinationCoordinate);
      // TODO: deal with pawn promotion and EnPassant!
      if (offset[1] == 0) {
        // when pawn is doing a non-attacking move
        if (offset[0] == 1 && !board.getTile(destinationIndex).isTileOccupied()) {
          // move pawn one tile forward
          legalMoves.add(new PawnMove(this, destinationIndex, board));
        }
        if (offset[0] == 2 && this.firstMove && !board.getTile(destinationIndex).isTileOccupied()) {
          final int behindDestinationIndex =
              BoardUtils.getPositionFrom2DCoordinate(
                  destinationCoordinate.offsetCoordinate(-1 * this.alliance.getDirection(), 0));
          if (board.getTile(behindDestinationIndex).isTileOccupied()) {
            // pawn cannot jump over another piece!
            continue;
          }
          // move pawn two tiles forward if first move for pawn and no piece in front of pawn
          legalMoves.add(new PawnJump(this, destinationIndex, board));
        }
      } else {
        // attacking move for a pawn
        if (board.getTile(destinationIndex).isTileOccupied()) {
          final Piece pieceAtDestinationIndex = board.getTile(destinationIndex).getPiece();
          if (this.alliance != pieceAtDestinationIndex.getAlliance()) {
            legalMoves.add(
                new PawnAttackMove(this, destinationIndex, board, pieceAtDestinationIndex));
          }
        }
      }
    }
    return ImmutableSet.copyOf(legalMoves);
  }

  @Override
  public String toString() {
    return PieceType.PAWN.toString();
  }
}
