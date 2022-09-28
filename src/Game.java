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

    private GameState state;
    private GameType type;
    private Turn turn;

    private final Board board;
    private final ArrayList<Entity> entites = new ArrayList<Entity>();
    private final static Random random = new Random();
    private final static Scanner userInput = new Scanner(System.in);

    public Game() {
        this.state = GameState.INIT;
        System.out.print("Welcome to Tic-Tac-Toe.\nPlease choose board size:\n>> ");
        board = new Board(userInput.nextInt());
        System.out.print("Choose gameplay:\n1. Player vs Player\n2. Player vs Comp\n>> ");
        type = userInput.nextInt() == 1 ? GameType.PLAYER_VS_PLAYER : GameType.PLAYER_VS_COMP;
        userInput.nextLine();
        if (type == GameType.PLAYER_VS_PLAYER) {
            System.out.print("Player 1, please write your name:\n>> ");
            entites.add(new Player(userInput.nextLine(), "❌"));
            System.out.print("Player 2, please write your name:\n>> ");
            entites.add(new Player(userInput.nextLine(), "⭕"));
        } else if (type == GameType.PLAYER_VS_COMP) {
            System.out.print("Please write your name:\n>> ");
            entites.add(new Player(userInput.nextLine(), "❌"));
            entites.add(new Comp("⭕"));
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
                        state = GameState.ENDED;
                        announcement(comp);
                    }
                }
            }

            turn = turn == Turn.PLAYER ? Turn.COMP : Turn.PLAYER;
        }
    }

    private Boolean makeMove(Player player) {
        board.show();
        System.out.print(player.getName() + ", please make your move\n>> ");
        int tileId = userInput.nextInt();
        if (tileId < 0 || tileId > board.getSize()) {
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
            state = GameState.ENDED;
            announcement(player);
        }
        return true;
    }

    private void announcement(Entity lastMove) {
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
