package com.chess.engine.player.ai;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.player.MoveTransition;

public class MiniMax implements MoveStrategy {

    private final Evaluator evaluator;

    public MiniMax() {
        this.evaluator = null;
    }

    @Override
    public String toString() {
        return "MiniMax";
    }

    @Override
    public Move execute(Board board, int depth) {
        final long startTime = System.currentTimeMillis();
        Move topMove = null;
        int highestVal = Integer.MIN_VALUE;
        int lowestVal = Integer.MAX_VALUE;
        int currVal = 0;

        System.out.println(board.currentPlayer() + "calculating with depth" + depth);

        int numMoves = board.currentPlayer().getLegalMoves().size();

        for(final Move move : board.currentPlayer().getLegalMoves()){
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if(moveTransition.getMoveStatus().isDone()){
                currVal = board.currentPlayer().getColour().isWhite() ?
                    min(moveTransition.getTransitionBoard(), depth - 1) : 
                    max(moveTransition.getTransitionBoard(), depth - 1);
            }

            if(board.currentPlayer().getColour().isWhite() && currVal >= highestVal){
                highestVal = currVal;
                topMove = move;
            }
            else if(board.currentPlayer().getColour().isBlack() && currVal <= lowestVal){
                lowestVal = currVal;
                topMove = move;
            }
        }

        final long timeTaken = System.currentTimeMillis() - startTime;

        return topMove;
    }

    public int min(final Board board, final int depth) {
        if (depth == 0) {
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
        if (depth == 0) {
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
}
