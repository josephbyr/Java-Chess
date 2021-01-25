package com.chess.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Colour;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;

public abstract class Player {
    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;
    private final boolean isInCheck;

    Player(final Board board, 
           final Collection<Move> legalMoves,
           final Collection<Move> opponentMoves){
        
        this.board = board;
        this.playerKing = establishKing();
        this.legalMoves = legalMoves;
        this.isInCheck = !Player.clacAttacksOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();
    }

    private static Collection<Move> clacAttacksOnTile(int piecePosition, Collection<Move> moves) {
        final List<Move> attackMoves = new ArrayList<>();
        for(final Move move : moves){
            if(piecePosition == move.getDestinationCoordinate()){
                attackMoves.add(move);
            }
        }
        return ImmutableList.copyOf(attackMoves);
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
        return this.isInCheck;
    }

    public boolean isInCheckMate(){
        return this.isInCheck && hasEscapeMoves();
    }
    
    public boolean isInStaleMate(){
        return !this.isInCheck && !hasEscapeMoves();
    }

    // to check if king can escape, make all legal moves on new board
    // and check if moves are possible
    private boolean hasEscapeMoves() {
        for(final Move move: this.legalMoves){
            final MoveTransition transition = makeMove(move);
            if(transition.getMoveStatus().isDone()){
                return true;
            }
        }
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
