package com.chess.engine.pieces;

import com.chess.engine.Colour;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.List;

public abstract class Piece {
    protected final int piecePosition;
    protected final Colour pieceColour;

    Piece(final int piecePosition, final Colour pieceColour){
        this.piecePosition = piecePosition;
        this.pieceColour = pieceColour;
    }

    public abstract List<Move>calcLegalMoves(final Board board);

    
}
