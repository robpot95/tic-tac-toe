public abstract class Entity {
    private String letter;

    public Entity(String letter) {
        this.letter = letter;
    }

    public String getLetter() {
        return letter;
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
