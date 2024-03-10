package com.chess.gui;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.utils.BoardUtils;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.swing.*;

import static com.chess.gui.Table.chessBoard;

public class BoardPanel extends JPanel {

  private final List<TilePanel> boardTiles;
  private static Tile sourceTile;
  private static Tile destinationTile;
  private static Piece selectedPiece;

  BoardPanel() {
    super(new GridLayout(8, 8));
    boardTiles = new ArrayList<>();
    for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
      final TilePanel tilePanel = new TilePanel(i);
      boardTiles.add(tilePanel);
      add(tilePanel);
    }
    setPreferredSize(Table.BOARD_PANEL_DIMENSION);
    validate();
  }

  public static Tile getSourceTile() {
    return sourceTile;
  }

  public static void setSourceTile(Tile sourceTile) {
    BoardPanel.sourceTile = sourceTile;
  }

  public static Tile getDestinationTile() {
    return destinationTile;
  }

  public static void setDestinationTile(Tile destinationTile) {
    BoardPanel.destinationTile = destinationTile;
  }

  public static Piece getSelectedPiece() {
    return selectedPiece;
  }

  public static void setSelectedPiece(Piece selectedPiece) {
    BoardPanel.selectedPiece = selectedPiece;
  }

  public static Collection<Move> movesForSelectedPiece() {
    if (selectedPiece != null && selectedPiece.getAlliance().equals(chessBoard.getCurrentPlayer().getAlliance())) {
      return selectedPiece.calculateLegalMoves(chessBoard);
    }
    return Collections.emptyList();
  }

  public void drawBoard(final Board board) {
    Table.chessBoard = board;
    removeAll();
    for (TilePanel tilePanel : Table.boardDirection.traverse(this.boardTiles)) {
      tilePanel.drawTile(board);
      add(tilePanel);
    }
    validate();
    repaint();
  }
}
