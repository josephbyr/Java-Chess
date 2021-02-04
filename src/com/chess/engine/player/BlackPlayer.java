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

public class BlackPlayer extends Player{

    public BlackPlayer(final Board board, final Collection<Move> whiteStandardLegalMoves,
            final Collection<Move> blackStandardLegalMoves) {
        
        super(board, blackStandardLegalMoves, whiteStandardLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces(){
        return this.board.getBlackPieces();
    }

    @Override
    public Colour getColour() {
        return Colour.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.board.whitePlayer();
    }

    @Override
    protected Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentLegals) {
        final List<Move> kingCastles = new ArrayList<>();

        // checking if king can castle
        if(this.playerKing.isFirstMove() && !this.isInCheck()){
            // black's king side caslte
            if(!this.board.getTile(5).isTileOccupied() && !this.board.getTile(6).isTileOccupied()){
                final Tile rookTile = this.board.getTile(7);

                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
                    if( Player.clacAttacksOnTile(5, opponentLegals).isEmpty() && 
                        Player.clacAttacksOnTile(6, opponentLegals).isEmpty() && 
                        rookTile.getPiece().getPieceType() == Piece.PieceType.ROOK){
                        // TODO add castle move
                        kingCastles.add(null);
                    }
                }
            }
            if(!this.board.getTile(1).isTileOccupied() && 
               !this.board.getTile(2).isTileOccupied() && 
               !this.board.getTile(3).isTileOccupied()){

                final Tile rookTile = this.board.getTile(0);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
                    // TODO add castle move
                    kingCastles.add(null);
                }
            }
        }
        
        return ImmutableList.copyOf(kingCastles);
    }
    
}
