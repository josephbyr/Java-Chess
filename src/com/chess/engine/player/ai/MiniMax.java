package com.chess.engine.player.ai;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.player.MoveTransition;

public class MiniMax {

    private final Evaluator evaluator;
    private final int depth;

    public MiniMax(final int depth) {
        this.evaluator = new Evaluator();
        this.depth = depth;
    }

    @Override
    public String toString() {
        return "MiniMax";
    }

    public Move execute(Board board) {
        final long startTime = System.currentTimeMillis();
        Move topMove = null;
        int highestVal = Integer.MIN_VALUE;
        int lowestVal = Integer.MAX_VALUE;
        int currVal = 0;

        System.out.println(board.currentPlayer() + " calculating with depth " + depth);

        int numMoves = board.currentPlayer().getLegalMoves().size();

        for (final Move move : board.currentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                currVal = board.currentPlayer().getColour().isWhite()
                        ? min(moveTransition.getTransitionBoard(), depth - 1)
                        : max(moveTransition.getTransitionBoard(), depth - 1);
            }

            if (board.currentPlayer().getColour().isWhite() && currVal >= highestVal) {
                highestVal = currVal;
                topMove = move;
            } else if (board.currentPlayer().getColour().isBlack() && currVal <= lowestVal) {
                lowestVal = currVal;
                topMove = move;
            }
        }

        final long timeTaken = System.currentTimeMillis() - startTime;
        // System.out.println(timeTaken);

        return topMove;
    }

    public int min(final Board board, final int depth) {
        if (depth == 0 || isEndOfGame(board)) {
            return this.evaluator.evaluate(board, depth);
        }
        int lowestVal = Integer.MAX_VALUE;
        for (final Move move : board.currentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                final int currVal = max(moveTransition.getTransitionBoard(), depth - 1);
                if (currVal <= lowestVal) {
                    lowestVal = currVal;
                }
            }
        }
        return lowestVal;
    }

    public int max(final Board board, final int depth) {
        if (depth == 0 || isEndOfGame(board)) {
            return this.evaluator.evaluate(board, depth);
        }
        int highestVal = Integer.MIN_VALUE;
        for (final Move move : board.currentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                final int currVal = min(moveTransition.getTransitionBoard(), depth - 1);
                if (currVal >= highestVal) {
                    highestVal = currVal;
                }
            }
        }
        return highestVal;
    }

    private static boolean isEndOfGame(final Board board) {
        return board.currentPlayer().isInCheckMate() || board.currentPlayer().isInStaleMate();
    }
}
