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
                if(tap_start == 0 || event.getX()-x >= Const.BLOCK_WIDTH || event.getX()-x <= (-1)*Const.BLOCK_WIDTH
                        || event.getY()-y >= Const.BLOCK_WIDTH || event.getY()-y <= (-1)*Const.BLOCK_WIDTH ){
                    tap_start = 0;
                }
                break;

            case(MotionEvent.ACTION_UP):
                if(tap_start != 0
                        && event.getX()-x < Const.BLOCK_WIDTH && event.getY()-y < Const.BLOCK_WIDTH
                        && x < Const.NUM_COLUMNS*Const.BLOCK_WIDTH && y < Const.NUM_ROWS*Const.BLOCK_WIDTH+Const.SCOREBOARD_HEIGHT && y > Const.SCOREBOARD_HEIGHT){

                    GameView.block_manager.revealBlocks(blockUnit(x), blockUnit(y-Const.SCOREBOARD_HEIGHT));
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
        if(tap_start != 0 && (SystemClock.elapsedRealtime() - tap_start)/1000.0 >= 0.30){
            if(Const.gameState == Const.GameState.OVER){
                GameView.resetGame();
                long[] pattern = {0, 50, 50, 100};
                vibe.vibrate(pattern, -1);
                tap_start = 0;
                return;
            }

            if(x < Const.NUM_COLUMNS*Const.BLOCK_WIDTH && y < Const.NUM_ROWS*Const.BLOCK_WIDTH+Const.SCOREBOARD_HEIGHT && y > Const.SCOREBOARD_HEIGHT
                    && GameView.block_manager.getBlock(blockUnit(x), blockUnit(y-Const.SCOREBOARD_HEIGHT)).toggleFlag()){
                vibe.vibrate(50);
            }
            tap_start = 0;
        }
    }
}
