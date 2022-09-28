import java.util.ArrayList;

class Board {
    private Tile[][] board;
    private int size = 3;

    public Board() {
        new Board(3);
    }

    public Board(int size) {
        // Create board depending on which size they choose
        this.board = new Tile[size][size];
        this.size = size;
        for (int row = 0; row < size; row++){
            for (int col = 0; col < size; col++) {
                board[row][col] = new Tile();
            }
        }
    }

    public ArrayList<Tile> getTiles() {
        ArrayList<Tile> tiles = new ArrayList<Tile>();
        for (int col = 0; col < size; col++) {
            for (int row = 0; row < size; row++) {
                tiles.add(board[col][row]);
            }
        }

        return tiles;
    }

    public ArrayList<Tile> getFreeTiles() {
        ArrayList<Tile> tiles = new ArrayList<Tile>();
        for (Tile tile : getTiles()) {
            if (tile.getState() == TileState.FREE) {
                tiles.add(tile);
            }
        }

        return tiles;
    }

    public void show() {
        for (int i = 0; i < getTiles().size(); i++) {
            if (i != 0 && i % size == 0) {
                System.out.println();
            }

            Entity owner = getTiles().get(i).getOwner();
            System.out.print(owner != null ? owner.getLetter() : "⬜");
        }

        System.out.println();
    }

    public int getSize() {
        return size * size;
    }
}