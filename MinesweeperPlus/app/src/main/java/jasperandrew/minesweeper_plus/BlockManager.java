package jasperandrew.minesweeper_plus;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import java.util.ArrayList;
import java.util.Random;

class BlockManager {
    private ArrayList<ArrayList<Block>> board;
    private ArrayList<Bitmap> num_imgs, block_imgs;

    BlockManager(Context context) {
        num_imgs = new ArrayList<>();
        num_imgs.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.zero));
        num_imgs.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.one));
        num_imgs.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.two));
        num_imgs.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.three));
        num_imgs.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.four));
        num_imgs.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.five));
        num_imgs.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.six));
        num_imgs.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.seven));
        num_imgs.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.eight));
        num_imgs.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.bomb));

        block_imgs = new ArrayList<>();
        block_imgs.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.block));
        block_imgs.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.block_flag));
        block_imgs.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.block_flag_wrong));

        board = new ArrayList<>();
    }

    void init() {
        board.clear();

        int[][] grid = generateGrid();

        for(int col = 0; col < Const.NUM_COLUMNS; col++){
            ArrayList<Block> tmp = new ArrayList<>();
            for(int row = 0; row < Const.NUM_ROWS; row++){
                tmp.add(new Block(col, row, grid[col][row], num_imgs.get(grid[col][row]), block_imgs));
            }
            board.add(tmp);
        }
    }

    private int[][] generateGrid() {
        int[][] grid = new int[Const.NUM_COLUMNS][Const.NUM_ROWS];
        int bombs = Const.NUM_COLUMNS*Const.NUM_ROWS/Const.EASINESS;
        int cols = Const.NUM_COLUMNS-1;
        int rows = Const.NUM_ROWS-1;
        Random r = new Random();

        while(bombs > 0){
            int x = r.nextInt(Const.NUM_COLUMNS-1);
            int y = r.nextInt(Const.NUM_ROWS-1);
            x = Math.random() < 0.5 ? x : cols-x;
            y = Math.random() < 0.5 ? y : rows-y;

            if(grid[x][y] < 9){

                grid[x][y] = 9;
                if(x > 0)                grid[x-1][ y ]++;
                if(x > 0 && y > 0)       grid[x-1][y-1]++;
                if(y > 0)                grid[ x ][y-1]++;
                if(y > 0 && x < cols)    grid[x+1][y-1]++;
                if(x < cols)             grid[x+1][ y ]++;
                if(x < cols && y < rows) grid[x+1][y+1]++;
                if(y < rows)             grid[ x ][y+1]++;
                if(y < rows && x > 0)    grid[x-1][y+1]++;

                bombs--;
            }
        }

        for(int i = 0; i < Const.NUM_COLUMNS; i++){
            for(int j = 0; j < Const.NUM_ROWS; j++){
                if(grid[i][j] > 9) grid[i][j] = 9;
            }
        }

        return grid;
    }

    boolean revealBlocks(int col, int row) {
        int blockType = getBlock(col, row).reveal();
        int cols = Const.NUM_COLUMNS-1;
        int rows = Const.NUM_ROWS-1;

        if(blockType == 0){
            if(col > 0)                  revealBlocks(col-1, row  );
            if(col > 0 && row > 0)       revealBlocks(col-1, row-1);
            if(row > 0)                  revealBlocks(col  , row-1);
            if(row > 0 && col < cols)    revealBlocks(col+1, row-1);
            if(col < cols)               revealBlocks(col+1, row  );
            if(col < cols && row < rows) revealBlocks(col+1, row+1);
            if(row < rows)               revealBlocks(col  , row+1);
            if(row < rows && col > 0)    revealBlocks(col-1, row+1);
        }
        return blockType > -1;
    }

    Block getBlock(int col, int row) {
        return board.get(col).get(row);
    }

    void checkWin() {
        for(ArrayList<Block> col: board){
            for(Block block: col){
                if(block.getValue() == 9 && block.getState() != Const.State.FLAG) return;
            }
        }
        GameView.scoreboard.winState();
    }

    void draw(Canvas canvas) {
        for(ArrayList<Block> col: board){
            for(Block block: col){
                block.draw(canvas);
            }
        }
    }
}

