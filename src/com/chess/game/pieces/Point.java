// Author: Ho Teck Fung

package com.chess.game.pieces;

import com.chess.game.board.Board;
import com.chess.game.player.Player;

public class Point extends Piece {

    // Constructor
    public Point(Player owner, int curR, int curC, boolean opposite) {
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

        // If the move is valid, update the board and handle special cases.
        updateBoard(newR, newC);

        return true; // The move was successful.
    }

    // Helper method to check if the move is valid.
    private boolean isValidMove(int distanceR, int distanceC, int newR, int newC) {
        if (distanceC != 0 || Math.abs(distanceR) > 2 ||
                isSameColorOccupied(newR, newC) ||
                (!opposite && distanceR > 0) ||
                (opposite && distanceR < 0) ||
                (Math.abs(distanceR) == 2 && isPieceInMiddleRow(newR))) {
            return false;
        }
        return true;
    }

    // Helper method to check if the destination is occupied by a piece of the same
    // color.
    private boolean isSameColorOccupied(int newR, int newC) {
        return Board.board[curR][curC].charAt(2) == Board.board[newR][newC].charAt(2);
    }

    // Helper method to check if a piece is in the middle row when moving two
    // squares.
    private boolean isPieceInMiddleRow(int newR) {
        return Board.board[curR + (newR - curR) / 2][curC].charAt(2) != ' ';
    }

    // Helper method to update the board after a valid move.
    private void updateBoard(int newR, int newC) {
        Board.board[newR][newC] = Board.board[curR][curC];
        Board.board[curR][curC] = "   ";

        // Handle piece direction change when reaching the end of the board.
        if (newR == 0 || newR == 5) {
            changeOpposite();
        }
    }
}
