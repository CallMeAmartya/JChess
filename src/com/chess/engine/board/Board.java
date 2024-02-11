package com.chess.engine.board;

import static com.chess.engine.utils.BoardUtils.NUM_TILES;
import static com.chess.engine.utils.BoardUtils.NUM_TILES_PER_ROW;

import com.chess.engine.enums.Alliance;
import com.chess.engine.pieces.*;
import com.google.common.collect.ImmutableList;
import java.util.*;

public class Board {

  private final List<Tile> chessBoard;
  private final Collection<Piece> whiteActivePieces;
  private final Collection<Piece> blackActivePieces;

  private Board(BoardBuilder boardBuilder) {
    this.chessBoard = createChessBoard(boardBuilder);
    this.whiteActivePieces = calculateActivePieces(this.chessBoard, Alliance.WHITE);
    this.blackActivePieces = calculateActivePieces(this.chessBoard, Alliance.BLACK);
    // calculate legal moves for both sided as soon as board is created
    Collection<Move> whiteLegalMoves = calculateLegalMoves(this.whiteActivePieces);
    Collection<Move> blackLegalMoves = calculateLegalMoves(this.blackActivePieces);
  }

  private static Collection<Piece> calculateActivePieces(
      final List<Tile> chessBoard, final Alliance alliance) {
    List<Piece> activePieces = new ArrayList<>();
    for (final Tile tile : chessBoard) {
      if (tile.isTileOccupied() && tile.getPiece().getAlliance().equals(alliance)) {
        activePieces.add(tile.getPiece());
      }
    }
    return ImmutableList.copyOf(activePieces);
  }

  private static List<Tile> createChessBoard(final BoardBuilder boardBuilder) {
    Tile[] tiles = new Tile[NUM_TILES];
    for (int i = 0; i < NUM_TILES; i++) {
      tiles[i] = Tile.createTile(i, boardBuilder.boardConfig.get(i));
    }
    return ImmutableList.copyOf(tiles);
  }

  public static Board createStandardChessboard() {
    return new BoardBuilder()
        .setPiece(new Rook(0, Alliance.BLACK))
        .setPiece(new Knight(1, Alliance.BLACK))
        .setPiece(new Bishop(2, Alliance.BLACK))
        .setPiece(new Queen(3, Alliance.BLACK))
        .setPiece(new King(4, Alliance.BLACK))
        .setPiece(new Bishop(5, Alliance.BLACK))
        .setPiece(new Knight(6, Alliance.BLACK))
        .setPiece(new Rook(7, Alliance.BLACK))
        .setPiece(new Pawn(8, Alliance.BLACK))
        .setPiece(new Pawn(9, Alliance.BLACK))
        .setPiece(new Pawn(10, Alliance.BLACK))
        .setPiece(new Pawn(11, Alliance.BLACK))
        .setPiece(new Pawn(12, Alliance.BLACK))
        .setPiece(new Pawn(13, Alliance.BLACK))
        .setPiece(new Pawn(14, Alliance.BLACK))
        .setPiece(new Pawn(15, Alliance.BLACK))
        .setPiece(new Pawn(48, Alliance.WHITE))
        .setPiece(new Pawn(49, Alliance.WHITE))
        .setPiece(new Pawn(50, Alliance.WHITE))
        .setPiece(new Pawn(51, Alliance.WHITE))
        .setPiece(new Pawn(52, Alliance.WHITE))
        .setPiece(new Pawn(53, Alliance.WHITE))
        .setPiece(new Pawn(54, Alliance.WHITE))
        .setPiece(new Pawn(55, Alliance.WHITE))
        .setPiece(new Rook(56, Alliance.WHITE))
        .setPiece(new Knight(57, Alliance.WHITE))
        .setPiece(new Bishop(58, Alliance.WHITE))
        .setPiece(new Queen(59, Alliance.WHITE))
        .setPiece(new King(60, Alliance.WHITE))
        .setPiece(new Bishop(61, Alliance.WHITE))
        .setPiece(new Knight(62, Alliance.WHITE))
        .setPiece(new Rook(63, Alliance.WHITE))
        .build();
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < NUM_TILES; i++) {
      final String tileText = this.chessBoard.get(i).toString();
      stringBuilder.append(String.format("%3s", tileText));
      if ((i + 1) % NUM_TILES_PER_ROW == 0) {
        stringBuilder.append("\n");
      }
    }
    return stringBuilder.toString();
  }

  private Collection<Move> calculateLegalMoves(final Collection<Piece> pieceCollection) {
    List<Move> legalMoves = new ArrayList<>();
    for (final Piece piece : pieceCollection) {
      legalMoves.addAll(piece.calculateLegalMoves(this));
    }
    return ImmutableList.copyOf(legalMoves);
  }

  public Tile getTile(int tileCoordinate) {
    return this.chessBoard.get(tileCoordinate);
  }

  public static final class BoardBuilder {

    Map<Integer, Piece> boardConfig;

    Alliance nextMoveMaker;

    public BoardBuilder() {
      this.boardConfig = new HashMap<>();
    }

    public BoardBuilder setPiece(final Piece piece) {
      this.boardConfig.put(piece.getPieceIndex(), piece);
      return this;
    }

    public BoardBuilder setMoveMaker(final Alliance nextMoveMaker) {
      this.nextMoveMaker = nextMoveMaker;
      return this;
    }

    public Board build() {
      return new Board(this);
    }
  }
}
