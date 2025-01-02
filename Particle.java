package memorygame;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Particle {
    private double x, y;
    private double vx, vy;
    private int life;
    private Color color;
    
    public Particle(int x, int y) {
        this.x = x;
        this.y = y;
        double angle = Math.random() * Math.PI * 2;
        double speed = Math.random() * 5 + 2;
        this.vx = Math.cos(angle) * speed;
        this.vy = Math.sin(angle) * speed;
        this.life = 50;
        this.color = GameConstants.HIGHLIGHT_COLOR;
    }
    
    public boolean update() {
        x += vx;
        y += vy;
        vy += 0.1;
        life--;
        return life > 0;
    }
    
    public void draw(Graphics2D g2d) {
        int alpha = (int)((life / 50.0) * 255);
        g2d.setColor(new Color(
            color.getRed(),
            color.getGreen(),
            color.getBlue(),
            alpha
        ));
        g2d.fill(new Ellipse2D.Double(x - 3, y - 3, 6, 6));
    }
}