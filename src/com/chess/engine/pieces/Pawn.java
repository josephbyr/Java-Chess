package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.engine.Colour;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.board.Move.*;
import com.google.common.collect.ImmutableList;

public class Pawn extends Piece{

    private final static int[] CANDIDATE_MOVE_COORDINATES = {8};

    Pawn(final int piecePosition, final Colour pieceColour) {
        super(piecePosition, pieceColour);
    }

    @Override
    public List<Move> calcLegalMoves(final Board board) {
        
        final List<Move> legalMoves = new ArrayList<>();
        
        for(final int currentCandidateOffset: CANDIDATE_MOVE_COORDINATES){
            final int candidateDestinationCoordinate = this.piecePosition + (this.getPieceColour().getDirection() * currentCandidateOffset);

            if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                continue;
            }

            if(currentCandidateOffset == 8 && board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                // TODO deal with promotions
                legalMoves.add(new NonAttackMove(board, this, candidateDestinationCoordinate));
            } 
            else if(currentCandidateOffset == 16 && this.isFirstMove() && 
                    (BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceColour().isBlack()) || 
                    (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceColour().isWhite())){
                final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceColour.getDirection() * 8);
                if(!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() && 
                    !board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                        legalMoves.add(new NonAttackMove(board, this, candidateDestinationCoordinate));
                }
            }
        }
        return null;
    }
}
