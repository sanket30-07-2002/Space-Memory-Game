package memorygame;

import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;

public class GamePanel extends JPanel {
    private static final int ROWS = 4;
    private static final int COLS = 4;
    private static final int TILE_SIZE = 100;
    private static final int GAP = 10;
    
    private Tile[][] tiles;
    private int score;
    private Tile firstSelected;
    private boolean isProcessing;
    private ArrayList<Particle> particles;
    private SpaceMemoryGame gameController;
    
    public GamePanel(SpaceMemoryGame controller) {
        this.gameController = controller;
        this.particles = new ArrayList<>();
        
        setPreferredSize(new Dimension(
            COLS * (TILE_SIZE + GAP) + GAP,
            ROWS * (TILE_SIZE + GAP) + GAP + 50
        ));
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleMouseClick(e.getX(), e.getY());
            }
        });
        
        // Animation timer for particles
        Timer animationTimer = new Timer(16, e -> {
            updateParticles();
            repaint();
        });
        animationTimer.start();
        
        resetGame();
    }
    
    public void resetGame() {
        score = 0;
        firstSelected = null;
        isProcessing = false;
        particles.clear();
        initializeTiles();
        repaint();
    }
    
    private void initializeTiles() {
        tiles = new Tile[ROWS][COLS];
        ArrayList<String> symbols = new ArrayList<>();
        
        // Add pairs of symbols
        for (String symbol : GameConstants.SPACE_SYMBOLS) {
            symbols.add(symbol);
            symbols.add(symbol);
        }
        Collections.shuffle(symbols);
        
        int index = 0;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                tiles[row][col] = new Tile(symbols.get(index++), row, col);
            }
        }
    }
    
    private void handleMouseClick(int x, int y) {
        if (isProcessing) return;
        
        int col = (x - GAP) / (TILE_SIZE + GAP);
        int row = (y - GAP) / (TILE_SIZE + GAP);
        
        if (row >= 0 && row < ROWS && col >= 0 && col < COLS) {
            handleTileClick(tiles[row][col]);
        }
    }
    
    private void handleTileClick(Tile tile) {
        if (tile.isRevealed()) return;
        
        tile.setRevealed(true);
        
        if (firstSelected == null) {
            firstSelected = tile;
        } else {
            isProcessing = true;
            
            if (firstSelected.getSymbol().equals(tile.getSymbol())) {
                // Match found
                score += 100;
                createMatchParticles(tile);
                isProcessing = false;
                firstSelected = null;
                
                // Check for win condition
                if (checkWinCondition()) {
                    gameController.gameOver();
                }
            } else {
                // No match
                Timer timer = new Timer(1000, e -> {
                    firstSelected.setRevealed(false);
                    tile.setRevealed(false);
                    firstSelected = null;
                    isProcessing = false;
                    ((Timer)e.getSource()).stop();
                });
                timer.setRepeats(false);
                timer.start();
            }
        }
        repaint();
    }
    
    private boolean checkWinCondition() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (!tiles[row][col].isRevealed()) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private void createMatchParticles(Tile tile) {
        int centerX = GAP + tile.getCol() * (TILE_SIZE + GAP) + TILE_SIZE / 2;
        int centerY = GAP + tile.getRow() * (TILE_SIZE + GAP) + TILE_SIZE / 2;
        
        for (int i = 0; i < 20; i++) {
            particles.add(new Particle(centerX, centerY));
        }
    }
    
    private void updateParticles() {
        particles.removeIf(particle -> !particle.update());
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw background
        g2d.setColor(GameConstants.BACKGROUND_COLOR);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        // Draw tiles
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                drawTile(g2d, tiles[row][col]);
            }
        }
        
        // Draw particles
        for (Particle particle : particles) {
            particle.draw(g2d);
        }
        
        // Draw score
        g2d.setColor(GameConstants.TEXT_COLOR);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("Score: " + score, 10, getHeight() - 20);
    }
    
    private void drawTile(Graphics2D g2d, Tile tile) {
        int x = GAP + tile.getCol() * (TILE_SIZE + GAP);
        int y = GAP + tile.getRow() * (TILE_SIZE + GAP);
        
        // Draw tile background
        g2d.setColor(tile.isRevealed() ? GameConstants.HIGHLIGHT_COLOR : GameConstants.TILE_COLOR);
        g2d.fill(new RoundRectangle2D.Float(x, y, TILE_SIZE, TILE_SIZE, 15, 15));
        
        // Draw symbol if revealed
        if (tile.isRevealed()) {
            g2d.setColor(GameConstants.TEXT_COLOR);
            g2d.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
            FontMetrics fm = g2d.getFontMetrics();
            int symbolWidth = fm.stringWidth(tile.getSymbol());
            int symbolHeight = fm.getHeight();
            g2d.drawString(tile.getSymbol(),
                         x + (TILE_SIZE - symbolWidth) / 2,
                         y + (TILE_SIZE + symbolHeight) / 2 - 10);
        }
    }
    
    public int getScore() {
        return score;
    }
}

