// Author: Tan Teng Hui

public class Rule {

    public static final int BOARD_LENGTH = 7;
    public static final int BOARD_HEIGHT = 6;

    public static Player playerY;
    public static Player playerB;
    public static Piece selectedTilePiece;

    // Constructor
    // playerY's turn is set to true because playerY will always starts first when a
    // new game is created
    public Rule() {
        playerY = new Player('Y', true);
        playerB = new Player('B', false);
        selectedTilePiece = null;
    }

    // Swapping the Times and Pluses
    public static void swapPiece() {
        for (int r = 0; r < BOARD_HEIGHT; r++) {
            for (int c = 0; c < BOARD_LENGTH; c++) { // loop through the board
                if (playerY.getMoveCount() % 2 == 0 && playerY.getTurn()) { // if playerY moved 2 turns
                    if (Board.board[r][c].equals("TiY")) { // if the tile is equals to Yellow Time
                        Board.board[r][c] = "PlY"; // change the tile to Yellow Plus
                    } else if (Board.board[r][c].equals("PlY")) { // if the tile is equals to Yellow Plus
                        Board.board[r][c] = "TiY"; // change the tile to Yellow Time
                    }
                } else if (playerB.getMoveCount() % 2 == 0 && playerB.getTurn()) { // if playerB moved 2 turns
                    if (Board.board[r][c].equals("TiB")) { // if the tile is equals to Blue Time
                        Board.board[r][c] = "PlB"; // change the tile to Blue Plus
                    } else if (Board.board[r][c].equals("PlB")) { // if the tile is equals to Blue Plus
                        Board.board[r][c] = "TiB"; // change the tile to Blue Time
                    }
                }
            }
        }
    }

    // Checking if the game is over
    public static boolean checkEndGame() {
        boolean sunYExist = false;
        boolean sunBExist = false;
        for (int r = 0; r < BOARD_HEIGHT; r++) {
            for (int c = 0; c < BOARD_LENGTH; c++) {
                if (Board.board[r][c].equals("SuB")) // if the tile is equals to Blue Sun
                    sunBExist = true; // set Blue Sun to exist
                if (Board.board[r][c].equals("SuY")) // if the tile is equals to Yellow Sun
                    sunYExist = true; // set Yellow Sun to exist
            }
        }
        if (!sunYExist) { // if Yellow Sun doesn't exist
            System.out.println("Game over. Blue player win.");
            playerY.setTurn(false); // not playerY's turn
            playerB.setTurn(false); // not playerB's turn
            playerB.setWin(true); // playerB wins
            return true; // return checkEndGame() to true
        } else if (!sunBExist) { // if Blue Sun doesn't exist
            System.out.println("Game over. Yellow player win.");
            playerY.setTurn(false); // not playerY's turn
            playerB.setTurn(false); // not playerB's turn
            playerY.setWin(true); // playerY wins
            return true; // return checkEndGame() to true
        }
        return false; // return checkEndGame() to false
    }

    // getter for playerY & playerB
    public static Player getPlayerY() {
        return playerY;
    }

    public static Player getPlayerB() {
        return playerB;
    }

    // Changing the player's turn
    public static void changePlayerTurn() {
        if (Rule.playerY.getTurn()) { // if playerY's turn is over
            Rule.playerY.setTurn(false); // not playerY's turn
            Rule.playerB.setTurn(true); // set to playerB's turn
        } else if (Rule.playerB.getTurn()) { // if playerB's turn is over
            Rule.playerY.setTurn(true); // set to playerY's turn
            Rule.playerB.setTurn(false); // not playerB's turn
        }
    }
}
