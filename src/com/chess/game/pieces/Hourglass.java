// Author: Ho Teck Fung

package com.chess.game.pieces;

import com.chess.game.board.Board;
import com.chess.game.player.Player;

public class Hourglass extends Piece {

    // Constructor
    public Hourglass(Player owner, int curR, int curC, boolean opposite) {
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
        // If the piece is trying to move vertically, horizontally, diagonally, or more
        // than 2 squares in any direction, the move is invalid.
        if (distanceC == 0 || distanceR == 0 || distanceC > 2 || distanceR > 2
                || Math.abs(distanceR) == Math.abs(distanceC)) {
            return false;
        }

        // If the piece is trying to move to a square occupied by a piece of the same
        // color, or to a non-empty square that is not 2 squares away, the move is
        // invalid.
        if (isSameColorOccupied(newR, newC) || (distanceR != 2 && distanceC != 2 && isNonEmptySquare(newR, newC))) {
            return false;
        }

        return true;
    }

    // Helper method to check if the destination is occupied by a piece of the same
    // color.
    private boolean isSameColorOccupied(int newR, int newC) {
        return Board.board[curR][curC].charAt(2) == Board.board[newR][newC].charAt(2);
    }

    // Helper method to check if the destination is a non-empty square that is not 2
    // squares away.
    private boolean isNonEmptySquare(int newR, int newC) {
        return Board.board[newR][newC].charAt(2) != ' ';
    }

    // Helper method to update the board after a valid move.
    private void updateBoard(int newR, int newC) {
        Board.board[newR][newC] = Board.board[curR][curC];
        Board.board[curR][curC] = "   ";
    }
}
