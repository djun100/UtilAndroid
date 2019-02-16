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

    private static void show(String text,int timeLength){
        if (UtilThread.isMainThread()){
            Toast.makeText(UtilContext.getContext(), text, timeLength).show();
        }else {
            Looper.prepare();
            Toast.makeText(UtilContext.getContext(), text, timeLength).show();
            Looper.loop();
        }
    }
}
