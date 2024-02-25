package com.chess.gui;

import com.chess.engine.board.TwoDimensionalCoordinate;
import com.chess.engine.utils.BoardUtils;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Table {

  private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
  private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
  private static final Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);
  private static final Color LIGHT_TILE_COLOR = Color.decode("#e3c06f");
  private static final Color DARK_TILE_COLOR = Color.decode("#b88a4a");
  private final JFrame gameFrame;
  private final BoardPanel boardPanel;

  public Table() {
    this.gameFrame = new JFrame("JChess");
    this.gameFrame.setLayout(new BorderLayout());
    // Set inner details
    final JMenuBar menuBar = createMenuBar();
    this.gameFrame.setJMenuBar(menuBar);

    this.boardPanel = new BoardPanel();
    this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
    // Set frame properties
    this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
    this.gameFrame.setVisible(true);
  }

  private JMenuBar createMenuBar() {
    final JMenuBar menuBar = new JMenuBar();
    menuBar.add(createFileMenu());
    return menuBar;
  }

  private JMenu createFileMenu() {
    final JMenu fileMenu = new JMenu("File");
    // Load PGN option
    final JMenuItem openPGN = new JMenuItem("Load PGN File");
    openPGN.addActionListener(e -> System.out.println("open up that pgn file!"));
    fileMenu.add(openPGN);
    // Exit option
    final JMenuItem exit = new JMenuItem("Exit");
    exit.addActionListener(e -> System.exit(0));
    fileMenu.add(exit);
    return fileMenu;
  }

  private class BoardPanel extends JPanel {

    private final List<TilePanel> boardTiles;

    BoardPanel() {
      super(new GridLayout(8, 8));
      boardTiles = new ArrayList<>();
      for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
        final TilePanel tilePanel = new TilePanel(this, i);
        boardTiles.add(tilePanel);
        add(tilePanel);
      }
      setPreferredSize(BOARD_PANEL_DIMENSION);
      validate();
    }
  }

  private class TilePanel extends JPanel {
    private final int tileId;

    TilePanel(final BoardPanel boardPanel, final int tileId) {
      super(new GridBagLayout());
      this.tileId = tileId;
      setPreferredSize(TILE_PANEL_DIMENSION);
      assignTileColor();
      validate();
    }

    private void assignTileColor() {
      final TwoDimensionalCoordinate coordinate = BoardUtils.get2DCoordinateFromPosition(this.tileId);
      if (coordinate.getX() % 2 == 0) {
        setBackground(coordinate.getY() % 2 == 0 ? LIGHT_TILE_COLOR : DARK_TILE_COLOR);
      } else {
        setBackground(coordinate.getY() % 2 == 0 ? DARK_TILE_COLOR : LIGHT_TILE_COLOR);
      }
    }
  }
}
