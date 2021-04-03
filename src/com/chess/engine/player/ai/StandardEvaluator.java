package com.chess.engine.player.ai;

import com.chess.engine.board.Board;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.Player;

public class StandardEvaluator implements Evaluator{

    @Override
    public int evaluate(final Board board, final int depth) {
        return evaluatePlayer(board, board.whitePlayer(), depth) - 
               evaluatePlayer(board, board.blackPlayer(), depth);
    }

    private int evaluatePlayer(final Board board, final Player player, final int depth) {
        return pieceValue(player);
    }

    private static int pieceValue(final Player player) {
        int pieceValueScore = 0;
        for(final Piece piece : player.getActivePieces()){
            pieceValueScore += piece.getPieceValue();
        }
        return pieceValueScore;
    }
}