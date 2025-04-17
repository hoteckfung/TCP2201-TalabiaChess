// Author: Sadman Zuifiquer

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class GUI extends JFrame {

    // Constants for board dimensions
    public static final int BOARD_LENGTH = 7;
    public static final int BOARD_HEIGHT = 6;

    // 2D array to represent the chessboard tiles
    private static Tile[][] tiles;
    private JLabel messageLabel;

    private ImageIcon createImageIcon(String path, int width, int height) {
        return new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }

    private final ImageIcon pointY = createImageIcon("assets/Point_Y.png", 60, 60);
    private final ImageIcon pointB = createImageIcon("assets/Point_B.png", 60, 60);
    private final ImageIcon pointYO = createImageIcon("assets/Point_Y_O.png", 60, 60);
    private final ImageIcon pointBO = createImageIcon("assets/Point_B_O.png", 60, 60);
    private final ImageIcon sunY = createImageIcon("assets/Sun_Y.png", 60, 60);
    private final ImageIcon sunB = createImageIcon("assets/Sun_B.png", 60, 60);
    private final ImageIcon timeY = createImageIcon("assets/Time_Y.png", 60, 60);
    private final ImageIcon timeB = createImageIcon("assets/Time_B.png", 60, 60);
    private final ImageIcon plusY = createImageIcon("assets/Plus_Y.png", 60, 60);
    private final ImageIcon plusB = createImageIcon("assets/Plus_B.png", 60, 60);
    private final ImageIcon hourglassY = createImageIcon("assets/Hourglass_Y.png", 60, 60);
    private final ImageIcon hourglassB = createImageIcon("assets/Hourglass_B.png", 60, 60);

    // Constructor for the GUI class
    public GUI() {
        super("Talabia Chess");
        initializeMainWindow();
        initializeMenuBar();
        initializeChessboard();
        initializeMessagePanel();
        loadPiecesOntoTiles();
        loadBoardToUI();
        setVisible(true);
    }

    // Constants for window dimensions
    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 700;

    // Method to initialize the main window
    private void initializeMainWindow() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(true);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // Method to initialize the menu bar
    private void initializeMenuBar() {
        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        List<JMenuItem> menuItems = new ArrayList<>();
        menuItems.add(createMenuItem("New Game", new NewGameListener()));
        menuItems.add(createMenuItem("Save Game", new SaveGameListener()));
        menuItems.add(createMenuItem("Load Game", new LoadGameListener()));
        menuItems.add(createMenuItem("Exit Game", new ExitGameListener()));

        for (JMenuItem menuItem : menuItems) {
            fileMenu.add(menuItem);
        }

        // Add a MouseListener to the "File" menu
        fileMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playSound("notify.wav");
            }
        });

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    // Method to create a JMenuItem with a specified title and ActionListener
    private JMenuItem createMenuItem(String title, ActionListener listener) {
        JMenuItem menuItem = new JMenuItem(title);
        menuItem.addActionListener(listener);
        return menuItem;
    }

    // Method to initialize the chessboard
    private void initializeChessboard() {
        // Creating the chessboard as a JPanel with GridLayout
        JPanel boardPanel = new JPanel(new GridLayout(BOARD_HEIGHT, BOARD_LENGTH));
        tiles = new Tile[BOARD_HEIGHT][BOARD_LENGTH];
        for (int r = 0; r < BOARD_HEIGHT; r++) {
            for (int c = 0; c < BOARD_LENGTH; c++) {
                tiles[r][c] = new Tile();
                tiles[r][c].addActionListener(new TileActionListener());
                if ((r + c) % 2 != 0) // if every two spaces
                    tiles[r][c].setBackground(new Color(216, 217, 216));
                else
                    tiles[r][c].setBackground(new Color(168, 169, 168));
                boardPanel.add(tiles[r][c]);
            }
        }
        add(boardPanel, BorderLayout.CENTER);
    }

    // Method to initialize the message panel
    private void initializeMessagePanel() {
        // Creating a message panel with a JLabel
        JPanel messagePanel = new JPanel();
        messageLabel = new JLabel("Good Luck Have Fun!");
        messagePanel.add(messageLabel);
        add(messagePanel, BorderLayout.SOUTH);
    }

    // Inner class representing a tile on the chessboard (a JButton)
    private static class Tile extends JButton {
        public Piece chessPiece;

        Tile() { // Constructor for Tile class (initializes the tile as empty)
            chessPiece = null;
        }
    }

    // Method to load pieces onto the tiles
    // if at opposite, then Piece(getPlayer(), r, c, true)
    private void loadPiecesOntoTiles() {
        for (int r = 0; r < BOARD_HEIGHT; r++) {
            for (int c = 0; c < BOARD_LENGTH; c++) {
                tiles[r][c].chessPiece = createPiece(Board.board[r][c], r, c);
            }
        }
    }

    // Method to create a piece based on pieceCode and position
    private Piece createPiece(String pieceCode, int r, int c) {
        Player player = pieceCode.endsWith("B") ? Rule.getPlayerB() : Rule.getPlayerY();
        boolean isOpposite = pieceCode.endsWith("B");
        switch (pieceCode.substring(0, 2)) {
            case "Pl":
                return new Plus(player, r, c, isOpposite);
            case "Ho":
                return new Hourglass(player, r, c, isOpposite);
            case "Su":
                return new Sun(player, r, c, isOpposite);
            case "Po":
                return new Point(player, r, c, isOpposite);
            case "Ti":
                return new Time(player, r, c, isOpposite);
            default:
                return null;
        }
    }

    // Method to load the current state of the board to the UI
    private void loadBoardToUI() {
        for (int r = 0; r < BOARD_HEIGHT; r++) {
            for (int c = 0; c < BOARD_LENGTH; c++) {
                tiles[r][c].setIcon(getIconForPiece(Board.board[r][c], tiles[r][c].chessPiece));
            }
        }
    }

    // Method to get the appropriate ImageIcon for a piece
    // Can use map for better flexibility
    private ImageIcon getIconForPiece(String pieceCode, Piece piece) {
        boolean isOpposite = piece != null && piece.isOpposite();
        switch (pieceCode) {
            case "PlB":
                return plusB;
            case "PlY":
                return plusY;
            case "HoB":
                return hourglassB;
            case "HoY":
                return hourglassY;
            case "SuB":
                return sunB;
            case "SuY":
                return sunY;
            case "PoB":
                return isOpposite ? pointBO : pointB;
            case "PoY":
                return isOpposite ? pointYO : pointY;
            case "TiB":
                return timeB;
            case "TiY":
                return timeY;
            default:
                return null;
        }
    }

    // Method to play a sound file (in the sounds folder)
    private void playSound(String soundFileName) {
        try {
            String soundFilePath = "sounds/" + soundFileName;
            AudioInputStream audioInputStream = AudioSystem
                    .getAudioInputStream(new File(soundFilePath).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ActionListener for the chessboard tiles
    private class TileActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            // Handling tile clicks and game logic
            for (int r = 0; r < BOARD_HEIGHT; r++) {
                for (int c = 0; c < BOARD_LENGTH; c++) {
                    if (ae.getSource() == tiles[r][c]) {
                        System.out.println("Button [" + r + "][" + c + "] pressed.\n");
                        if (tiles[r][c].chessPiece != null) {
                            Player tempPerson = tiles[r][c].chessPiece.getOwner();
                            System.out.println("Now playing: " + tempPerson.getColor());
                        }
                        if (Rule.selectedTilePiece == null && tiles[r][c].chessPiece != null) {
                            if ((Rule.playerY.getTurn() && tiles[r][c].chessPiece.getOwner() == Rule.playerY) ||
                                    (Rule.playerB.getTurn() && tiles[r][c].chessPiece.getOwner() == Rule.playerB)) {
                                System.out.println("[Play selected tile]\n");
                                Rule.selectedTilePiece = tiles[r][c].chessPiece;
                            } else if (!Rule.playerY.getTurn() && tiles[r][c].chessPiece.getOwner() == Rule.playerY) {
                                System.out.println("Error: [Not your turn]\n");
                                Rule.selectedTilePiece = null;
                            } else if (!Rule.playerB.getTurn() && tiles[r][c].chessPiece.getOwner() == Rule.playerB) {
                                System.out.println("Error: [Not your turn]\n");
                                Rule.selectedTilePiece = null;
                            }
                        } else if (Rule.selectedTilePiece != null && tiles[r][c].chessPiece == Rule.selectedTilePiece) {
                            System.out.println("Error: [Reset]\n"); // invoke error when [newR][newC] == [curR][curC]
                            Rule.selectedTilePiece = null;
                        } else if (Rule.selectedTilePiece != null) {
                            // Perform the move for eating opponent's piece
                            if (Rule.selectedTilePiece.movePiece(r, c)) {
                                System.out.println("[Moved piece]\n");
                                playSound("move.wav"); // play sound when piece is moved
                                Player tempPlayer = Rule.selectedTilePiece.getOwner();
                                if (tempPlayer.getColor() == 'Y') {
                                    Rule.playerY.setMoveCount(Rule.playerY.getMoveCount() + 1);
                                } else if (tempPlayer.getColor() == 'B') {
                                    Rule.playerB.setMoveCount(Rule.playerB.getMoveCount() + 1);
                                }
                                tiles[Rule.selectedTilePiece.getCurR()][Rule.selectedTilePiece
                                        .getCurC()].chessPiece = null;
                                Rule.selectedTilePiece.setCurR(r);
                                Rule.selectedTilePiece.setCurC(c);
                                tiles[r][c].chessPiece = Rule.selectedTilePiece;
                                swapPieces();
                                Rule.swapPiece();
                                Board.flipBoard();
                                Rule.changePlayerTurn();
                                flipTiles();
                                loadBoardToUI();
                                if (Rule.playerB.getTurn()) {
                                    messageLabel.setText("Blue Player's Turn");
                                } else {
                                    messageLabel.setText("Yellow Player's Turn");
                                }
                                setVisible(true);
                            } else {
                                playSound("illegal.wav"); // play sound when illegal move is made
                                System.out.println("Error: [Invalid Move]\n"); // invoke error when invalid move
                            }
                            Rule.selectedTilePiece = null;
                        } else if (tiles[r][c].chessPiece == null) {
                            System.out.println("Error: [Select a Piece]\n"); // invoke error when no piece was selected
                            Rule.selectedTilePiece = null;
                        }
                    }
                }
            }
            for (int i = 0; i < BOARD_HEIGHT; i++) {
                for (int j = 0; j < BOARD_LENGTH; j++) {
                    System.out.print(Board.board[i][j] + " ");
                }
                System.out.println();
            }
            if (Rule.checkEndGame()) { // if game is over
                if (Rule.playerY.getWin()) { // if playerY wins
                    playSound("win.wav");
                    messageLabel.setText("Game Over! Yellow Player Wins");
                } else { // if playerB wins
                    playSound("win.wav");
                    messageLabel.setText("Game Over! Blue Player Wins");
                }
            }
            System.out.println("===========================");
        }
    }

    // ActionListener for starting a new game
    private class NewGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            FileManager.newGame();
            messageLabel.setText("New Game.");
            Rule.playerY = new Player('Y', true);
            Rule.playerB = new Player('B', false);
            loadPiecesOntoTiles();
            loadBoardToUI();
            setVisible(true);
        }
    }

    // ActionListener for saving the current game
    private class SaveGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            FileManager.saveGame();
            messageLabel.setText("Saved Game.");
        }
    }

    // ActionListener for loading a saved game
    private class LoadGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            boolean[][] oppList = FileManager.loadGame();
            messageLabel.setText("Loaded Game.");
            loadPiecesOntoTiles();
            for (int r = 0; r < BOARD_HEIGHT; r++) {
                for (int c = 0; c < BOARD_LENGTH; c++) {
                    if (tiles[r][c].chessPiece != null) {
                        setPieceOpposite(r, c, oppList[r][c]);
                    }
                }
            }
            loadBoardToUI();
            setVisible(true);
        }
    }

    // ActionListener for exiting the game
    private static class ExitGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            System.exit(0);
        }
    }

    // Method to flip the tiles on the board
    public static void flipTiles() {
        Piece[][] tempPieceList = new Piece[BOARD_HEIGHT][BOARD_LENGTH];
        for (int r = 0; r < BOARD_HEIGHT; r++) {
            for (int c = 0; c < BOARD_LENGTH; c++) {
                tempPieceList[BOARD_HEIGHT - 1 - r][BOARD_LENGTH - 1 - c] = tiles[r][c].chessPiece;
            }
        }
        for (int r = 0; r < BOARD_HEIGHT; r++) {
            for (int c = 0; c < BOARD_LENGTH; c++) {
                tiles[r][c].chessPiece = tempPieceList[r][c];
                if (tiles[r][c].chessPiece != null) {
                    tiles[r][c].chessPiece.refreshLocation(r, c);
                    Player tempPlayer = tiles[r][c].chessPiece.getOwner();
                    if (tempPlayer.getColor() == 'Y') {
                        tiles[r][c].chessPiece.setOwner(Rule.playerY);
                    } else if (tempPlayer.getColor() == 'B') {
                        tiles[r][c].chessPiece.setOwner(Rule.playerB);
                    }
                }
            }
        }
    }

    // Method to swap Plus and Time pieces based on number of player turns
    public static void swapPieces() {
        for (int r = 0; r < BOARD_HEIGHT; r++) {
            for (int c = 0; c < BOARD_LENGTH; c++) {
                if (Rule.playerY.getMoveCount() % 2 == 0 && Rule.playerY.getTurn()) {
                    if (Board.board[r][c].equals("TiY")) {
                        tiles[r][c].chessPiece = new Plus(Rule.getPlayerY(), r, c, true);
                    } else if (Board.board[r][c].equals("PlY")) {
                        tiles[r][c].chessPiece = new Time(Rule.getPlayerY(), r, c, false);
                    }
                } else if (Rule.playerB.getMoveCount() % 2 == 0 && Rule.playerB.getTurn()) {
                    if (Board.board[r][c].equals("TiB")) {
                        tiles[r][c].chessPiece = new Plus(Rule.getPlayerB(), r, c, true);
                    } else if (Board.board[r][c].equals("PlB")) {
                        tiles[r][c].chessPiece = new Time(Rule.getPlayerB(), r, c, false);
                    }
                }
            }
        }
    }

    // Method to check if a piece is opposite
    public static boolean isPieceOpposite(int r, int c) {
        return tiles[r][c].chessPiece.isOpposite();
    }

    // Method to set a piece as opposite
    public static void setPieceOpposite(int r, int c, boolean opp) {
        tiles[r][c].chessPiece.setOpposite(opp);
    }

    // Method to get the piece at a specified position
    public static Piece getPieceAtPosition(int r, int c) {
        return tiles[r][c].chessPiece;
    }
}