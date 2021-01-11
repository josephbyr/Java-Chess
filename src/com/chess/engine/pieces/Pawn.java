package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.engine.Colour;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.*;
import com.google.common.collect.ImmutableList;

public class Pawn extends Piece{

    private final static int[] CANDIDATE_MOVE_COORDINATES = {8, 16, 7, 9};

    Pawn(final int piecePosition, final Colour pieceColour) {
        super(piecePosition, pieceColour);
    }

    @Override
    public List<Move> calcLegalMoves(final Board board) {
        
        final List<Move> legalMoves = new ArrayList<>();
        
        for(final int currentCandidateOffset: CANDIDATE_MOVE_COORDINATES){
            final int candidateDestinationCoordinate = this.piecePosition + (this.pieceColour.getDirection() * currentCandidateOffset);

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
            // if pawn is on respective right hand side edge of board and isn't in an exception movement case
            else if(currentCandidateOffset == 7 &&
                    !((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.getPieceColour().isWhite() || 
                    (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.getPieceColour().isBlack())))){
                if(board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if(this.pieceColour != pieceOnCandidate.getPieceColour()){
                        // TODO more here!
                        legalMoves.add(new NonAttackMove(board, this, candidateDestinationCoordinate));
                    }
                }
            }
            // if pawn is on respective left hand edge of board and isn't in an exception movement case
            else if(currentCandidateOffset == 9 && 
                    !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.getPieceColour().isWhite() || 
                    (BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.getPieceColour().isBlack())))){
                if(board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if(this.pieceColour != pieceOnCandidate.getPieceColour()){
                        // TODO more here!
                        legalMoves.add(new NonAttackMove(board, this, candidateDestinationCoordinate));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }
}
