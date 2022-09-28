public class Player extends Entity {
    private final String name;

    public Player(String name, String letter) {
        super(letter);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
