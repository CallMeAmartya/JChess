package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.List;

public abstract class Piece {

    protected final int piecePosition;

    protected final Alliance alliance;

    public Piece(int piecePosition, Alliance alliance) {
        this.piecePosition = piecePosition;
        this.alliance = alliance;
    }

    public abstract List<Move> calculateMoves(Board board);
}
