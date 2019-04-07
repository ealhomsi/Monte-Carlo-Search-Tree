package student_player;

import java.util.*;

public class Node {
    State state;
    Node parent;
    List<Node> childArray;

    public Node() {

    };

    public Node(Node n) {
        this.state = (State) n.getState().clone();
        this.parent = n.getParent();
        this.childArray = n.getChildArray();
    }

    public Node(State state) {
        this.state = state;
    }

    public Node(State state, Node parent, List<Node> childArray) {
        this.state = state;
        this.parent = parent;
        this.childArray = childArray;
    }

    public Node getRandomChildNode() {
        if (childArray == null)
            throw new RuntimeException("The child array is null");

        Random rand = new Random();
        return childArray.get(rand.nextInt(childArray.size()));
    }

    public Node getParent() {
        return this.parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildArray() {
        return this.childArray;
    }

    public void setChildArray(List<Node> childArray) {
        this.childArray = childArray;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean leaf() {
        return state.getPbs().gameOver();
    }

    public Node getChildWithMaxScore() {
        if (this.getChildArray() == null || this.getChildArray().size() == 0) {
            return null;
        }

        int maxI = 0;
        for (int i = 1; i < this.getChildArray().size(); i++) {
            if (this.getChildArray().get(i).getState().getWinScore() > this.getChildArray().get(maxI).getState()
                    .getWinScore())
                maxI = i;
        }

        return this.getChildArray().get(maxI);
    }
}