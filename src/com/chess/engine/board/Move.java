package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

public abstract class Move {
    final Board board;
    final Piece movedPiece;
    final int destinationCoordinate;
    
    public static final Move NULL_MOVE = new NullMove();

    private Move(final Board board, final Piece movedPiece, final int destinationCoordinate){
            this.board = board;
            this.movedPiece = movedPiece;
            this.destinationCoordinate = destinationCoordinate;
    }

    public int getCurrentCoordinate(){
        return this.movedPiece.getPiecePosition();
    }

    public int getDestinationCoordinate() {
		return this.destinationCoordinate;
	}

    public Piece getMovedPiece(){
        return this.movedPiece;
    }

    public Board execute(){
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
    };

    public static final class NonAttackMove extends Move{
        public NonAttackMove(  final Board board, 
                    final Piece movedPiece, 
                    final int destinationCoordinate){
            super(board, movedPiece, destinationCoordinate);
        }
    }

    public static class AttackMove extends Move{
        final Piece attackedPiece;
        public AttackMove( final Board board, 
                    final Piece movedPiece, 
                    final int destinationCoordinate,
                    final Piece attackedPiece){
            super(board, movedPiece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }
    }

    public static final class PawnMove extends Move{
        public PawnMove(final Board board, 
                        final Piece movedPiece, 
                        final int destinationCoordinate){
            super(board, movedPiece, destinationCoordinate);
        }
    }

    public static class PawnAttackMove extends AttackMove{
        public PawnAttackMove(final Board board, 
                              final Piece movedPiece, 
                              final int destinationCoordinate,
                              final Piece attackedPiece){
            super(board, movedPiece, destinationCoordinate, attackedPiece);
        }
    }

    public static final class PawnEnPassantAttackMove extends PawnAttackMove{
        public PawnEnPassantAttackMove(final Board board, 
                              final Piece movedPiece, 
                              final int destinationCoordinate,
                              final Piece attackedPiece){
            super(board, movedPiece, destinationCoordinate, attackedPiece);
        }
    }

    public static final class PawnJump extends Move{
        public PawnJump(final Board board, 
                        final Piece movedPiece, 
                        final int destinationCoordinate){
            super(board, movedPiece, destinationCoordinate);
        }
    }

    static abstract class CastleMove extends Move{
        public CastleMove(  final Board board, 
                            final Piece movedPiece, 
                            final int destinationCoordinate){
            super(board, movedPiece, destinationCoordinate);
        }
    }

    public static final class KingSideCastleMove extends CastleMove{
        public KingSideCastleMove(  final Board board, 
                                    final Piece movedPiece, 
                                    final int destinationCoordinate){
            super(board, movedPiece, destinationCoordinate);
        }
    }

    public static final class QueenSideCastleMove extends CastleMove{
        public QueenSideCastleMove( final Board board, 
                                    final Piece movedPiece, 
                                    final int destinationCoordinate){
            super(board, movedPiece, destinationCoordinate);
        }
    }

    public static final class NullMove extends Move{
        public NullMove(){
            super(null, null, -1);
        }

        @Override
        public Board execute(){
            throw new RuntimeException("Cannot execute null move");
        }
    }

    public static class MoveFactory{
        private MoveFactory(){
            throw new RuntimeException("Not instansiable");
        }

        public static Move createMove(final Board board, final int currentCoordinate, final int destinationCoordinate){

            for(final Move move : board.getAllLegalMoves()){
                if(move.getCurrentCoordinate() == currentCoordinate &&
                   move.getDestinationCoordinate() == destinationCoordinate){
                       return move;
                }
            }
            return NULL_MOVE;
        }
    }
}
