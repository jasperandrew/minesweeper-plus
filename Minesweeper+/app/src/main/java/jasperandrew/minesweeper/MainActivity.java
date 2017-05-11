package jasperandrew.minesweeper;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        Const.SCREEN_WIDTH = dm.widthPixels;
        Const.SCREEN_HEIGHT = dm.heightPixels;

        Const.NUM_COLUMNS = 10;
        Const.BLOCK_WIDTH = Const.SCREEN_WIDTH / Const.NUM_COLUMNS;
        Const.NUM_ROWS = (Const.SCREEN_HEIGHT - 100)/ Const.BLOCK_WIDTH;

        Const.FONT = Typeface.createFromAsset(getAssets(), "fonts/UbuntuMono.ttf");

        setContentView(new GameView(this));
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}