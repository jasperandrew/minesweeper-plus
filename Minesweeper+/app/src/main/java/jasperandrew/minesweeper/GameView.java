package jasperandrew.minesweeper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    static BlockManager block_manager;
    private TapManager tap_manager;
    static Scoreboard scoreboard;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);

        block_manager = new BlockManager(context);
        tap_manager = new TapManager(context);
        scoreboard = new Scoreboard(context);

        initBoard();

        setFocusable(true);
    }

    public void update() {
        tap_manager.update();
        if(Const.gameState != Const.GameState.OVER)
            scoreboard.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.LTGRAY); // Theme background color
        block_manager.draw(canvas);
        scoreboard.draw(canvas);
    }

    public static void resetGame() {
        initBoard();
    }

    public static void initBoard() {
        Const.NUM_COLUMNS = Const.C;
        Const.BLOCK_WIDTH = Const.SCREEN_WIDTH / Const.NUM_COLUMNS;
        Const.NUM_ROWS = (Const.SCREEN_HEIGHT - 100) / Const.BLOCK_WIDTH;

        Const.SCOREBOARD_HEIGHT = Const.SCREEN_HEIGHT - (Const.NUM_ROWS*Const.BLOCK_WIDTH);
        System.out.println("BW " + Const.BLOCK_WIDTH*Const.NUM_COLUMNS);
        System.out.println("SW " + Const.SCREEN_WIDTH);
        block_manager.init();
        scoreboard.init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return tap_manager.manageEvent(event);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry){
            try {
                thread.setRunning(false);
                thread.join();
            } catch(Exception e) { e.printStackTrace(); }

            retry = false;
        }
    }
}
