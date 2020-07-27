package com.cy.view;

import android.animation.ArgbEvaluator;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;

public class UtilColor {
    public static int colorLightDown(@ColorInt int colorInt) {
        return colorLightDown(colorInt, 0.2f);
    }

    public static int colorLightUp(@ColorInt int colorInt) {
        return colorLightUp(colorInt, 0.2f);
    }

    /**
     * 颜色降低亮度
     */
    public static int colorLightDown(@ColorInt int colorInt, @FloatRange(from = 0, to = 1) float light) {
        float[] hsv = new float[3];
        Color.colorToHSV(colorInt, hsv);
        colorInt = Color.HSVToColor(new float[]{hsv[0], hsv[1], (float) (hsv[2] - light)});
        return colorInt;
    }

    /**
     * 颜色增加亮度
     */
    public static int colorLightUp(@ColorInt int colorInt, @FloatRange(from = 0, to = 1) float light) {
        float[] hsv = new float[3];
        Color.colorToHSV(colorInt, hsv);
        colorInt = Color.HSVToColor(new float[]{hsv[0], hsv[1], (float) (hsv[2] + light)});
        return colorInt;
    }

    /**
     * 计算颜色中间值
     * @param ratio 0~1.0f
     * @param startColor
     * @param endColor
     * @return
     */
    public static int getMiddleColor(float ratio,int startColor,int endColor){
        ArgbEvaluator argbEvaluator = new ArgbEvaluator();
        int currColor = (int)(argbEvaluator.evaluate(ratio,startColor, endColor));
        return currColor;
    }
}