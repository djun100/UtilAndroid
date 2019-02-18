package com.cy.view;

import android.os.Looper;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.cy.app.UtilContext;
import com.cy.communication.UtilThread;

public class UtilToast {

    public static void showShort(String text){
        show(text,Toast.LENGTH_SHORT);
    }

    public static void showShort(@StringRes int textRes){
        show(textRes,Toast.LENGTH_SHORT);
    }

    public static void showLong(String text){
        show(text,Toast.LENGTH_LONG);
    }

    public static void showLong(@StringRes int textRes){
        show(textRes,Toast.LENGTH_LONG);
    }



    private static void show(@StringRes int textRes, int timeLength){
        show(UtilContext.getContext().getString(textRes),timeLength);
    }

    /**在非UI线程显示toast，如果使用Looper.prepare()...Looper.loop()，因loop()内部有死循环，
     * 所以toast后面不能有代码;
     * 所以非UI线程可以通过looper显示toast，但不建议使用，应转到UI线程去toast
     * @param text
     * @param timeLength
     */
    private static void show(final String text, final int timeLength){
        if (UtilThread.isMainThread()){
            Toast.makeText(UtilContext.getContext(), text, timeLength).show();
        }else {
            UtilThread.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(UtilContext.getContext(), text, timeLength).show();
                }
            });
        }
    }
}
