package dev.ricr.androidone.Helpers;

import java.util.concurrent.Executor;

public class AsyncRunner {

    /**
     * @param executor = the main thread
     * @param fn       = function to run
     * @param delay    = delay in ms
     */
    public static void setTimeout(Executor executor, Runnable fn, int delay) {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                executor.execute(fn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}
