package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.engine.Colour;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

public class Knight extends Piece{
    // https://www.chessprogramming.org/Knight_Pattern
    private final static int[] CANDIDATE_MOVE_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};

    Knight(int piecePosition, Colour pieceColour){
        super(piecePosition, pieceColour);
    }

    @Override
    public List<Move> calcLegalMoves(Board board){
        
        int candidateDestinationCoordinate;
        final List<Move> legalMoves = new ArrayList<>();

        for(int currentCandidate : CANDIDATE_MOVE_COORDINATES){
            candidateDestinationCoordinate = this.piecePosition + currentCandidate;
        
            // if valid tile coordinate TO DO
            if(true){
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);

                if(!candidateDestinationTile.isTileOccupied()){
                    legalMoves.add(new Move());
                }
                else{
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Colour pieceColour = pieceAtDestination.getPieceColour();

                    if(this.pieceColour != pieceColour){
                        legalMoves.add(new Move());
                    }
                }
            }

        }
        return ImmutableList.copyOf(legalMoves);
    }

}
