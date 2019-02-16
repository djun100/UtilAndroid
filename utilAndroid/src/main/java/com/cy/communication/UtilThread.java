package com.cy.communication;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by Administrator on 2016/8/24.
 */
public class UtilThread {

    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    /**
     * @param runnable
     */
    public static void runOnUIThread(Runnable runnable){
        if (isMainThread()) {
            runnable.run();
        } else {
            new Handler(Looper.getMainLooper()).post(runnable);
        }
    }

    /**
     * @param runnable
     */
    public static void runOnBackground(Runnable runnable){
        new Thread(runnable).start();
    }
}
