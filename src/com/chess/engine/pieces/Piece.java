package com.chess.engine.pieces;

import com.chess.engine.Colour;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.List;

public abstract class Piece {
    protected final PieceType pieceType;
    protected final int piecePosition;
    protected final Colour pieceColour;
    protected final boolean isFirstMove;

    Piece(final PieceType pieceType,
          final Colour pieceColour, 
          final int piecePosition){
        this.pieceType = pieceType;
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

    public PieceType getPieceType(){
        return this.pieceType;
    }

    public abstract List<Move>calcLegalMoves(final Board board);

    public enum PieceType{

        PAWN("P"),
        KNIGHT("N"),
        BISHOP("B"),
        ROOK("R"),
        QUEEN("Q"),
        KING("K");
        
        private String pieceName;
        
        PieceType(final String pieceName){
            this.pieceName = pieceName;
        }

        @Override
        public String toString(){
            return this.pieceName;
        }
    }    
}
