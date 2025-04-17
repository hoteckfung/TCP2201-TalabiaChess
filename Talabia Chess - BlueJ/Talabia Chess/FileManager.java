// Author: Tan Teng Hui

import java.util.*;
import java.io.*;

public class FileManager {

    public static final int BOARD_LENGTH = 7;
    public static final int BOARD_HEIGHT = 6;

    // Creates a new game
    public static void newGame() {
        Board.board = new String[][] {
                { "PlB", "HoB", "TiB", "SuB", "TiB", "HoB", "PlB" },
                { "PoB", "PoB", "PoB", "PoB", "PoB", "PoB", "PoB" },
                { "   ", "   ", "   ", "   ", "   ", "   ", "   " },
                { "   ", "   ", "   ", "   ", "   ", "   ", "   " },
                { "PoY", "PoY", "PoY", "PoY", "PoY", "PoY", "PoY" },
                { "PlY", "HoY", "TiY", "SuY", "TiY", "HoY", "PlY" }
        };
        System.out.println("Created New Game.");
    }

    // Saves the current playing game
    public static void saveGame() {
        File file = new File("saveFile.txt"); // path name for the save file of the game in .txt form
        PrintWriter writer; // used to print the formatted representation of objects to the text-output
                            // stream.
        try {
            writer = new PrintWriter(file); // set file path to saveFile.txt
            writer.println(Rule.playerY.getMoveCount()); // save playerY's move count
            writer.println(Rule.playerY.getTurn()); // save playerY's current turn
            writer.println(Rule.playerB.getMoveCount()); // save playerB's move count
            writer.println(Rule.playerB.getTurn()); // save playerB's current turn
            for (int r = 0; r < BOARD_HEIGHT; r++) { // prints for 2D array
                for (int c = 0; c < BOARD_LENGTH; c++) {
                    if (Board.board[r][c].equals("   ")) // if current position is an empty tile
                        writer.print(" 0 " + " ");
                    else
                        writer.print(Board.board[r][c] + " ");
                }
                writer.print("\n");
            }
            for (int r = 0; r < BOARD_HEIGHT; r++) { // prints for GUI tile array
                for (int c = 0; c < BOARD_LENGTH; c++) {
                    if (GUI.getPieceAtPosition(r, c) == null) {
                        writer.print("0" + " ");
                    } else {
                        writer.print(GUI.isPieceOpposite(r, c) + " ");
                    }
                }
                writer.print("\n");
            }
            writer.close();
            System.out.println("Successfully save game.");
        } catch (FileNotFoundException e) { // catch any error when handling saveGame()
            System.out.println("Error occur while saving game to file.");
        }
    }

    // Loads the game
    public static boolean[][] loadGame() {
        File file = new File("saveFile.txt");
        boolean[][] pieceOppositeList = new boolean[BOARD_HEIGHT][BOARD_LENGTH];
        Scanner fileReader; // used to read the .txt file
        try {
            fileReader = new Scanner(file); // set file path to saveFile.txt
            Rule.playerY.setMoveCount(fileReader.nextInt()); // load playerY's move count
            Rule.playerY.setTurn(fileReader.next().equals("true")); // load playerY's current turn
            Rule.playerB.setMoveCount(fileReader.nextInt()); // load playerB's move count
            Rule.playerB.setTurn(fileReader.next().equals("true")); // load playerB's current turn
            for (int r = 0; r < BOARD_HEIGHT; r++) {
                for (int c = 0; c < BOARD_LENGTH; c++) {
                    String chessName = fileReader.next();
                    if (chessName.equals("0")) { // if it's '0'
                        Board.board[r][c] = "   "; // set current position to an empty tile
                    } else {
                        Board.board[r][c] = chessName; // set current position to the exact piece
                    }
                }
            }
            // load GUI tile array
            for (int r = 0; r < BOARD_HEIGHT; r++) {
                for (int c = 0; c < BOARD_LENGTH; c++) {
                    String tmpStr = fileReader.next();
                    if (tmpStr.equals("true")) {
                        pieceOppositeList[r][c] = true;
                    } else if (tmpStr.equals("false")) {
                        pieceOppositeList[r][c] = false;
                    } else {
                        pieceOppositeList[r][c] = false;
                    }
                }
            }
            System.out.println("Successfully load game.");
        } catch (FileNotFoundException e) { // catch any error when handling loadGame()
            System.out.println("Error occur while loading game from file.");
        }
        return pieceOppositeList;
    }
}