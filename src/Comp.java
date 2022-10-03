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

    @Override
    public Boolean makeMove(Tile tile) {
        // We are the owner this tile now
        tile.setOwner(this);
        return true;
    }
}
