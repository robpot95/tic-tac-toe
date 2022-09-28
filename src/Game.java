import java.util.Random;

public class Game {
    enum Turn {
        PLAYER,
        COMP,
    }

    private Turn turn;
    private final static Random random = new Random();

    public Game() {
        // Randomize who starts
        turn = Turn.values()[random.nextInt(Turn.values().length)];
    }
}
