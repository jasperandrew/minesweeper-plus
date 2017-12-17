package jasperandrew.minesweeper;

import android.graphics.Typeface;

class Const {
    static final int MAX_FPS = 30;

    static int SCREEN_WIDTH;
    static int SCREEN_HEIGHT;

    static int SCOREBOARD_HEIGHT;

    static int BLOCK_WIDTH;
    static int NUM_COLUMNS;
    static int NUM_ROWS;

    static Typeface FONT;

    enum State {
        BLOCK, FLAG, NUM
    }

    enum GameState {
        READY, RUNNING, OVER
    }

    static GameState gameState = GameState.READY;

    /* settings values */
    static int C = 16;
    static int E = 6;
}

/* Theme members:
    - background color
    - number images
    - block_images
    - font color
    - font
 */

/* Settings
    - Enable milliseconds
    - Easiness
    - Number of rows
    - Theme

 */