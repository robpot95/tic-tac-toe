import java.util.ArrayList;
import java.util.HashMap;

class Board {
    private HashMap<Integer, Tile> board = new HashMap<Integer, Tile>();
    private int size = 3;

    public Board() {
    }

    public Board(int size) {
        // Create board depending on which size they choose
        this.size = size;
        for (int i = 0; i < size * size; i++) {
            board.put(i, new Tile());
        }
    }
 
    public void show() {
        // Loop through the board and show the values
        for (int i = 0; i < board.size(); i++) {
            // When we get 0 then we add new line to break the board up
            if (i % size == 0) {
                System.out.println("");
            }
            System.out.print(board.get(i).getValueOrDefault());
        }
    }

    public ArrayList<Tile> getFreeTiles() {
        // Loop through the tiles and find a tile which is not occupied at the moment
        ArrayList<Tile> tiles = new ArrayList<Tile>();
        for (Tile tile : board.values()) {
            if (tile.getState() == TileState.FREE) {
                tiles.add(tile);
            }
        }   


        return tiles;
    }
}