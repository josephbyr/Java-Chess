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
    private final int cachedHashCode;

    Piece(final PieceType pieceType,
          final Colour pieceColour, 
          final int piecePosition){
        this.pieceType = pieceType;
        this.pieceColour = pieceColour;
        this.piecePosition = piecePosition;
        // TODO more here!
        this.isFirstMove = false;
        this.cachedHashCode = computeHashCode();
    }

   private int computeHashCode() {
        int result = pieceType.hashCode();
        result = 31 * result + pieceColour.hashCode();
        result = 31 * result + piecePosition;
        result = 31 * result + (isFirstMove ? 1 : 0);
        return result;
    }

    // changes equals() from reference equality to object equality
    @Override
    public boolean equals(final Object other) {
        if(this == other){
            return true;
        }
        if(!(other instanceof Piece)){
            return false;
        }
        final Piece otherPiece = (Piece)other;
        return piecePosition == otherPiece.getPiecePosition() && pieceType == otherPiece.getPieceType() && 
               pieceColour == otherPiece.getPieceColour() && isFirstMove == otherPiece.isFirstMove();
    }

    @Override
    public int hashCode(){
        return this.cachedHashCode;
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

    public abstract Piece movePiece(Move move);

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
