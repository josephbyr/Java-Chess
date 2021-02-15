package com.chess.engine.board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chess.engine.Colour;
import com.chess.engine.pieces.Bishop;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Knight;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Queen;
import com.chess.engine.pieces.Rook;
import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

public class Board {

    private final List<Tile> gameBoard;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;

    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private final Player currentPlayer;

    private Board(final Builder builder){
        this.gameBoard = createGameBoard(builder);
        this.whitePieces = calculateActivePieces(this.gameBoard, Colour.WHITE);
        this.blackPieces = calculateActivePieces(this.gameBoard, Colour.BLACK);
    
        final Collection<Move> whiteStandardLegalMoves = calcLegalMoves(this.whitePieces);
        final Collection<Move> blackStandardLegalMoves = calcLegalMoves(this.blackPieces);

        this.whitePlayer = new WhitePlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
        this.blackPlayer = new BlackPlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
        this.currentPlayer = builder.nextMoveMaker.choosePlayer(this.whitePlayer, this.blackPlayer);
    }

    @Override
    public String toString(){
        final StringBuilder builder = new StringBuilder();
        for(int i = 0; i < BoardUtils.NUM_TILES; i++){
            final String tileText = this.gameBoard.get(i).toString();
            builder.append(String.format("%3s", tileText));
            if((i + 1) % BoardUtils.NUM_TILES_PER_RANK == 0){
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    public Player whitePlayer(){
        return this.whitePlayer;
    }

    public Player blackPlayer(){
        return this.blackPlayer;
    }

    public Player currentPlayer() {
        return this.currentPlayer;
    }

    public Collection<Piece> getBlackPieces(){
        return this.blackPieces;
    }

    public Collection<Piece> getWhitePieces(){
        return this.whitePieces;
    }

    private Collection<Move> calcLegalMoves(final Collection<Piece> pieces) {
        final List<Move> legalMoves = new ArrayList<>();
        for(final Piece piece : pieces) {
            legalMoves.addAll(piece.calcLegalMoves(this));
        }
        return ImmutableList.copyOf(legalMoves);
    }

    // tracks active pieces on board
    private Collection<Piece> calculateActivePieces(final List<Tile> gameBoard, final Colour colour) {
        final List<Piece> activePieces = new ArrayList<>();
        for(final Tile tile: gameBoard) {
            if(tile.isTileOccupied()){
                final Piece piece = tile.getPiece();
                if(piece.getPieceColour() == colour){
                    activePieces.add(piece);
                }
            }
        }
        return ImmutableList.copyOf(activePieces);
    }

    public Tile getTile(final int tileCoordinate){
        return gameBoard.get(tileCoordinate);
    }

    private static List<Tile> createGameBoard(Builder builder){
        final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
        for(int i = 0; i < BoardUtils.NUM_TILES; i++){
            tiles[i] = Tile.createTile(i, builder.boardConfig.get(i));
        }
        return ImmutableList.copyOf(tiles);
    }

    // set pieces to starting position
    public static Board createInitialBoard(){
        final Builder builder = new Builder();
        // black Layout
        builder.setPiece(new Rook(Colour.BLACK, 0));
        builder.setPiece(new Knight(Colour.BLACK, 1));
        builder.setPiece(new Bishop(Colour.BLACK, 2));
        builder.setPiece(new Queen(Colour.BLACK, 3));
        builder.setPiece(new King(Colour.BLACK, 4));
        builder.setPiece(new Bishop(Colour.BLACK, 5));
        builder.setPiece(new Knight(Colour.BLACK, 6));
        builder.setPiece(new Rook(Colour.BLACK, 7));
        builder.setPiece(new Pawn(Colour.BLACK, 8));
        builder.setPiece(new Pawn(Colour.BLACK, 9));
        builder.setPiece(new Pawn(Colour.BLACK, 10));
        builder.setPiece(new Pawn(Colour.BLACK, 11));
        builder.setPiece(new Pawn(Colour.BLACK, 12));
        builder.setPiece(new Pawn(Colour.BLACK, 13));
        builder.setPiece(new Pawn(Colour.BLACK, 14));
        builder.setPiece(new Pawn(Colour.BLACK, 15));
        // white Layout
        builder.setPiece(new Pawn(Colour.WHITE, 48));
        builder.setPiece(new Pawn(Colour.WHITE, 49));
        builder.setPiece(new Pawn(Colour.WHITE, 50));
        builder.setPiece(new Pawn(Colour.WHITE, 51));
        builder.setPiece(new Pawn(Colour.WHITE, 52));
        builder.setPiece(new Pawn(Colour.WHITE, 53));
        builder.setPiece(new Pawn(Colour.WHITE, 54));
        builder.setPiece(new Pawn(Colour.WHITE, 55));
        builder.setPiece(new Rook(Colour.WHITE, 56));
        builder.setPiece(new Knight(Colour.WHITE, 57));
        builder.setPiece(new Bishop(Colour.WHITE, 58));
        builder.setPiece(new Queen(Colour.WHITE, 59));
        builder.setPiece(new King(Colour.WHITE, 60));
        builder.setPiece(new Bishop(Colour.WHITE, 61));
        builder.setPiece(new Knight(Colour.WHITE, 62));
        builder.setPiece(new Rook(Colour.WHITE, 63));
        //white to move
        builder.setMoveMaker(Colour.WHITE);
        //build the board
        return builder.build();
    }

    public Iterable<Move> getAllLegalMoves() {
		return Iterables.unmodifiableIterable(Iterables.concat(this.whitePlayer.getLegalMoves(), this.blackPlayer.getLegalMoves()));
	}

    public static class Builder{

        Map<Integer, Piece> boardConfig;
        Colour nextMoveMaker;
        Pawn enPassantPawn;

        public Builder(){
            this.boardConfig = new HashMap<>();
        }

        public Builder setPiece(final Piece piece){
            this.boardConfig.put(piece.getPiecePosition(), piece);
            return this;
        }

        public Builder setMoveMaker(final Colour nextMoveMaker){
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }

        public Board build(){
            return new Board(this);
        }

		public void setEnPassantPawn(Pawn enPassantPawn) {
            this.enPassantPawn = enPassantPawn;
		}
    }
}
