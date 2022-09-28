public class Comp extends Entity {
    public Comp(String letter) {
        super(letter);
    }
    
    public Boolean isComp() {
        return true;
    }

    @Override
    public Player getPlayer() {
        return null;
    }
}
