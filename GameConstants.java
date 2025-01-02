package memorygame;
import java.awt.Color;

public class GameConstants {
    public static final int ROWS = 4;
    public static final int COLS = 4;
    public static final int TILE_SIZE = 100;
    public static final int GAP = 10;
    public static final int GAME_TIME = 180; // 3 minutes in seconds
    
    // Color scheme
    public static final Color BACKGROUND_COLOR = new Color(22, 27, 34);
    public static final Color TILE_COLOR = new Color(48, 54, 61);
    public static final Color HIGHLIGHT_COLOR = new Color(88, 166, 255);
    public static final Color TEXT_COLOR = new Color(201, 209, 217);
    
    // Game symbols
    public static final String[] SPACE_SYMBOLS = {
        "🚀", "⭐", "🌎", "🌙", "☄️", "👾", "🛸", "🌠"
    };
}