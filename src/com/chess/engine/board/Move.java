package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

public abstract class Move {
    final Board board;
    final Piece movedPiece;
    final int destinationCoordinate;

    private Move(final Board board, final Piece movedPiece, final int destinationCoordinate){
            this.board = board;
            this.movedPiece = movedPiece;
            this.destinationCoordinate = destinationCoordinate;
    }

    public int getDestinationCoordinate() {
		return this.destinationCoordinate;
	}

    public Piece getMovedPiece(){
        return this.movedPiece;
    }

    public abstract Board execute();

    public static final class NonAttackMove extends Move{
        public NonAttackMove(  final Board board, 
                    final Piece movedPiece, 
                    final int destinationCoordinate){
            super(board, movedPiece, destinationCoordinate);
        }

        @Override
        public Board execute() {
            final Board.Builder builder = new Board.Builder();
            for(final Piece piece: this.board.currentPlayer().getActivePieces()){
                // TODO hashcode and equals for pieces
                if(!this.movedPiece.equals(piece)){
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece: this.board.currentPlayer().getOpponent().getActivePieces()){
                builder.setPiece(piece);
            }
            // move the moved piece
            builder.setPiece(this.movedPiece.movePiece(this));
            // switch to next player's turn
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getColour());
            return builder.build();
        }
    }

    public static final class AttackMove extends Move{
        final Piece attackedPiece;
        public AttackMove( final Board board, 
                    final Piece movedPiece, 
                    final int destinationCoordinate,
                    final Piece attackedPiece){
            super(board, movedPiece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public Board execute() {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
