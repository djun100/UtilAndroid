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
     * 设计稿按其他屏尺寸设计，dp按设计稿中2x取值，可以适配当前屏幕的尺寸
     * @param uiDesignedDpValue 设计图标注dp
     * @param uiDesignedScreenHeight    设计图基于的表屏高度
     * @param uiDesignedScreenDensity   设计图基于的表屏密度
     * @return
     */
    public static int getScaledPx(float uiDesignedDpValue,float uiDesignedScreenHeight,float uiDesignedScreenDensity) {
        Context context= UtilContext.getContext();

        int uiDesignedPx = (int) (uiDesignedDpValue * uiDesignedScreenDensity + 0.5F);
        float ratio = context.getResources().getDisplayMetrics().heightPixels / uiDesignedScreenHeight;
        int scaledPx = (int) (uiDesignedPx * ratio);
        return scaledPx;
    }
}
