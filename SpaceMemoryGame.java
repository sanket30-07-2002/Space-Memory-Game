package memorygame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SpaceMemoryGame extends JFrame implements ActionListener {
    private GamePanel gamePanel;
    private ControlPanel controlPanel;
    private Timer gameTimer;
    private int timeRemaining;
    private boolean isGameStarted;
    private boolean isPaused;
    
    public SpaceMemoryGame() {
        setTitle("Space Memory Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        
        controlPanel = new ControlPanel(this);
        gamePanel = new GamePanel(this);
        
        add(controlPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);
        
        initializeTimer();
        pack();
        setLocationRelativeTo(null);
    }
    
    private void initializeTimer() {
        gameTimer = new Timer(1000, e -> {
            if (timeRemaining > 0) {
                timeRemaining--;
                controlPanel.updateTimer(timeRemaining);
                if (timeRemaining == 0) {
                    gameOver();
                }
            }
        });
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "start" -> startGame();
            case "pause" -> pauseGame();
            case "stop" -> stopGame();
            case "instructions" -> showInstructions();
        }
    }
    
    private void startGame() {
        timeRemaining = GameConstants.GAME_TIME;
        isGameStarted = true;
        gameTimer.start();
        controlPanel.setGameRunning(true);
        gamePanel.resetGame();
    }
    
    private void pauseGame() {
        if (!isPaused) {
            gameTimer.stop();
            isPaused = true;
        } else {
            gameTimer.start();
            isPaused = false;
        }
        controlPanel.setPaused(isPaused);
    }
    
    private void stopGame() {
        gameTimer.stop();
        isGameStarted = false;
        isPaused = false;
        timeRemaining = GameConstants.GAME_TIME;
        controlPanel.updateTimer(timeRemaining);
        controlPanel.setGameRunning(false);
        controlPanel.setPaused(false);
        gamePanel.resetGame();
    }
    
    void gameOver() {
        gameTimer.stop();
        JOptionPane.showMessageDialog(this,
            "Game Over!\nFinal Score: " + gamePanel.getScore(),
            "Time's Up!",
            JOptionPane.INFORMATION_MESSAGE);
        stopGame();
    }
    
    private void showInstructions() {
        String instructions = """
            🎮 Space Memory Game Instructions 🚀
            
            Objective:
            Match pairs of space-themed tiles within 3 minutes to score points and complete the game!
            
            How to Play:
            1. Click 'Start Game' to begin your space adventure
            2. Click any tile to reveal its space symbol
            3. Find and click its matching pair
            4. Match all pairs before time runs out!
            
            Game Controls:
            • Start Game: Begins a new game session
            • Pause: Temporarily stops the timer
            • Resume: Continues a paused game
            • Stop: Ends the current game
            
            Scoring System:
            • Each matched pair: 100 points
            • Try to get the highest score possible!
            
            Time Limit:
            • 3 minutes (180 seconds) per game
            • Timer shows remaining time
            • Game ends when time runs out
            
            Game Symbols:
            🚀 Rocket    ⭐ Star
            🌎 Earth     🌙 Moon
            ☄️ Comet     👾 Alien
            🛸 UFO       🌠 Shooting Star
            
            Winning Strategy Tips:
            • Remember the positions of revealed tiles
            • Work quickly but strategically
            • Use the pause button if you need to think
            • Try to memorize patterns of symbols
            
            Good luck, Space Explorer! 🍀
            """;
        
        // Create a styled text area for instructions
        JTextArea textArea = new JTextArea(instructions);
        textArea.setEditable(false);
        textArea.setBackground(GameConstants.BACKGROUND_COLOR);
        textArea.setForeground(GameConstants.TEXT_COLOR);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setMargin(new Insets(10, 10, 10, 10));
        
        // Create a scrollable pane for the instructions
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 500));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        // Show the instructions in a dialog without trying to load an icon
        JOptionPane.showMessageDialog(
            this,
            scrollPane,
            "How to Play Space Memory Game",
            JOptionPane.PLAIN_MESSAGE
        );
    }

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SpaceMemoryGame().setVisible(true);
        });
    }
}