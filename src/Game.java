import java.text.NumberFormat;
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
        // Here we write what size of the board we want. Minimum is 2
        int size;
        do {
            System.out.print("Welcome to Tic-Tac-Toe.\nPlease choose board size:\n>> ");
            size = readNumberFromInput().intValue();
            board = new Board(size);
        } while (size < 2);

        // Here we select if we would like to play against CPU or another Player
        int gameType;
        do {
            System.out.print("Choose gameplay:\n1. Player vs Player\n2. Player vs Comp\n>> ");
            gameType = readNumberFromInput().intValue();
            type = gameType == 1 ? GameType.PLAYER_VS_PLAYER : GameType.PLAYER_VS_COMP;
        } while (gameType > GameType.values().length);

        switch (type) {
            case PLAYER_VS_PLAYER:
                System.out.print("Player 1, please write your name:\n>> ");
                entites.add(new Player(userInput.nextLine(), "❌"));
                System.out.print("Player 2, please write your name:\n>> ");
                entites.add(new Player(userInput.nextLine(), "⭕"));
                break;
            case PLAYER_VS_COMP:
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

        // Loop the game until the GameState has been changed
        while (state == GameState.STARTED) {
            if (type == GameType.PLAYER_VS_PLAYER) {
                // When there is Player vs Player we assign second player as COMP. So we use inline condition to check who made the move
                Boolean move = canMove(entites.get(turn == Turn.PLAYER ? 0 : 1).getPlayer());
                if (!move) {
                    continue;
                }
            } else if (type == GameType.PLAYER_VS_COMP) {
                if (turn == Turn.PLAYER) {
                    // Player is first in the array, so we select the player using the index 0

                    Player player = entites.get(0).getPlayer();
                    Boolean move = canMove(player);
                    if (!move) {
                        continue;
                    }

                    if (board.checkState(player) != BoardState.NONE) {
                        announcement(player);
                    }
                } else if (turn == Turn.COMP) {
                    ArrayList<Tile> tiles = board.getFreeTiles();
                    Entity comp = entites.get(1);

                    // Select random free tile
                    comp.makeMove(tiles.get(random.nextInt(tiles.size())));
                    if (board.checkState(comp) != BoardState.NONE) {
                        announcement(comp);
                    }
                }
            }

            turn = turn == Turn.PLAYER ? Turn.COMP : Turn.PLAYER;
        }
    }

    private Boolean canMove(Player player) {
        board.show();

        System.out.print(player.getName() + ", please make your move\n>> ");
        int tileId = readNumberFromInput().intValue();

        // Make sure that user does not write index outside the board
        if (tileId < 0 || tileId > board.getSize()) {
            System.out.println("Sorry but that spot does not exsist.");
            return false;
        }

        // We are returning the value of MakeMove if we were able to move
        return player.makeMove(board.getTiles().get(tileId - 1));
    }

    private void announcement(Entity lastMove) {
        // BoardState has changed to WIN OR DRAW, we shall check using the last "player" who made the move. If it's the person who won or just a draw
        state = GameState.ENDED;
        board.show();

        // Somebody won, add the score to that entity
        if (board.getState() == BoardState.WINNER) {
            System.out.format("The winner of this game is %s\n", lastMove.getPlayer() != null ? lastMove.getPlayer().getName() : "Comp");
            lastMove.addWin();
        } else if (board.getState() == BoardState.DRAW) {
            System.out.println("It was a draw.");
        }

        // Show us how many wins there are
        System.out.println("The scoreboard:");
        for (Entity entity : entites) {
            System.out.format("%s - %d\n", entity.getPlayer() != null ? entity.getPlayer().getName() : "Comp", entity.getWins());
        }

        System.out.print("Would you like to play again? (y/n)\n>> ");
        if (userInput.nextLine().toLowerCase().equals("y")) {
            // Reset the game if user type Y
            board.reset();
            state = GameState.STARTED;
        }
    }

    private Number readNumberFromInput() {
        // We run this function until user write number
        while (true) {
            try {
                return NumberFormat.getInstance().parse(userInput.nextLine());
            } catch (Exception e) {
                System.out.print("Something went wrong, try again.\n>> ");
            }
        }
    }
}
