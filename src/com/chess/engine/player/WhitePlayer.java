package com.chess.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Colour;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;
import com.chess.engine.pieces.Piece.PieceType;
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
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals, 
                                                    final Collection<Move> opponentLegals) {
        
        final List<Move> kingCastles = new ArrayList<>();

        // checking if king can castle
        if(this.playerKing.isFirstMove() && !this.isInCheck()){
            // white's king side caslte
            if(!this.board.getTile(61).isTileOccupied() && !this.board.getTile(62).isTileOccupied()){
                final Tile rookTile = this.board.getTile(63);

                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
                    if( Player.clacAttacksOnTile(61, opponentLegals).isEmpty() && 
                        Player.clacAttacksOnTile(62, opponentLegals).isEmpty() && 
                        rookTile.getPiece().getPieceType() == PieceType.ROOK){
                        kingCastles.add(new Move.KingSideCastleMove(this.board, 
                                                                    this.playerKing, 
                                                                    62, 
                                                                    (Rook)rookTile.getPiece(), 
                                                                    rookTile.getTileCoordinate(), 
                                                                    61));
                    }
                }
            }
            if(!this.board.getTile(59).isTileOccupied() && 
               !this.board.getTile(58).isTileOccupied() && 
               !this.board.getTile(57).isTileOccupied()){

                final Tile rookTile = this.board.getTile(56);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove() && 
                Player.clacAttacksOnTile(58, opponentLegals).isEmpty() && 
                Player.clacAttacksOnTile(59, opponentLegals).isEmpty() && 
                rookTile.getPiece().getPieceType() == PieceType.ROOK){
                    kingCastles.add(new Move.KingSideCastleMove(this.board, 
                                                                this.playerKing, 
                                                                58, 
                                                                (Rook)rookTile.getPiece(), 
                                                                rookTile.getTileCoordinate(), 
                                                                59));
                }
            }
        }
        return ImmutableList.copyOf(kingCastles);
    }
}
