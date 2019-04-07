package student_player;

import java.util.*;

import boardgame.Move;

import pentago_swap.PentagoPlayer;
import pentago_swap.PentagoBoardState;
import pentago_swap.PentagoMove;

/** A player file submitted by a student. */
public class BruteForcePlayer extends PentagoPlayer {

    /**
     * You must modify this constructor to return your student number. This is
     * important, because this is what the code that runs the competition uses to
     * associate you with your agent. The constructor should do nothing else.
     */
    public BruteForcePlayer() {
        super("260797449");
    }

    /**
     * This is the primary method that you need to implement. The ``boardState``
     * object contains the current state of the game, which your agent must use to
     * make decisions.
     * 
     * SUPER BRUTEFORCE basically explore all paths in a dfs style till we see a
     * game where we win
     */
    public Move chooseMove(PentagoBoardState boardState) {
        ArrayList<PentagoMove> moves = boardState.getAllLegalMoves();

        // Return your move to be processed by the server.
        int moveID = best_move(boardState);
        return boardState.getAllLegalMoves().get(moveID);
    }

    /**
     * This is super brute force it does a dfs tree and checks which path is a good
     * path. BruteForce simulator
     */
    public int best_move(PentagoBoardState state) {
        // clone the state
        state = (PentagoBoardState) state.clone();

        // get a list of all moves
        ArrayList<PentagoMove> moves = state.getAllLegalMoves();
        for (int i = 0; i < moves.size(); i++) {
            // play the move
            state.processMove(moves.get(i));

            // check if the game has ended
            if (state.gameOver() && state.getWinner() == state.getTurnPlayer()) {
                return i;
            }

            if (best_move(state) != -1)
                return i;
        }

        return -1;
    }
}