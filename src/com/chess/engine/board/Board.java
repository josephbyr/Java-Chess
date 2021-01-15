package com.chess.engine.board;

import java.util.List;
import java.util.Map;

import com.chess.engine.Colour;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;

public class Board {

    private final List<Tile> gameBoard;

    private Board(Builder builder){
        this.gameBoard = createGameBoard(builder);
    }

    public Tile getTile(final int tileCoordinate){
        return null;
    }

    private static List<Tile> createGameBoard(Builder builder){
        final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
        for(int i = 0; i < BoardUtils.NUM_TILES; i++){
            tiles[i] = Tile.createTile(i, builder.boardConfig.get(i));
        }
        return ImmutableList.copyOf(tiles);
    }

    public static Board createInitialBoard(){
        return null;
    }

    public static class Builder{

        Map<Integer, Piece> boardConfig;
        Colour nextMoveMaker;

        public Builder(){
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
    }
}
