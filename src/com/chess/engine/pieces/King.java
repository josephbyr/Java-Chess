package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.engine.Colour;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.*;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

public class King extends Piece{

    private final static int[] CANDIDATE_MOVE_COORDINATES = {-9, -8, -7, -1, 1, 7, 8, 9};

    public King(final Colour pieceColour, final int piecePosition) {
        super(PieceType.KING, pieceColour, piecePosition);
    }

    @Override
    public List<Move> calcLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCandidateOffset: CANDIDATE_MOVE_COORDINATES){
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;

            if(isFirstColumnException(this.piecePosition, candidateDestinationCoordinate) || isEighthColumnException(this.piecePosition, candidateDestinationCoordinate)) {
                continue;
            }

            if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
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

    @Override
    public String toString(){
        return PieceType.KING.toString();
    }

    // movement exceptions for king
    private static boolean isFirstColumnException(final int currentPos, final int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPos] && (candidateOffset == -9 || candidateOffset == -1 || 
        candidateOffset == 7);
    }

    private static boolean isEighthColumnException(final int currentPos, final int candidateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPos] && (candidateOffset == -7 || candidateOffset == 1 || 
        candidateOffset == 9);
    }
}
