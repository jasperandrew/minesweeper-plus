package jasperandrew.minesweeper;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

class MainThread extends Thread {
    private final SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running;
    //private double averageFPS;



    void setRunning(boolean running) {
        this.running = running;
    }
    MainThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    @Override
    public void run() {
        long startTime;
        long timeMillis;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000/Const.MAX_FPS;

        while(running){
            startTime = System.nanoTime();
            Canvas canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    this.gameView.update();
                    this.gameView.draw(canvas);
                }
            } catch(Exception e){ e.printStackTrace(); }
            finally {
                if(canvas != null){
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch(Exception e) { e.printStackTrace(); }
                }
            }

            timeMillis = (System.nanoTime() - startTime)/1000000;
            waitTime = targetTime - timeMillis;

            try {
                if(waitTime > 0) sleep(waitTime);
            } catch (Exception e){ e.printStackTrace(); }

            totalTime += System.nanoTime() - startTime;
            frameCount++;

            if(frameCount == Const.MAX_FPS){
                //averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount = 0;
                totalTime = 0;
            }
        }
    }
}