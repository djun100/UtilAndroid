package com.cy.view;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.StringRes;
import android.widget.Toast;


import com.cy.app.UtilContext;
import com.cy.communication.UtilThread;

import java.lang.reflect.Field;


public class UtilToast {

    private static Field sField_TN;
    private static Field sField_TN_Handler;
    static {
        try {
            sField_TN = Toast.class.getDeclaredField("mTN");
            sField_TN.setAccessible(true);
            sField_TN_Handler = sField_TN.getType().getDeclaredField("mHandler");
            sField_TN_Handler.setAccessible(true);
        } catch (Exception e) {}
    }

    public static void showShort(String text){
        show(text,Toast.LENGTH_SHORT);
    }

    public static void showShort(@StringRes int textRes){
        show(UtilContext.getContext().getString(textRes),Toast.LENGTH_SHORT);
    }

    public static void showLong(String text){
        show(text,Toast.LENGTH_LONG);
    }

    public static void showLong(@StringRes int textRes){
        show(UtilContext.getContext().getString(textRes),Toast.LENGTH_LONG);
    }

    /**在非UI线程显示toast，如果使用Looper.prepare()...Looper.loop()，因loop()内部有死循环，
     * 所以toast后面不能有代码;
     * 所以非UI线程可以通过looper显示toast，但不建议使用，应转到UI线程去toast
     * @param text
     * @param duration
     */
    private static void show(final String text, final int duration){
        if (UtilThread.isMainThread()){
            _show(text, duration);
        }else {
            UtilThread.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    _show(text, duration);
                }
            });
        }
    }

    private static void _show(final String text, final  int duration) {
        Toast toast = Toast.makeText(UtilContext.getContext(), text, duration);
        if(Build.VERSION.SDK_INT < 26) { //Android8.0以前
            hook(toast);
        }
        toast.show();
    }

    private static void hook(Toast toast) {
        try {
            Object tn = sField_TN.get(toast);
            Handler preHandler = (Handler)sField_TN_Handler.get(tn);
            sField_TN_Handler.set(tn,new SafelyHandlerWrapper(preHandler));
        } catch (Exception e) {}
    }


    private static class SafelyHandlerWrapper extends Handler {

        private final Handler impl;

        public SafelyHandlerWrapper(Handler impl) {
            this.impl = impl;
        }

        @Override
        public void dispatchMessage(Message msg) {
            try {
                super.dispatchMessage(msg);
            } catch (Exception e) {}
        }

        @Override
        public void handleMessage(Message msg) {
            impl.handleMessage(msg);//需要委托给原Handler执行
        }
    }
}
