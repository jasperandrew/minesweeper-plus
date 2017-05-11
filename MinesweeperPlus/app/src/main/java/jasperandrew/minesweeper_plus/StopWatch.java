package jasperandrew.minesweeper_plus;

import android.os.SystemClock;

class StopWatch {
    private int secs, mins;
    private long start_time;
    private boolean running;

    StopWatch() {
        secs = 0;
        mins = 0;
        running = false;
    }

    void start() {
        start_time = SystemClock.elapsedRealtime();
        running = true;
    }

    void reset() {
        secs = 0;
        mins = 0;
        running = false;
    }

    void pause() { running = false; }

    void unpause() { running = true; }

    @Override
    public String toString() {
        return (mins < 10 ? "0"+mins : mins) + ":" + (secs < 10 ? "0"+secs : secs);
    }

    void update() {
        if(running) {
            long curr_time = SystemClock.elapsedRealtime();
            if ((curr_time - start_time) / 1000.0 >= 1) {
                if (secs < 59) {
                    secs++;
                } else {
                    secs = 0;
                    mins++;
                }
                start_time = curr_time;
            }
        }
    }
}
