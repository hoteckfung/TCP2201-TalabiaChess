// Created and wrote by [Tan Teng Hui]
// Model

package board;

public class Board {

    // Define the size of the board as a constant
    public static final int BOARD_LENGTH = 7;
    public static final int BOARD_HEIGHT = 6;

    // Define the initial state of the board
    private static final String[][] INITIAL_BOARD = {
            { "PlB", "HoB", "TiB", "SuB", "TiB", "HoB", "PlB" },
            { "PoB", "PoB", "PoB", "PoB", "PoB", "PoB", "PoB" },
            { "   ", "   ", "   ", "   ", "   ", "   ", "   " },
            { "   ", "   ", "   ", "   ", "   ", "   ", "   " },
            { "PoY", "PoY", "PoY", "PoY", "PoY", "PoY", "PoY" },
            { "PlY", "HoY", "TiY", "SuY", "TiY", "HoY", "PlY" }
    };

    // Initialize the board with the initial state
    public static String[][] board = new String[BOARD_HEIGHT][BOARD_LENGTH];

    static {
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            System.arraycopy(INITIAL_BOARD[i], 0, board[i], 0, BOARD_LENGTH);
        }
    }

    // Flip the board when it's opponent's turn.
    public static void flipBoard() {
        String[][] tempBoard = new String[BOARD_HEIGHT][BOARD_LENGTH];

        // making the tempBoard to be the inverted version of the board, used when
        // flipping the board.
        for (int r = 0; r < BOARD_HEIGHT; r++) { // for rows
            for (int c = 0; c < BOARD_LENGTH; c++) { // for columns
                tempBoard[r][c] = board[BOARD_HEIGHT - 1 - r][BOARD_LENGTH - 1 - c];
            }
        }
        board = tempBoard;
    }
}