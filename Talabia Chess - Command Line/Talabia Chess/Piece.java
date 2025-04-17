// Author: Ho Teck Fung

public abstract class Piece {

    // Making them only accessible in the same package and subclasses.
    protected Player owner;
    protected int curR;
    protected int curC;
    protected boolean opposite;

    // Constructor
    Piece(Player owner, int curR, int curC, boolean opposite) {
        this.owner = owner;
        this.curR = curR;
        this.curC = curC;
        this.opposite = opposite;
    }

    // Method to update the position of this piece and change its direction.
    public void refreshLocation(int curR, int curC) {
        this.curR = curR;
        this.curC = curC;
        changeOpposite();
    }

    // Getters & Setters for the properties
    public int getCurR() {
        return curR;
    }

    public void setCurR(int curR) {
        this.curR = curR;
    }

    public int getCurC() {
        return curC;
    }

    public void setCurC(int curC) {
        this.curC = curC;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public boolean isOpposite() {
        return opposite;
    }

    public void setOpposite(boolean opposite) {
        this.opposite = opposite;
    }

    // Method to change the direction of this piece.
    public void changeOpposite() {
        setOpposite(!isOpposite());
    }

    // Abstract method to move this piece to a new position on the board.
    // This method must be implemented by any class that extends Piece.
    abstract public boolean movePiece(int newR, int newC);

    public boolean isCaptured() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isCaptured'");
    }

}