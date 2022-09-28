enum TileState {
    FREE,
    OCCUPIED
}

public class Tile {
    private TileState state;
    private String value;

    public Tile() {
        state = TileState.FREE;
        value = "";
    }

    public Tile(TileState state, String value) {
        this.state = state;
        this.value = value;
    }

    public TileState getState() {
        return state;
    }

    public String getValue() {
        return value;
    }

    public String getValueOrDefault() {
        return value.isEmpty() ? "⬜" : value;
    }
}
