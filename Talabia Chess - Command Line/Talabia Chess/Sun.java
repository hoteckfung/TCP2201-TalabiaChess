// Author: Ho Teck Fung

public class Sun extends Piece {

    // Constructor
    public Sun(Player owner, int curR, int curC, boolean opposite) {
        super(owner, curR, curC, opposite);
    }

    // This method attempts to move the piece to a new position (newR, newC).
    @Override
    public boolean movePiece(int newR, int newC) {
        // Calculate the absolute row and column distances between the current and new
        // positions.
        int distanceR = Math.abs(newR - curR);
        int distanceC = Math.abs(newC - curC);

        // Check for invalid moves using descriptive conditions for better readability.
        if (!isValidMove(distanceR, distanceC, newR, newC)) {
            return false;
        }

        // If the move is valid, update the board.
        updateBoard(newR, newC);

        return true; // The move was successful.
    }

    // Helper method to check if the move is valid.
    private boolean isValidMove(int distanceR, int distanceC, int newR, int newC) {
        // Check if the destination position is not occupied by a piece of the same
        // color and the piece is moving only one space vertically or horizontally.
        return Board.board[curR][curC].charAt(2) != Board.board[newR][newC].charAt(2) && distanceR <= 1
                && distanceC <= 1;
    }

    // Helper method to update the board after a valid move.
    private void updateBoard(int newR, int newC) {
        Board.board[newR][newC] = Board.board[curR][curC];
        Board.board[curR][curC] = "   ";
    }
}
