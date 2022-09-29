import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    enum GameState {
        INIT,
        STARTED,
        ENDED,
    }
    enum GameType {
        PLAYER_VS_PLAYER,
        PLAYER_VS_COMP,
    }
    enum Turn {
        PLAYER,
        COMP,
    }

    private GameState state = GameState.INIT;
    private GameType type;
    private Turn turn;

    private Board board;
    private final ArrayList<Entity> entites = new ArrayList<Entity>();
    private final static Random random = new Random();
    private final static Scanner userInput = new Scanner(System.in);

    public Game() {
        try {
            int size;
            do {
                System.out.print("Welcome to Tic-Tac-Toe.\nPlease choose board size:\n>> ");
                size = userInput.nextInt();
                board = new Board(size);
            } while (size < 2);
        } catch (Exception e) {
            System.out.println("Invalid board size: " + e.getMessage() + ". A default board size of 3 has been created.");
            board = new Board();
            userInput.nextLine();
        }

        try {
            int gameType;
            do {
                System.out.print("Choose gameplay:\n1. Player vs Player\n2. Player vs Comp\n>> ");
                gameType = userInput.nextInt();
                type = gameType == 1 ? GameType.PLAYER_VS_PLAYER : GameType.PLAYER_VS_COMP;
            } while (gameType > GameType.values().length);
        } catch (Exception e) {
            System.out.println("Invalid gameplay: " + e.getMessage() + ". Option 2 has been selected as a default.");
            type = GameType.PLAYER_VS_COMP;
        }

        switch (type) {
            case PLAYER_VS_PLAYER:
                userInput.nextLine();
                System.out.print("Player 1, please write your name:\n>> ");
                entites.add(new Player(userInput.nextLine(), "❌"));
                System.out.print("Player 2, please write your name:\n>> ");
                entites.add(new Player(userInput.nextLine(), "⭕"));
                break;
            case PLAYER_VS_COMP:
                userInput.nextLine();
                System.out.print("Please write your name:\n>> ");
                entites.add(new Player(userInput.nextLine(), "❌"));
                entites.add(new Comp("⭕"));
                break;
            default:
                break;
        }
        
        state = GameState.STARTED;
        GameLoop();
    }

    private void GameLoop() {
        // Randomize who starts
        turn = Turn.values()[random.nextInt(Turn.values().length)];
        while (state == GameState.STARTED) {
            if (type == GameType.PLAYER_VS_PLAYER) {
                Boolean isMoved = makeMove(entites.get(turn == Turn.PLAYER ? 0 : 1).getPlayer());
                if (!isMoved) {
                    continue;
                }
            } else if (type == GameType.PLAYER_VS_COMP) {
                if (turn == Turn.PLAYER) {
                    Boolean isMoved = makeMove(entites.get(0).getPlayer());
                    if (!isMoved) {
                        continue;
                    }
                } else if (turn == Turn.COMP) {
                    ArrayList<Tile> tiles = board.getFreeTiles();
                    Entity comp = entites.get(1);
                    tiles.get(random.nextInt(tiles.size())).markTile(comp);
                    if (board.checkState(comp) != BoardState.NONE) {
                        announcement(comp);
                    }
                }
            }

            turn = turn == Turn.PLAYER ? Turn.COMP : Turn.PLAYER;
        }
    }

    private Boolean makeMove(Player player) {
        board.show();

        int tileId = -1;
        try {
            System.out.print(player.getName() + ", please make your move\n>> ");
            tileId = userInput.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid move: " + e.getMessage());
            userInput.nextLine();
        }

        if (tileId == -1 || tileId < 0 || tileId > board.getSize()) {
            System.out.println("Sorry but that spot does not exsist.");
            return false;
        }

        Tile tile = board.getTiles().get(tileId - 1);
        if (tile.getState() == TileState.OCCUPIED) {
            System.out.println("Sorry but that spot is already taken.");
            return false;
        }

        tile.markTile(player);
        if (board.checkState(player) != BoardState.NONE) {
            announcement(player);
        }
        return true;
    }

    private void announcement(Entity lastMove) {
        state = GameState.ENDED;

        userInput.nextLine();
        board.show();
        if (board.getState() == BoardState.WINNER) {
            System.out.format("The winner of this game is %s\n", lastMove.getPlayer() != null ? lastMove.getPlayer().getName() : "Comp");
            lastMove.addWin();
        } else if (board.getState() == BoardState.DRAW) {
            System.out.println("It was a draw.");
        }

        System.out.println("The scoreboard:");
        for (Entity entity : entites) {
            System.out.format("%s - %d\n", entity.getPlayer() != null ? entity.getPlayer().getName() : "Comp", entity.getWins());
        }

        System.out.print("Would you like to play again? (y/n)\n>> ");
        if (userInput.nextLine().toLowerCase().equals("y")) {
            board.reset();
            state = GameState.STARTED;
        }
    }
}
