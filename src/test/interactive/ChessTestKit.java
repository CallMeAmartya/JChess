package test.interactive;

import static com.chess.engine.utils.BoardUtils.NUM_TILES_PER_ROW;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.board.TwoDimensionalCoordinate;
import com.chess.engine.enums.Alliance;
import com.chess.engine.pieces.*;
import com.chess.engine.utils.BoardUtils;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import test.enums.Piece;

public class ChessTestKit {

  // ANSI color codes
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";
  private static final Scanner scanner = new Scanner(System.in);
  private static Board board;

  public static void main(String[] args) {
    greetUser();
    while (true) {
      Board.BoardBuilder boardBuilder = new Board.BoardBuilder();
      if (!selectPiece(boardBuilder)) {
        continue;
      }
      if (!placeAdditionalPiece()) {
        board = boardBuilder.build();
        String[][] sb = getAndPrintBoard();
        targetPiece(sb);
      }
    }
  }

  private static void targetPiece(String[][] sb) {
    System.out.println("Select a chess piece to see its path!");
    int x = getIntInput("Enter the x-coordinate (0-7) of this piece: ");
    int y = getIntInput("Enter the y-coordinate (0-7) of this piece: ");
    Tile tile =
        board.getTile(BoardUtils.getPositionFrom2DCoordinate(new TwoDimensionalCoordinate(x, y)));
    com.chess.engine.pieces.Piece piece = tile.getPiece();
    Collection<Move> moves = piece.calculateLegalMoves(board);
    for (Move move : moves) {
      TwoDimensionalCoordinate twoDimensionalCoordinate =
          BoardUtils.get2DCoordinateFromPosition(move.getDestinationIndex());
      int t_x = twoDimensionalCoordinate.getX();
      int t_y = twoDimensionalCoordinate.getY();
      sb[t_x][t_y] = "o";
    }
    printBoard(sb);
    System.exit(0);
  }

  private static void greetUser() {
    System.out.println(ANSI_CYAN + "***********************************************");
    System.out.println("*                                             *");
    System.out.println("*            Welcome to Chess Testing         *");
    System.out.println("*                                             *");
    System.out.println("***********************************************" + ANSI_RESET);
    System.out.println();
    System.out.println("Let's place some chess pieces on the board.\n");
  }

  private static boolean selectPiece(Board.BoardBuilder boardBuilder) {
    System.out.println("Select a chess piece:");
    Map<Integer, Piece> map = new HashMap<>();
    int count = 1;
    for (Piece piece : Piece.values()) {
      map.put(count, piece);
      System.out.println(count++ + ". " + piece.name());
    }
    int choice = getIntInput("Enter your choice: ");
    if (map.containsKey(choice)) {
      System.out.println("You selected " + map.get(choice));
    } else {
      System.out.println("Invalid choice, please select again from the given list");
      return false;
    }
    String color = getStringInput("Is this black or white piece: ").toUpperCase();
    int x = getIntInput("Enter the x-coordinate (0-7) on the board: ");
    int y = getIntInput("Enter the y-coordinate (0-7) on the board: ");
    // Place the piece on the board
    int pieceIndex = BoardUtils.getPositionFrom2DCoordinate(new TwoDimensionalCoordinate(x, y));
    boardBuilder.setPiece(getPieceFromChoice(map.get(choice), pieceIndex, color));
    return true;
  }

  private static com.chess.engine.pieces.Piece getPieceFromChoice(
      Piece piece, int pieceIndex, String color) {
    switch (piece) {
      case BISHOP -> {
        return new Bishop(pieceIndex, Alliance.valueOf(color));
      }
      case KNIGHT -> {
        return new Knight(pieceIndex, Alliance.valueOf(color));
      }
      case ROOK -> {
        return new Rook(pieceIndex, Alliance.valueOf(color));
      }
      case QUEEN -> {
        return new Queen(pieceIndex, Alliance.valueOf(color));
      }
      case PAWN -> {
        return new Pawn(pieceIndex, Alliance.valueOf(color));
      }
      case KING -> {
        return new King(pieceIndex, Alliance.valueOf(color));
      }
      default -> {
        return null;
      }
    }
  }

  private static boolean placeAdditionalPiece() {
    String choice = getStringInput("Do you want to place another piece? (yes/no): ");
    return choice.equalsIgnoreCase("yes");
  }

  private static String[][] getAndPrintBoard() {
    System.out.println("\nPrinting the board with placed pieces...");
    // Print the 2D board with placed pieces
    String[][] sb = new String[NUM_TILES_PER_ROW][NUM_TILES_PER_ROW];
    for (int i = 0; i < NUM_TILES_PER_ROW; i++) {
      for (int j = 0; j < NUM_TILES_PER_ROW; j++) {
        Tile tile =
            board.getTile(
                BoardUtils.getPositionFrom2DCoordinate(new TwoDimensionalCoordinate(i, j)));
        if (!tile.isTileOccupied()) {
          sb[i][j] = getColoredText(tile.toString(), ANSI_YELLOW);
        } else {
          com.chess.engine.pieces.Piece piece = tile.getPiece();
          if (piece.getAlliance().equals(Alliance.BLACK)) {
            sb[i][j] = getColoredText(piece.toString(), ANSI_PURPLE);
          } else {
            sb[i][j] = getColoredText(piece.toString(), ANSI_WHITE);
          }
        }
      }
    }
    printBoard(sb);
    return sb;
  }

  private static void printBoard(String[][] sb) {
    for (String[] sbRow : sb) {
      for (String s : sbRow) {
        System.out.print(" " + s + " ");
      }
      System.out.println();
    }
  }

  private static int getIntInput(String message) {
    System.out.print(message);
    while (!scanner.hasNextInt()) {
      System.out.print("Invalid input. Please enter a number: ");
      scanner.next();
    }
    return scanner.nextInt();
  }

  private static String getStringInput(String message) {
    System.out.print(message);
    return scanner.next();
  }

  public static String getColoredText(String text, String color) {
    return color + text + ANSI_RESET;
  }
}
