// Author: Ho Teck Fung

public class Plus extends Piece {

    // Constructor
    public Plus(Player owner, int curR, int curC, boolean opposite) {
        super(owner, curR, curC, opposite);
    }

    // This method attempts to move the piece to a new position (newR, newC).
    @Override
    public boolean movePiece(int newR, int newC) {
        // Calculate the row and column distances between the current and new positions.
        int distanceR = newR - curR;
        int distanceC = newC - curC;

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
        // If the piece is trying to move to a square occupied by a piece of the same
        // color, or trying to move diagonally, the move is invalid.
        if (isSameColorOccupied(newR, newC) || (distanceR != 0 && distanceC != 0)) {
            return false;
        }

        // Calculate the step size for the row and column directions.
        int stepR = distanceR != 0 ? distanceR / Math.abs(distanceR) : 0;
        int stepC = distanceC != 0 ? distanceC / Math.abs(distanceC) : 0;

        // Check all squares along the path of the move. If any square is occupied, the
        // move is invalid.
        for (int i = 1; i < Math.max(Math.abs(distanceR), Math.abs(distanceC)); i++) {
            if (isSquareOccupied(curR + i * stepR, curC + i * stepC)) {
                return false;
            }
        }

        return true;
    }

    // Helper method to check if the destination is occupied by a piece of the same
    // color.
    private boolean isSameColorOccupied(int newR, int newC) {
        return Board.board[curR][curC].charAt(2) == Board.board[newR][newC].charAt(2);
    }

    // Helper method to check if a square is occupied.
    private boolean isSquareOccupied(int row, int col) {
        return Board.board[row][col].charAt(2) != ' ';
    }

    // Helper method to update the board after a valid move.
    private void updateBoard(int newR, int newC) {
        Board.board[newR][newC] = Board.board[curR][curC];
        Board.board[curR][curC] = "   ";
    }
}
