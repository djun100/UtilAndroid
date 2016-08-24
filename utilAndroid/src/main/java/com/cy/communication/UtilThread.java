package com.cy.communication;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.cy.app.UtilContext;

/**
 * Created by Administrator on 2016/8/24.
 */
public class UtilThread {

    public static void toast(final String content){
        Runnable runnable = new Runnable() {
            public void run() {
                Toast.makeText(UtilContext.getContext(),content, Toast.LENGTH_SHORT).show();
            }
        };
        runOnUIThread(runnable);
    }

    /**
     * @param runnable
     */
    public static void runOnUIThread(Runnable runnable){
        if (Looper.myLooper() != Looper.getMainLooper()) {
            new Handler(Looper.getMainLooper()).post(runnable);
        } else {
            runnable.run();
        }
    }
}
