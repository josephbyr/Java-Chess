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

public class Queen extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = {-9, -8, -7, -1, 1, 7, 8, 9};
    
    public Queen(final Colour pieceColour, final int piecePosition) {
        super(PieceType.QUEEN, pieceColour, piecePosition);
    }

    @Override
    public List<Move> calcLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();
        
        for(final int candidateCoordinateOffset: CANDIDATE_MOVE_COORDINATES){
            int candidateDestinationCoordinate = this.piecePosition;

            while(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                
                if(isFirstColumnException(candidateDestinationCoordinate, candidateCoordinateOffset) || 
                        isEighthColumnException(candidateDestinationCoordinate, candidateCoordinateOffset)){
                    break;
                }

                candidateDestinationCoordinate += candidateCoordinateOffset;
                // continue calculating legal moves until blocked by another piece
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
                        break;
                    }
                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Queen movePiece(Move move) {
        return new Queen(move.getMovedPiece().getPieceColour(), move.getDestinationCoordinate());
    }

    @Override
    public String toString(){
        return PieceType.QUEEN.toString();
    }

    // Exceptions for queen movement
    private static boolean isFirstColumnException(final int currentPos, final int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPos] && (candidateOffset == -1 || candidateOffset == -9 || candidateOffset == 7);
    }

    private static boolean isEighthColumnException(final int currentPos, final int candidateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPos] && (candidateOffset == 1 || candidateOffset == -7 || candidateOffset == 9);
    }
}
