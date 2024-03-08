package com.chess.gui;

import com.chess.engine.board.Board;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.utils.BoardUtils;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class BoardPanel extends JPanel {

  private final List<TilePanel> boardTiles;
  private Board chessBoard;
  private Tile sourceTile;
  private Tile destinationTile;
  private Piece selectedPiece;

  BoardPanel(Board chessBoard) {
    super(new GridLayout(8, 8));
    this.chessBoard = chessBoard;
    boardTiles = new ArrayList<>();
    for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
      final TilePanel tilePanel = new TilePanel(this, i);
      boardTiles.add(tilePanel);
      add(tilePanel);
    }
    setPreferredSize(Table.BOARD_PANEL_DIMENSION);
    validate();
  }

  public Tile getSourceTile() {
    return sourceTile;
  }

  public void setSourceTile(Tile sourceTile) {
    this.sourceTile = sourceTile;
  }

  public Tile getDestinationTile() {
    return destinationTile;
  }

  public void setDestinationTile(Tile destinationTile) {
    this.destinationTile = destinationTile;
  }

  public Piece getSelectedPiece() {
    return selectedPiece;
  }

  public void setSelectedPiece(Piece selectedPiece) {
    this.selectedPiece = selectedPiece;
  }

  public Board getChessBoard() {
    return chessBoard;
  }

  public void drawBoard(final Board board) {
    this.chessBoard = board;
    removeAll();
    for (TilePanel tilePanel : this.boardTiles) {
      tilePanel.drawTile(board);
      add(tilePanel);
    }
    validate();
    repaint();
  }
}
