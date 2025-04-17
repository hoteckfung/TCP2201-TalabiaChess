// Author: Teng Wei Joe

public class Player {

    private final char color; // The color of the player's pieces
    private boolean turn; // Whether it's this player's turn
    private int moveCount; // The number of moves this player has made
    private boolean win; // Whether this player has won

    // Constructor
    public Player(char color, boolean turn) {
        this.color = color; // Set the color of the player's pieces
        this.turn = turn; // Set whether it's this player's turn
        moveCount = 0; // Initialize the move count to 0
        win = false; // Initialize the win status to false
    }

    // Getter for color
    public char getColor() {
        return color; // Return the color of the player's pieces
    }

    // Getter for turn
    public boolean getTurn() {
        return turn; // Return whether it's this player's turn
    }

    // Setter for turn
    public void setTurn(boolean turn) {
        this.turn = turn; // Set whether it's this player's turn
    }

    // Getter for moveCount
    public int getMoveCount() {
        return moveCount; // Return the number of moves this player has made
    }

    // Setter for moveCount
    public void setMoveCount(int moveCount) {
        this.moveCount = moveCount; // Set the number of moves this player has made
    }

    // Getter for win
    public boolean getWin() {
        return win; // Return whether this player has won
    }

    // Setter for win
    public void setWin(boolean win) {
        this.win = win; // Set whether this player has won
    }

}
