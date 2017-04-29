package com.cy.view;

import android.os.SystemClock;
import android.view.View;

/**
 * Created by <a href="https://github.com/djun100">cy</a> on 2016/5/31.
 */

public class UClick {
    public interface MultiClickListener{
        void onMultiClickEnd();
    }

    /**连续多次点击事件监听器
     * @param view         连续点击的view
     * @param clickTimes 连续点击多少次
     * @param milliseconds 限定的毫秒时间内
     * @param listener
     */
    public static void setMultiClickListener(View view, final int clickTimes
            , final long milliseconds
            , final MultiClickListener listener){
        view.setOnClickListener(new View.OnClickListener() {
            long[] mHints = new long[clickTimes];//初始全部为0
            @Override
            public void onClick(View v) {
                //将mHints数组内的所有元素左移一个位置
                System.arraycopy(mHints, 1, mHints, 0, mHints.length - 1);
                //获得当前系统已经启动的时间
                mHints[mHints.length - 1] = SystemClock.uptimeMillis();
                if (SystemClock.uptimeMillis()-mHints[0]<=milliseconds){
                    listener.onMultiClickEnd();
                }
            }
        });
    }
}
