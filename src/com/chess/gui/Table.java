package com.chess.gui;

import com.chess.engine.board.Board;
import java.awt.*;
import javax.swing.*;

public class Table {

  public static final String PIECE_ICON_FOLDER_PATH = "art/pieces/plain_no_shadow/";
  public static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
  public static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
  public static final Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);
  public static final Color LIGHT_TILE_COLOR = Color.decode("#e3c06f");
  public static final Color DARK_TILE_COLOR = Color.decode("#b88a4a");
  static BoardDirection boardDirection;
  static BoardPanel boardPanel;
  static Board chessBoard;
  static boolean shouldHighlight;

  public Table() {
    chessBoard = Board.createStandardChessboard();

    JFrame gameFrame = new JFrame("JChess");
    gameFrame.setLayout(new BorderLayout());

    // Set inner details
    final JMenuBar menuBar = createMenuBar();
    gameFrame.setJMenuBar(menuBar);

    // set board orientation
    boardDirection = BoardDirection.NORMAL;

    // set highlight moves to true by default
    shouldHighlight = true;

    // set board
    boardPanel = new BoardPanel();
    gameFrame.add(boardPanel, BorderLayout.CENTER);
    // Set frame properties
    gameFrame.setSize(OUTER_FRAME_DIMENSION);
    gameFrame.setResizable(false);
    gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    gameFrame.setLocationRelativeTo(null);
    gameFrame.setVisible(true);
  }

  private JMenuBar createMenuBar() {
    final JMenuBar menuBar = new JMenuBar();
    menuBar.add(createFileMenu());
    menuBar.add(createPreferencesMenu());
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

  private JMenu createPreferencesMenu() {
    final JMenu preferencesMenu = new JMenu("Preferences");
    // Flip Board option
    final JMenuItem flipBoard = new JMenuItem("Flip Board");
    flipBoard.addActionListener(
        e -> {
          boardDirection = boardDirection.opposite();
          boardPanel.drawBoard(chessBoard);
        });
    preferencesMenu.add(flipBoard);
    preferencesMenu.addSeparator();
    // Highlight Moves
    final JCheckBoxMenuItem highlightMoves = new JCheckBoxMenuItem("Highlight Moves", true);
    highlightMoves.addActionListener(
        e -> {
          shouldHighlight = highlightMoves.isSelected();
          boardPanel.drawBoard(chessBoard);
        });
    preferencesMenu.add(highlightMoves);
    return preferencesMenu;
  }
}
