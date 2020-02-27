package com.cy.file;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.cy.app.UtilContext;
import com.cy.io.Log;


public class UtilResource {

    public static int getRawResourceId(Resources resources, String resourceIdName, String packageName) {
        int rawResId = resources.getIdentifier(resourceIdName, "raw", packageName);
        return rawResId;
    }

    public static int getDrawableResourceId(Resources resources, String resourceIdName, String packageName) {
        int drawableResId = resources.getIdentifier(resourceIdName, "drawable", packageName);
        return drawableResId;
    }

    public static Drawable getDrawable(Resources resources, String resourceIdName, String packageName) {
        int imageResId = resources.getIdentifier(resourceIdName, "drawable", packageName);
        Drawable drawable = resources.getDrawable(imageResId);
        return drawable;
    }

    private static int designed_device_density;
    private static int designed_device_screen_height;

    public static void initDesignedDeviceSize(int density,int screen_height){
        designed_device_density = density;
        designed_device_screen_height = screen_height;
    }
    /**
     * 设计稿按joy3设计，dp按设计稿中2x取值，可以适配短屏幕的Y1表
     * @param joy3DpValue
     * @return
     */
    public static int getScaledPx(float joy3DpValue) {
//        Log.i("screen --> joy3 dp:" + joy3DpValue);
        int joy3Px = (int) (joy3DpValue * designed_device_density + 0.5F);
//        Log.i("screen --> joy3 px:" + joy3Px);
        float ratio = UtilContext.getContext().getResources().getDisplayMetrics().heightPixels / designed_device_screen_height;
        int scaledPx = (int) (joy3Px * ratio);
//        Log.i("screen --> scaledPx:" + scaledPx);
        return scaledPx;
    }
}
