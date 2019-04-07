package student_player;

class MCTS {
    // static variable single_instance of type Singleton
    private static MonteCarloTreeSearch single_instance = null;

    // private constructor restricted to this class itself
    private MCTS() {
    }

    // static method to create instance of Singleton class
    public static MonteCarloTreeSearch getInstance() {
        if (single_instance == null)
            single_instance = new MonteCarloTreeSearch();

        return single_instance;
    }
}