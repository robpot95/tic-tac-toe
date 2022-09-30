public abstract class Entity {
    private String letter;
    private int wins;

    public Entity(String letter) {
        this.letter = letter;
        this.wins = 0;
    }

    public abstract Boolean makeMove(Tile tile);

    public String getLetter() {
        return letter;
    }

    public void addWin() {
        wins++;
    }

    public int getWins() {
        return wins;
    }

    public Boolean isEntity() {
        return true;
    }

    public Boolean isPlayer() {
        return false;
    }

    public Boolean isComp() {
        return false;
    }

    public abstract Player getPlayer();
}
