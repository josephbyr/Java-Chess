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

public class Knight extends Piece{
    // https://www.chessprogramming.org/Knight_Pattern
    private final static int[] CANDIDATE_MOVE_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};

    public Knight(final Colour pieceColour, final int piecePosition) {
        super(pieceColour, piecePosition);
    }

    @Override
    public List<Move> calcLegalMoves(final Board board){

        final List<Move> legalMoves = new ArrayList<>();

        for(int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES){
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
        
            // if valid tile coordinate
            if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){

                if(isFirstColumnException(this.piecePosition, currentCandidateOffset) || 
                        isSecondColumnException(this.piecePosition, currentCandidateOffset) || 
                        isSeventhColumnException(this.piecePosition, currentCandidateOffset) || 
                        isEighthColumnException(this.piecePosition, currentCandidateOffset)){
                    continue;
                }

                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if(!candidateDestinationTile.isTileOccupied()){
                    legalMoves.add(new NonAttackMove(board, this, candidateDestinationCoordinate));
                }
                else{
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Colour pieceColour = pieceAtDestination.getPieceColour();

                    if(this.pieceColour != pieceColour){
                        legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                    }
                }
            }

        }
        return ImmutableList.copyOf(legalMoves);
    }

    // Exceptions for knight movement
    private static boolean isFirstColumnException(final int currentPos, final int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPos] && ((candidateOffset == -17) || (candidateOffset == -10) || 
        (candidateOffset == 6) || (candidateOffset == 15));
    }

    private static boolean isSecondColumnException(final int currentPos, final int candidateOffset){
        return BoardUtils.SECOND_COLUMN[currentPos] && ((candidateOffset == -10) || (candidateOffset == 6));
    }

    private static boolean isSeventhColumnException(final int currentPos, final int candidateOffset){
        return BoardUtils.SEVENTH_COLUMN[currentPos] && ((candidateOffset == -6) || (candidateOffset == 10));
    }

    private static boolean isEighthColumnException(final int currentPos, final int candidateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPos] && ((candidateOffset == -15) || (candidateOffset == -6) || 
        (candidateOffset == 10) || (candidateOffset == 17));
    }
}
