package jasperandrew.minesweeper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

class Scoreboard {
    private int bombs;
    private StopWatch stopwatch;
    private Paint text_paint;
    private Rect gamestate_rect;
    private Bitmap playing_img, win_img, lose_img, curr_img;

    Scoreboard(Context context) {
        stopwatch = new StopWatch();

        text_paint = new Paint();
        text_paint.setTypeface(Const.FONT); // Theme font
        text_paint.setColor(Color.BLACK); // Theme font color
        text_paint.setTextSize(100);

        playing_img = BitmapFactory.decodeResource(context.getResources(), R.drawable.classic_block);
        win_img = BitmapFactory.decodeResource(context.getResources(), R.drawable.classic_flag);
        lose_img = BitmapFactory.decodeResource(context.getResources(), R.drawable.classic_bomb);
    }

    void init() {
        stopwatch.reset();
        curr_img = playing_img;
        bombs = GameView.block_manager.numBombs();
        Const.gameState = Const.GameState.READY;

        int ht = (int)(Const.SCOREBOARD_HEIGHT*0.6);
        int gap = (int)(Const.SCOREBOARD_HEIGHT*0.2);

        gamestate_rect = new Rect(Const.SCREEN_WIDTH/2-ht/2, gap, Const.SCREEN_WIDTH/2+ht/2, Const.SCOREBOARD_HEIGHT-gap);
    }

    void startTheClock() {
        stopwatch.start();
    }

    void gameOver(boolean won) {
        curr_img = won ? win_img : lose_img;
        Const.gameState = Const.GameState.OVER;
    }

    private String bombString() {
        if(bombs < 0) return "ERR";
        if(bombs < 10) return "00"+bombs;
        if(bombs < 100) return "0"+bombs;
        return ""+bombs;
    }

    int numBombs() { return bombs; }

    void updateBombNum(int n) { bombs += n; }

    void draw(Canvas canvas) {
        canvas.drawText(stopwatch.toString(), 140, Const.SCOREBOARD_HEIGHT/2+35, text_paint);
        canvas.drawText(bombString(), Const.SCREEN_WIDTH-295, Const.SCOREBOARD_HEIGHT/2+35, text_paint);
        canvas.drawBitmap(curr_img, null, gamestate_rect, new Paint());
    }

    void update() {
        stopwatch.update();
    }
}