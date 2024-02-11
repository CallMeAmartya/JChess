package com.chess.engine;

import com.chess.engine.board.Board;
import com.chess.engine.exceptions.KingNotEstablishedException;

public class JChess {

  public static void main(String[] args) throws KingNotEstablishedException {
    Board board = Board.createStandardChessboard();
    System.out.println(board);
  }
}
