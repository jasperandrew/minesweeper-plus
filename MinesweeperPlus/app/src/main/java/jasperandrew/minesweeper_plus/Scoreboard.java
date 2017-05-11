package jasperandrew.minesweeper_plus;

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

    Scoreboard(int height, Context context) {
        bombs = Const.NUM_COLUMNS*Const.NUM_ROWS/Const.EASINESS;
        stopwatch = new StopWatch();

        text_paint = new Paint();
        text_paint.setTypeface(Const.FONT);
        text_paint.setColor(Color.WHITE);
        text_paint.setTextSize(80);

        gamestate_rect = new Rect(Const.SCREEN_WIDTH/2 - height/2, Const.SCREEN_HEIGHT-height, Const.SCREEN_WIDTH/2 + height/2, Const.SCREEN_HEIGHT);
        playing_img = BitmapFactory.decodeResource(context.getResources(), R.drawable.block);
        win_img = BitmapFactory.decodeResource(context.getResources(), R.drawable.block_flag);
        lose_img = BitmapFactory.decodeResource(context.getResources(), R.drawable.bomb);
    }

    void init() {
        stopwatch.reset();
        stopwatch.start();
        curr_img = playing_img;
        bombs = Const.NUM_COLUMNS*Const.NUM_ROWS/Const.EASINESS;
        Const.GAME_OVER = false;
    }

    void winState() {
        curr_img = win_img;
        Const.GAME_OVER = true;
    }

    void loseState() {
        curr_img = lose_img;
        Const.GAME_OVER = true;
    }

    private String bombString() {
        if(bombs < 10) return "00"+bombs;
        if(bombs < 100) return "0"+bombs;
        return ""+bombs;
    }

    int numBombs() { return bombs; }

    void changeBombs(int n) { bombs += n; }

    void draw(Canvas canvas) {
        canvas.drawText(stopwatch.toString(), 140, Const.SCREEN_HEIGHT-25, text_paint);
        canvas.drawText(bombString(), Const.SCREEN_WIDTH-290, Const.SCREEN_HEIGHT-25, text_paint);
        canvas.drawBitmap(curr_img, null, gamestate_rect, new Paint());
    }

    void update() {
        stopwatch.update();
    }
}
