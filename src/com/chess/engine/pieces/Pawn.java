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

    Pawn(int piecePosition, Colour pieceColour) {
        super(piecePosition, pieceColour);
    }

    @Override
    public List<Move> calcLegalMoves(Board board) {
        
        final List<Move> legalMoves = new ArrayList<>();
        
        for(final int currentCandidateOffset: CANDIDATE_MOVE_COORDINATES){
            int candidateDestinationCoordinate = this.piecePosition + (this.getPieceColour().getDirection() * currentCandidateOffset);

            if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                continue;
            }

            if(currentCandidateOffset == 8 && board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                // TODO more here!
                legalMoves.add(new NonAttackMove(board, this, candidateDestinationCoordinate));
            }
        }

        return null;
    }
    
}
