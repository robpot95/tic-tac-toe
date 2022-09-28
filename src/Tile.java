enum TileState {
    FREE,
    OCCUPIED
}

public class Tile {
    private TileState state;
    private Entity owner;

    public Tile() {
        state = TileState.FREE;
    }

    public void markTile(Entity entity) {
        this.state = TileState.OCCUPIED;
        this.owner = entity;
    }

    public TileState getState() {
        return state;
    }

    public Entity getOwner() {
        return owner;
    }
}
