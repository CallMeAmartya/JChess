package com.chess.engine;

import com.chess.engine.board.Board;

public class JChess {

  public static void main(String[] args) {
    Board board = Board.createStandardChessboard();
    System.out.println(board);
  }
}
