
package TicTacToe_LLD;

import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Scanner;

public class Game {

    private Board board;
    private Deque<Player> players;
    private Rule rule;
    private Scanner scanner;

    // Constructor
    public Game(Player player1, Player player2) {

        board = new Board();

        players = new ArrayDeque<>();

        players.addLast(player1);
        players.addLast(player2);

        rule = new standardRules();

        scanner = new Scanner(System.in);
    }

    // Get current player
    private Player getCurrentPlayer() {
        return players.pollFirst();
    }

    // Switch player turn
    private void switchTurn(Player currentPlayer) {
        players.addLast(currentPlayer);
    }

    // Start the game
    public void startGame() {

        System.out.println("Welcome to Tic Tac Toe!");

        board.display();

        while (true) {

            // Get current player
            Player currentPlayer = getCurrentPlayer();

            System.out.println("\n" + currentPlayer.getName()
                    + " (" + currentPlayer.getsymbol() + "), enter row and column:");
            System.out.println();
            
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            // Validate move
            if (!rule.isValidMove(row, col, board)) {

                System.out.println("Invalid move! Try again.");

                players.addFirst(currentPlayer);
                continue;
            }

            // Place player's symbol
            board.markCell(currentPlayer.getsymbol(), row, col);

            // Display updated board
            board.display();

            // Check winner
            if (rule.checkWin(row, col, board, currentPlayer.getsymbol())) {

                System.out.println(currentPlayer.getName() + " wins!");

                break;
            }

            // Check draw
            if (rule.isDraw(board)) {

                System.out.println("The game is a draw!");

                break;
            }

            // Switch turn
            switchTurn(currentPlayer);
        }

        System.out.println("Game Over!");
    }
    
    public static void main(String[] args) {

        Player player1 = new Player(1, "Eren", Symbol.X);
        Player player2 = new Player(2, "Mikasa", Symbol.O);

        Game game = new Game(player1, player2);

        game.startGame();
    }
}
