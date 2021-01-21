package com.chess.engine.player;

import java.util.Collection;

import com.chess.engine.Colour;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;

public abstract class Player {
    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;

    Player(final Board board, 
           final Collection<Move> legalMoves,
           final Collection<Move> opponentMoves){
        
        this.board = board;
        this.playerKing = establishKing();
        this.legalMoves = legalMoves;
    }

    private King establishKing() {
        for(final Piece piece : getActivePieces()){
            if(piece.getPieceType() == Piece.PieceType.KING){
                return (King) piece;
            }
        }
        throw new RuntimeException("Not a valid board");
    }

    public boolean isMoveLegal(final Move move){
        return this.legalMoves.contains(move);
    }

    // TODO
    public boolean isInCheck(){
        return false;
    }

    public boolean isInCheckMate(){
        return false;
    }

    public boolean isInStaleMate(){
        return false;
    }

    public boolean isCastled(){
        return false;
    }

    public MoveTransition makeMove(final Move move){
        return null;
    }

    public abstract Collection<Piece> getActivePieces();
    public abstract Colour getColour();
    public abstract Player getOpponent();
}
