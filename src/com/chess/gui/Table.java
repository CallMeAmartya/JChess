package com.chess.gui;

import com.chess.engine.board.Board;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Table {

  public static final String PIECE_ICON_FOLDER_PATH = "art/pieces/plain_no_shadow/";
  public static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
  public static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
  public static final Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);
  public static final Color LIGHT_TILE_COLOR = Color.decode("#e3c06f");
  public static final Color DARK_TILE_COLOR = Color.decode("#b88a4a");

  public Table() {
    Board chessBoard = Board.createStandardChessboard();

    JFrame gameFrame = new JFrame("JChess");
    gameFrame.setLayout(new BorderLayout());

    // Set inner details
    final JMenuBar menuBar = createMenuBar();
    gameFrame.setJMenuBar(menuBar);

    BoardPanel boardPanel = new BoardPanel(chessBoard);
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
}
