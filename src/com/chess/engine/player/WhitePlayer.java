package com.chess.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Colour;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;

public class WhitePlayer extends Player{

    public WhitePlayer(final Board board, final Collection<Move> whiteStandardLegalMoves, final Collection<Move> blackStandardLegalMoves) {
        super(board, whiteStandardLegalMoves, blackStandardLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces(){
        return this.board.getWhitePieces();
    }

    @Override
    public Colour getColour() {
        return Colour.WHITE;
    }

    @Override
    public Player getOpponent() {
        return this.board.blackPlayer();
    }

    @Override
    protected Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentLegals) {
        
        final List<Move> kingCastles = new ArrayList<>();

        // checking if king can castle
        if(this.playerKing.isFirstMove() && !this.isInCheck()){
            // white's king side caslte
            if(!this.board.getTile(61).isTileOccupied() && !this.board.getTile(62).isTileOccupied()){
                final Tile rookTile = this.board.getTile(63);

                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
                    if( Player.clacAttacksOnTile(61, opponentLegals).isEmpty() && 
                        Player.clacAttacksOnTile(62, opponentLegals).isEmpty() && 
                        rookTile.getPiece().getPieceType() == Piece.PieceType.ROOK){
                        // TODO add castle move
                        kingCastles.add(null);
                    }
                }
            }
            if(!this.board.getTile(59).isTileOccupied() && 
               !this.board.getTile(58).isTileOccupied() && 
               !this.board.getTile(57).isTileOccupied()){

                final Tile rookTile = this.board.getTile(56);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
                    // TODO add castle move
                    kingCastles.add(null);
                }
            }
        }
        
        return ImmutableList.copyOf(kingCastles);
    }
}
