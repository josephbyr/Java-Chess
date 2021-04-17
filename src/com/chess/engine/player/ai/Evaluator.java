package com.chess.engine.player.ai;

import com.chess.engine.board.Board;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.Player;

public class Evaluator {

    private static final int CHECK_POINTS = 50;
    private static final int CHECK_MATE_POINTS = Integer.MAX_VALUE;
    private static final int DEPTH_POINTS = 100;
    private static final int CASTLED_POINTS = 60;

    public int evaluate(final Board board, final int depth) {
        return evaluatePlayer(board, board.whitePlayer(), depth) - evaluatePlayer(board, board.blackPlayer(), depth);
    }

    private int evaluatePlayer(final Board board, final Player player, final int depth) {
        return pieceValue(player) + mobility(player) + check(player) + checkmate(player, depth) + castled(player);
    }

    private static int mobility(final Player player) {
        return player.getLegalMoves().size();
    }

    private static int check(final Player player) {
        return player.getOpponent().isInCheck() ? CHECK_POINTS : 0;
    }

    private static int checkmate(final Player player, int depth) {
        return player.getOpponent().isInCheckMate() ? CHECK_MATE_POINTS * depthBonus(depth) : 0;
    }

    private static int depthBonus(int depth) {
        return depth == 0 ? 1 : DEPTH_POINTS * depth;
    }

    private static int castled(final Player player) {
        return player.isCastled() ? CASTLED_POINTS : 0;
    }

    private static int pieceValue(final Player player) {
        int pieceValueScore = 0;
        for (final Piece piece : player.getActivePieces()) {
            pieceValueScore += piece.getPieceValue();
        }
        return pieceValueScore;
    }
}