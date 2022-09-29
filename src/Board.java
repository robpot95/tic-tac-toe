import java.util.ArrayList;

enum BoardState {
    NONE,
    DRAW,
    WINNER,
}

class Board {
    private Tile[][] board;
    private int size = 3;
    private BoardState state;

    public Board() {
        initBoard(3);
    }

    public Board(int size) {
        initBoard(size);
    }

    private void initBoard(int size) {
        // Create board depending on which size they choose
        this.board = new Tile[size][size];
        this.size = size;
        this.state = BoardState.NONE;
        for (int row = 0; row < size; row++){
            for (int col = 0; col < size; col++) {
                board[row][col] = new Tile();
            }
        }
    }

    public ArrayList<Tile> getTiles() {
        // Lets fetch all the tiles and store them into an Array
        ArrayList<Tile> tiles = new ArrayList<Tile>();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                tiles.add(board[row][col]);
            }
        }

        return tiles;
    }

    public ArrayList<Tile> getFreeTiles() {
        // Loop through the tiles and find those which is not occupied and store them into an Array 
        ArrayList<Tile> tiles = new ArrayList<Tile>();
        for (Tile tile : getTiles()) {
            if (tile.getState() == TileState.FREE) {
                tiles.add(tile);
            }
        }

        return tiles;
    }

    public void show() {
        // Show us the board
        for (int i = 0; i < getTiles().size(); i++) {
            if (i != 0 && i % size == 0) {
                System.out.println();
            }

            Entity owner = getTiles().get(i).getOwner();

            if (i % size + 1 == 0) {
                System.out.print(owner != null ? owner.getLetter() : i + 1);
            } else {
                System.out.print((owner != null ? owner.getLetter() : i + 1) + " | ");
            }
        }

        System.out.println("\n");
    }
    
    public BoardState checkState(Entity entity) {
        Boolean win = null;

        // Checking rows
        for (int i = 0; i < size; i++) {
            win = true;
            for (int j = 0; j < size; j++) {
                Entity owner = board[i][j].getOwner();
                if (owner != entity) {
                    win = false;
                    break;
                }
            }

            if (win) {
                state = BoardState.WINNER;
                return state;
            }
        }

        // Checking cols
        for (int i = 0; i < size; i++) {
            win = true;
            for (int j = 0; j < size; j++) {
                Entity owner = board[j][i].getOwner();
                if (owner != entity) {
                    win = false;
                    break;
                }
            }

            if (win) {
                state = BoardState.WINNER;
                return state;
            }
        }

        // Checking diagonals
        win = true;
        for (int i = 0; i < size; i++) {
            Entity owner = board[i][i].getOwner();
            if (owner != entity) {
                win = false;
                break;
            }
        }

        if (win) {
            state = BoardState.WINNER;
            return state;
        }

        win = true;
        for (int i = 0; i < size; i++) {
            Entity owner = board[i][size - 1 - i].getOwner();
            if (owner != entity) {
                win = false;
                break;
            }
        }

        if (win) {
            state = BoardState.WINNER;
            return state;
        }

        // There is no empty tiles - Then it's a draw
        if (getFreeTiles().isEmpty()) {
            state = BoardState.DRAW;
            return state;
        }
        
        return state;
    }

    public void reset() {
        initBoard(size);
    }

    public int getSize() {
        return size * size;
    }

    public BoardState getState() {
        return state;
    }
}