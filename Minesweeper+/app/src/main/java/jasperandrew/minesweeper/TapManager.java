package jasperandrew.minesweeper;

import android.content.Context;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.MotionEvent;

class TapManager {
    private float x, y;
    private long tap_start;
    private Vibrator vibe;

    TapManager(Context context) {
        tap_start = 0;
        vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    private int blockUnit(float n) {
        return (int)(n/Const.BLOCK_WIDTH);
    }

    boolean manageEvent(MotionEvent event) {
        switch(event.getAction()){
            case(MotionEvent.ACTION_DOWN):
                x = event.getX();
                y = event.getY();
                tap_start = SystemClock.elapsedRealtime();
                break;

            case(MotionEvent.ACTION_MOVE):
                if(tap_start == 0 || event.getX()-x >= Const.BLOCK_WIDTH || event.getY()-y >= Const.BLOCK_WIDTH){
                    tap_start = 0;
                }
                break;

            case(MotionEvent.ACTION_UP):
                if(tap_start != 0
                        && event.getX()-x < Const.BLOCK_WIDTH && event.getY()-y < Const.BLOCK_WIDTH
                        && x < Const.NUM_COLUMNS*Const.BLOCK_WIDTH && y < Const.NUM_ROWS*Const.BLOCK_WIDTH){

                    GameView.block_manager.revealBlocks(blockUnit(x), blockUnit(y));
                    if(Const.gameState == Const.GameState.READY){
                        GameView.scoreboard.startTheClock();
                    }
                }
                tap_start = 0;
                break;

            default:
        }
        return true;
    }

    void update() {
        if(tap_start != 0 && (SystemClock.elapsedRealtime() - tap_start)/1000.0 >= 0.35){
            if(Const.gameState == Const.GameState.OVER){
                GameView.resetGame();
                vibe.vibrate(200);
                tap_start = 0;
                return;
            }
            System.out.println("hi");
            if(x < Const.NUM_COLUMNS*Const.BLOCK_WIDTH && y < Const.NUM_ROWS*Const.BLOCK_WIDTH
                    && GameView.block_manager.getBlock(blockUnit(x), blockUnit(y)).toggleFlag()){
                vibe.vibrate(100);
            }
            tap_start = 0;
        }
    }
}
