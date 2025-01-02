package memorygame;

public class Tile {
    private String symbol;
    private int row, col;
    private boolean isRevealed;
    
    public Tile(String symbol, int row, int col) {
        this.symbol = symbol;
        this.row = row;
        this.col = col;
        this.isRevealed = false;
    }
    
    // Getters and setters
    public String getSymbol() { return symbol; }
    public int getRow() { return row; }
    public int getCol() { return col; }
    public boolean isRevealed() { return isRevealed; }
    public void setRevealed(boolean revealed) { isRevealed = revealed; }
}
