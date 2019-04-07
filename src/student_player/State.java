package student_player;

import java.util.*;
import pentago_swap.PentagoBoardState;
import pentago_swap.PentagoMove;

public class State implements Cloneable {
    PentagoBoardState pbs;
    int visitCount;
    double winScore;
    PentagoMove move;

    public PentagoMove getMove() {
        return this.move;
    }

    public void setMove(PentagoMove move) {
        this.move = move;
    }

    public State(PentagoBoardState pbs, int visitCount, double winScore, PentagoMove m) {
        this.pbs = pbs;
        this.visitCount = visitCount;
        this.winScore = winScore;
        this.move = m;
    }

    public void incrementVisit() {
        this.visitCount++;
    }

    public void addScore(double score) {
        this.winScore += score;
    }

    public State(State s) {
        this.pbs = (PentagoBoardState) s.getPbs().clone();
        this.visitCount = s.getVisitCount();
        this.winScore = s.getWinScore();
    }

    /**
     * Generate all possible states from the current state
     */
    public List<State> getAllPossibleStates() {
        // constructs a list of all possible states from current state
        List<PentagoMove> moves = pbs.getAllLegalMoves();
        List<State> states = new ArrayList<>();
        for (PentagoMove m : moves) {
            PentagoBoardState clonedPbs = (PentagoBoardState) pbs.clone();
            clonedPbs.processMove(m);
            states.add(new State(clonedPbs, 0, 0.0d, m)); // TODO figure out how to initialize
        }

        // return the total states
        return states;
    }

    /**
     * Play a random move
     */
    public void randomPlay() {
        pbs.processMove((PentagoMove) pbs.getRandomMove());
    }

    public PentagoBoardState getPbs() {
        return this.pbs;
    }

    public void setPbs(PentagoBoardState pbs) {
        this.pbs = pbs;
    }

    public int getVisitCount() {
        return this.visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    public double getWinScore() {
        return this.winScore;
    }

    public void setWinScore(double winScore) {
        this.winScore = winScore;
    }

    @Override
    public Object clone() {
        return new State(this);
    }

    public boolean terminal() {
        return this.getPbs().gameOver();
    }
}