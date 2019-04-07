package student_player;

import java.util.*;
import pentago_swap.PentagoBoardState;
import pentago_swap.PentagoMove;

public class MonteCarloTreeSearch {
    static final int WIN_SCORE = 10;
    int level;
    int opponent;

    public PentagoMove findNextMove(PentagoBoardState pbs) {
        opponent = pbs.getOpponent();
        Tree tree = new Tree(new Node(new State(pbs, 0, 0.0d)));

        Node promisingNode = selectPromisingNode(tree.getRoot());
        if (!promisingNode.getState().getPbs().gameOver())
            expandNode(promisingNode);

        Node nodeToExplore = promisingNode;
        if (promisingNode.getChildArray().size() > 0)
            nodeToExplore = promisingNode.getRandomChildNode();

        int playoutResult = simulateRandomPlayout(nodeToExplore);
        backPropogation(nodeToExplore, playoutResult);

        Node winnerNode = tree.getRoot().getChildWithMaxScore();
        tree.setRoot(winnerNode);
        return winnerNode.getState().getMove();
    }

    private Node selectPromisingNode(Node rootNode) {
        Node node = rootNode;
        while (node.getChildArray().size() != 0)
            node = UCT.findBestNodeWithUCT(node);
        return node;
    }

    private void expandNode(Node node) {
        List<State> possibleStates = node.getState().getAllPossibleStates();
        possibleStates.forEach(state -> {
            Node newNode = new Node(state);
            newNode.setParent(node);
            node.getChildArray().add(newNode);
        });
    }

    private void backPropogation(Node nodeToExplore, int playerNo) {
        Node tempNode = nodeToExplore;
        while (tempNode != null) {
            tempNode.getState().incrementVisit();
            if (tempNode.getState().getPbs().getTurnPlayer() == playerNo) {
                tempNode.getState().addScore(WIN_SCORE);
            }
            tempNode = tempNode.getParent();
        }
    }

    private int simulateRandomPlayout(Node node) {
        Node tempNode = new Node(node);
        State tempState = tempNode.getState();
        PentagoBoardState pbs = tempNode.getState().getPbs();

        while (!tempState.terminal()) {
            tempState.randomPlay();
        }

        if (pbs.getWinner() == opponent) {
            tempNode.getParent().getState().setWinScore(Integer.MIN_VALUE);
        }

        return pbs.getWinner();
    }
}
