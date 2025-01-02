package memorygame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    private JLabel timerLabel;
    private JButton startButton, pauseButton, stopButton;
    
    public ControlPanel(ActionListener gameController) {
        setBackground(GameConstants.BACKGROUND_COLOR);
        setLayout(new FlowLayout());
        
        startButton = createButton("Start Game", "start", gameController);
        pauseButton = createButton("Pause", "pause", gameController);
        stopButton = createButton("Stop", "stop", gameController);
        
        timerLabel = new JLabel("03:00");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        timerLabel.setForeground(GameConstants.TEXT_COLOR);
        
        JButton instructionsButton = createButton("Instructions", "instructions", gameController);
        
        add(startButton);
        add(pauseButton);
        add(stopButton);
        add(timerLabel);
        add(instructionsButton);
        
        pauseButton.setEnabled(false);
        stopButton.setEnabled(false);
    }
    
    private JButton createButton(String text, String command, ActionListener listener) {
        JButton button = new JButton(text);
        button.setBackground(GameConstants.TILE_COLOR);
        button.setForeground(GameConstants.TEXT_COLOR);
        button.setFocusPainted(false);
        button.setActionCommand(command);
        button.addActionListener(listener);
        return button;
    }
    
    public void updateTimer(int timeRemaining) {
        int minutes = timeRemaining / 60;
        int seconds = timeRemaining % 60;
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }
    
    public void setGameRunning(boolean running) {
        startButton.setEnabled(!running);
        pauseButton.setEnabled(running);
        stopButton.setEnabled(running);
    }
    
    public void setPaused(boolean paused) {
        pauseButton.setText(paused ? "Resume" : "Pause");
    }
}