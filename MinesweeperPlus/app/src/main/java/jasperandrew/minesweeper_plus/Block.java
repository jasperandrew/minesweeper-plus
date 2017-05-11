package jasperandrew.minesweeper_plus;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import java.util.ArrayList;

class Block {
    private Rect rect;
    private int value;
    private Bitmap num_img, img;
    private ArrayList<Bitmap> block_imgs;
    private Const.State state;

    Block(int col, int row, int value, Bitmap num_img, ArrayList<Bitmap> block_imgs) {
        int startX = Const.BLOCK_WIDTH * col;
        int startY = Const.BLOCK_WIDTH * row;
        this.rect = new Rect(startX, startY, startX+Const.BLOCK_WIDTH, startY+Const.BLOCK_WIDTH);
        this.value = value;
        this.num_img = num_img;
        this.block_imgs = block_imgs;
        this.state = Const.State.BLOCK;
    }

    int reveal() {
        if(state == Const.State.BLOCK){
            state = Const.State.NUM;
            if(value == 9) GameView.scoreboard.loseState();
            return value == 0 ? 0 : 1;
        }
        return -1;
    }

    boolean toggleFlag() {
        if(state == Const.State.FLAG){
            state = Const.State.BLOCK;
            GameView.scoreboard.changeBombs(1);
            return true;
        }else if(state == Const.State.BLOCK){
            if(GameView.scoreboard.numBombs() == 0) return false;
            GameView.scoreboard.changeBombs(-1);
            state = Const.State.FLAG;
            if(GameView.scoreboard.numBombs() == 0) GameView.block_manager.checkWin();
            return true;
        }
        return false;
    }

    int getValue() { return value; }

    Const.State getState() { return state; }

    void draw(Canvas canvas) {
        if(!Const.GAME_OVER){
            switch(state){
                case BLOCK:
                    canvas.drawBitmap(block_imgs.get(0), null, rect, new Paint());
                    break;
                case FLAG:
                    canvas.drawBitmap(block_imgs.get(1), null, rect, new Paint());
                    break;
                case NUM:
                    canvas.drawBitmap(num_img, null, rect, new Paint());
                    break;
                default:
            }
        }else{
            switch(state){
                case BLOCK:
                    canvas.drawBitmap(num_img, null, rect, new Paint());
                    break;
                case FLAG:
                    if(value != 9) canvas.drawBitmap(block_imgs.get(2), null, rect, new Paint());
                    else canvas.drawBitmap(block_imgs.get(1), null, rect, new Paint());
                    break;
                case NUM:
                    canvas.drawBitmap(num_img, null, rect, new Paint());
                    break;
                default:
            }
        }
    }
}