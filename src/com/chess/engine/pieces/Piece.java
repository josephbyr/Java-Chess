package com.chess.engine.pieces;

import com.chess.engine.Colour;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.List;

public abstract class Piece {
    protected final int piecePosition;
    protected final Colour pieceColour;
    protected final boolean isFirstMove;

    Piece(final Colour pieceColour, final int piecePosition){
        this.pieceColour = pieceColour;
        this.piecePosition = piecePosition;
        // TODO more here!
        this.isFirstMove = false;
    }

    public int getPiecePosition(){
        return this.piecePosition;
    }

    public Colour getPieceColour(){
        return this.pieceColour;
    }

    public boolean isFirstMove(){
        return this.isFirstMove;
    }

    public abstract List<Move>calcLegalMoves(final Board board);

    
}
