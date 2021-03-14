package com.chess.engine.board;

import com.chess.engine.board.Board.Builder;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;

public abstract class Move {
    protected final Board board;
    protected final Piece movedPiece;
    protected final int destinationCoordinate;
    protected boolean isFirstMove; 
    
    public static final Move NULL_MOVE = new NullMove();

    private Move(final Board board, final Piece movedPiece, final int destinationCoordinate){
            this.board = board;
            this.movedPiece = movedPiece;
            this.destinationCoordinate = destinationCoordinate;
            this.isFirstMove = movedPiece.isFirstMove();
    }

    private Move(final Board board, final int destinationCoordinate){
        this.board = board;
        this.destinationCoordinate = destinationCoordinate;
        this.movedPiece = null;
        this.isFirstMove = false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.destinationCoordinate;
        result = prime * result + this.movedPiece.hashCode();
        result = prime * result + this.movedPiece.getPiecePosition();
        return result;
    }

    @Override
    public boolean equals(final Object other){
        if(this == other){
            return true;
        }
        if(!(other instanceof Move)){
            return false;
        }
        final Move otherMove = (Move)other;
        return getCurrentCoordinate() == otherMove.getCurrentCoordinate() &&
        getDestinationCoordinate() == otherMove.getDestinationCoordinate() &&
        getMovedPiece().equals(otherMove.getMovedPiece());
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

    public boolean isAttack(){
        return false;
    }

    public boolean isCastlingMove(){
        return false;
    }

    public Piece getAttackedPiece(){
        return null;
    }

    public Board execute(){
        final Board.Builder builder = new Board.Builder();
            for(final Piece piece: this.board.currentPlayer().getActivePieces()){
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

        @Override
        public boolean equals(final Object other){
            return this == other || (other instanceof NonAttackMove && super.equals(other));
        }

        @Override
        public String toString(){
            return movedPiece.getPieceType().toString() + BoardUtils.getPositionAtCoordinate(destinationCoordinate);
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

        @Override
        public int hashCode(){
            return this.attackedPiece.hashCode() + super.hashCode();
        }

        @Override
        public boolean equals(final Object other){
            if(this == other){
                return true;
            }
            if(!(other instanceof AttackMove)){
                return false;
            }
            final Move otherAttackMove = (AttackMove)other;
            return super.equals(otherAttackMove) && getAttackedPiece().equals(otherAttackMove.getAttackedPiece());
        }

        @Override
        public boolean isAttack(){
            return true;
        }

        @Override
        public Piece getAttackedPiece(){
            return this.attackedPiece;
        }
    }

    public static class PieceAttackMove extends AttackMove{

        public PieceAttackMove(final Board board,
                               final Piece pieceMoved,
                               final int destinationCoordinate,
                               final Piece pieceAttacked){
            super(board, pieceMoved, destinationCoordinate, pieceAttacked);
        }

        @Override
        public boolean equals(final Object other){
            return this == other || other instanceof PieceAttackMove && super.equals(other);
        }

        @Override
        public String toString(){
            return movedPiece.getPieceType() + 
                   BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
        }
    }

    public static final class PawnMove extends Move{
        public PawnMove(final Board board, 
                        final Piece movedPiece, 
                        final int destinationCoordinate){
            super(board, movedPiece, destinationCoordinate);
        }

        @Override
        public boolean equals(final Object other){
            return this == other || other instanceof PawnMove && super.equals(other);
        }

        @Override
        public String toString(){
            return BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
        }
    }

    public static class PawnAttackMove extends AttackMove{
        public PawnAttackMove(final Board board, 
                              final Piece movedPiece, 
                              final int destinationCoordinate,
                              final Piece attackedPiece){
            super(board, movedPiece, destinationCoordinate, attackedPiece);
        }

        @Override
        public boolean equals(final Object other){
            return this == other || other instanceof PawnAttackMove && super.equals(other);
        }

        @Override
        public String toString(){
            return BoardUtils.getPositionAtCoordinate(this.movedPiece.getPiecePosition()).substring(0, 1) + "x" + 
                   BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
        }
    }

    public static final class PawnEnPassantAttackMove extends PawnAttackMove{
        public PawnEnPassantAttackMove(final Board board, 
                              final Piece movedPiece, 
                              final int destinationCoordinate,
                              final Piece attackedPiece){
            super(board, movedPiece, destinationCoordinate, attackedPiece);
        }

        @Override
        public boolean equals(final Object other){
            return this == other || other instanceof PawnEnPassantAttackMove && super.equals(other);
        }

        @Override
        public Board execute(){
            final Builder builder = new Builder();
            for(final Piece piece : board.currentPlayer().getActivePieces()){
                if(!(this.movedPiece.equals(piece))){
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece : this.board.currentPlayer().getActivePieces()){
                if(!piece.equals(this.getAttackedPiece())){
                    builder.setPiece(piece);
                }
            }
            builder.setPiece(this.movedPiece.movePiece(this));
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getColour());
            return builder.build();
        }
    }

    public static final class PawnJump extends Move{
        public PawnJump(final Board board, 
                        final Piece movedPiece, 
                        final int destinationCoordinate){
            super(board, movedPiece, destinationCoordinate);
        }

        @Override
        public Board execute(){
            final Builder builder = new Builder();
            for(final Piece piece: this.board.currentPlayer().getActivePieces()){
                if(!this.movedPiece.equals(piece)){
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece: this.board.currentPlayer().getOpponent().getActivePieces()){
                builder.setPiece(piece);
            }
            final Pawn movedPawn = (Pawn)this.movedPiece.movePiece(this);
            builder.setPiece(movedPawn);
            builder.setEnPassantPawn(movedPawn);
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getColour());
            return builder.build();
        }

        @Override
        public String toString(){
            return BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
        }
    }

    static abstract class CastleMove extends Move{

        protected final Rook castleRook;
        protected final int castleRookInitPos;
        protected final int castleRookDestPos;

        public CastleMove(  final Board board, 
                            final Piece movedPiece, 
                            final int destinationCoordinate,
                            final Rook castleRook,
                            final int castleRookInitPos,
                            final int castleRookDestPos){
            super(board, movedPiece, destinationCoordinate);
            this.castleRook = castleRook;
            this.castleRookInitPos = castleRookInitPos;
            this.castleRookDestPos = castleRookDestPos;
        }

        public Rook getCastleRook(){
            return this.castleRook;
        }

        @Override
        public boolean isCastlingMove(){
            return true;
        }

        @Override
        public Board execute(){
        final Builder builder = new Builder();
            for(final Piece piece: this.board.currentPlayer().getActivePieces()){
                if(!this.movedPiece.equals(piece) && !this.castleRook.equals(piece)){
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece: this.board.currentPlayer().getOpponent().getActivePieces()){
                builder.setPiece(piece);
            }
            builder.setPiece(this.movedPiece.movePiece(this));
            builder.setPiece(new Rook(this.castleRook.getPieceColour(), this.castleRookDestPos));
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getColour());
            return builder.build();
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = super.hashCode();
            result = prime * result + this.castleRook.hashCode();
            result = prime * result + this.castleRookDestPos;
            return result;
        }

        @Override
        public boolean equals(Object other) {
            if(this == other){
                return true;
            }
            if(!(other instanceof CastleMove)){
                return false;
            }
            final CastleMove otherCastleMove = (CastleMove)other;
            return super.equals(otherCastleMove) && this.castleRook.equals(otherCastleMove.getCastleRook());
        }
    }

    public static final class KingSideCastleMove extends CastleMove{
        public KingSideCastleMove(  final Board board, 
                                    final Piece movedPiece, 
                                    final int destinationCoordinate,
                                    final Rook castleRook,
                                    final int castleRookInitPos,
                                    final int castleRookDestPos){
            super(board, movedPiece, destinationCoordinate, castleRook, castleRookInitPos, castleRookDestPos);
        }

        @Override
        public boolean equals(final Object other){
            return this == other || other instanceof KingSideCastleMove && super.equals(other);
        }

        @Override
        public String toString(){
            return "O-O";
        }
    }

    public static final class QueenSideCastleMove extends CastleMove{
        public QueenSideCastleMove( final Board board, 
                                    final Piece movedPiece, 
                                    final int destinationCoordinate,
                                    final Rook castleRook,
                                    final int castleRookInitPos,
                                    final int castleRookDestPos){
            super(board, movedPiece, destinationCoordinate, castleRook, castleRookInitPos, castleRookDestPos);
        }

        @Override
        public boolean equals(final Object other){
            return this == other || other instanceof QueenSideCastleMove && super.equals(other);
        }

        @Override
        public String toString(){
            return "O-O-O";
        }
    }

    public static final class NullMove extends Move{
        public NullMove(){
            super(null, -1);
        }

        @Override
        public Board execute(){
            throw new RuntimeException("Cannot execute null move");
        }

        @Override
        public int getCurrentCoordinate() {
            return -1;
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
