package com.chess.gui;

import static com.chess.gui.Table.boardPanel;
import static com.chess.gui.Table.chessBoard;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.board.TwoDimensionalCoordinate;
import com.chess.engine.exceptions.ImageNotFoundException;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.MoveTransition;
import com.chess.engine.utils.BoardUtils;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class TilePanel extends JPanel {
  private final int tileId;
  private Tile tile;
  private Piece piece;

  TilePanel(final int tileId) {
    super(new GridBagLayout());
    this.tileId = tileId;
    setPreferredSize(Table.TILE_PANEL_DIMENSION);
    this.tile = chessBoard.getTile(this.tileId);
    this.piece = this.tile.getPiece();
    assignTileColor();
    assignTileIcon();

    addMouseListener(
        new MouseListener() {
          @Override
          public void mouseClicked(final MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e)) {
              BoardPanel.setSourceTile(null);
              BoardPanel.setDestinationTile(null);
              BoardPanel.setSelectedPiece(null);
              System.out.println("cleared selection!\n");
            } else if (SwingUtilities.isLeftMouseButton(e)) {
              System.out.println("Left Mouse Click Registered\n");
              if (BoardPanel.getSourceTile() == null && piece != null) {
                // first click on a non-empty tile
                System.out.println("First click on non empty tile");
                BoardPanel.setSourceTile(tile);
                BoardPanel.setSelectedPiece(piece);
                System.out.println("selected piece=" + piece + "\n");
              } else if (BoardPanel.getSourceTile() != null) {
                // second click with a selected piece
                System.out.println(
                    "Trying to make a move from "
                        + BoardPanel.getSourceTile().getTileCoordinate()
                        + " with "
                        + BoardPanel.getSelectedPiece()
                        + " to "
                        + tile.getTileCoordinate());
                BoardPanel.setDestinationTile(tile);
                final Move move =
                    Move.MoveFactory.createMove(
                        BoardPanel.getSourceTile().getTileCoordinate(),
                        tile.getTileCoordinate(),
                        chessBoard);
                final MoveTransition transition = chessBoard.getCurrentPlayer().makeMove(move);
                if (transition.getMoveStatus().isDone()) {
                  chessBoard = transition.getTransitionBoard();
                  System.out.println("MOVED!");
                  System.out.println(chessBoard);
                  // TODO: add move to log
                }
                System.out.println(
                    "moved piece="
                        + BoardPanel.getSelectedPiece()
                        + " from "
                        + BoardPanel.getSourceTile().getTileCoordinate()
                        + " to "
                        + tile.getTileCoordinate()
                        + "\n");
                BoardPanel.setSourceTile(null);
                BoardPanel.setDestinationTile(null);
                BoardPanel.setSelectedPiece(null);
              }
              System.out.println("Refreshing board... \n");
            } else {
              System.out.println("Do nothing, invalid key press \n");
            }
            SwingUtilities.invokeLater(() -> boardPanel.drawBoard(chessBoard));
          }

          @Override
          public void mousePressed(final MouseEvent e) {}

          @Override
          public void mouseReleased(final MouseEvent e) {}

          @Override
          public void mouseEntered(final MouseEvent e) {}

          @Override
          public void mouseExited(final MouseEvent e) {}
        });
    validate();
  }

  private void assignTileColor() {
    final TwoDimensionalCoordinate coordinate = BoardUtils.get2DCoordinateFromPosition(this.tileId);
    if (coordinate.getX() % 2 == 0) {
      setBackground(coordinate.getY() % 2 == 0 ? Table.LIGHT_TILE_COLOR : Table.DARK_TILE_COLOR);
    } else {
      setBackground(coordinate.getY() % 2 == 0 ? Table.DARK_TILE_COLOR : Table.LIGHT_TILE_COLOR);
    }
  }

  private void assignTileIcon() {
    removeAll();
    if (this.tile.isTileOccupied()) {
      String iconName = piece.getAlliance().name().charAt(0) + "_" + piece.getPieceType().name();
      try {
        // TODO: figure out a way to avoid hardcoding image size and use tile dimension to scale
        // image
        final BufferedImage image =
            ImageIO.read(new File(Table.PIECE_ICON_FOLDER_PATH + iconName.toLowerCase() + ".png"));
        Image scaledImage = image.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        add(new JLabel(new ImageIcon(scaledImage)));
      } catch (IOException e) {
        throw new ImageNotFoundException("Could not find icon for " + iconName);
      }
    }
  }

  private void assignTileBorder() {
    setBorder(null);
    if (!Table.shouldHighlight) {
      return;
    }
    for (Move move : BoardPanel.movesForSelectedPiece()) {
      if (move.getDestinationIndex() == this.tileId) {
        if (this.tile.isTileOccupied()) {
          setBorder(BorderFactory.createLineBorder(Color.RED));
        } else {
          setBorder(BorderFactory.createLineBorder(Color.GREEN));
        }
      }
    }
  }

  public void drawTile(final Board board) {
    chessBoard = board;
    this.tile = chessBoard.getTile(this.tileId);
    this.piece = this.tile.getPiece();
//    assignTileColor();
    assignTileBorder();
    assignTileIcon();
    validate();
    repaint();
  }
}
