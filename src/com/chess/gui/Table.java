package com.chess.gui;

import java.awt.*;
import javax.swing.*;

public class Table {

  private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
  private final JFrame gameFrame;

  public Table() {
    this.gameFrame = new JFrame("JChess");
    // Set inner details
    final JMenuBar menuBar = new JMenuBar();
    populateMenuBar(menuBar);
    this.gameFrame.setJMenuBar(menuBar);
    // Set frame properties
    this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
    this.gameFrame.setVisible(true);
  }

  private void populateMenuBar(JMenuBar menuBar) {
    menuBar.add(createFileMenu());
  }

  private JMenu createFileMenu() {
    final JMenu fileMenu = new JMenu("File");
    final JMenuItem openPGN = new JMenuItem("Load PGN File");
    openPGN.addActionListener(e -> System.out.println("open up that pgn file!"));
    fileMenu.add(openPGN);
    return fileMenu;
  }
}
