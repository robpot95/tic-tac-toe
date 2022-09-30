public class Player extends Entity {
    private final String name;

    public Player(String name, String letter) {
        super(letter);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Boolean isPlayer() {
        return true;
    }

    @Override
    public Boolean makeMove(Tile tile) {
        // Tile is occupied, we failed to make the move
        if (tile.getState() == TileState.OCCUPIED) {
            System.out.println("Sorry but that spot is already taken.");
            return false;
        }

        // We are the owner of this tile
        tile.setOwner(this);
        return true;
    }

    @Override
    public Player getPlayer() {
        return this;
    }
}
