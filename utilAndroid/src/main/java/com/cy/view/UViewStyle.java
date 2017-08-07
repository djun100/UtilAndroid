package com.cy.view;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.view.View;
import android.widget.TextView;

/**
 * extract from https://github.com/H07000223/FlycoRoundView
 * RippleDrawable 知识参考： http://www.jianshu.com/p/0ef14eda6064
 * 使用方法：
 * new UViewStyle(mbtn).setBackgroundColor(0xff22dd90)
 * .setStrokeColor(Color.GREEN)
 * .setStrokeWidth(1)
 * //.setBackgroundPressColor(0xffff0000)
 * //.setRadiusHalfHeight(true) //强烈建议使用{@link #setCornerRadius(int)}替代，值设为>=view 高度即可
 * .setRippleEnable(true)
 * .apply();
 * <p>
 * Created by cy on 2017/7/17.
 */

public class UViewStyle {

    private View view;
    private int cornerRadius;
    private boolean isRadiusHalfHeight;
    private int backgroundColor;
    private int backgroundPressColor = Integer.MAX_VALUE;
    private int cornerRadius_TL;
    private int cornerRadius_TR;
    private int cornerRadius_BL;
    private int cornerRadius_BR;
    private int strokeWidth;
    private int strokeColor;
    private int strokePressColor;
    private int textPressColor = Integer.MAX_VALUE;
    private boolean isRippleEnable;
    private float[] radiusArr = new float[8];
    private GradientDrawable gd_background = new GradientDrawable();
    private GradientDrawable gd_background_press = new GradientDrawable();

    public UViewStyle(View view) {
        this.view = view;
    }

    public int getCornerRadius() {
        return cornerRadius;
    }

    public UViewStyle setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        return this;
    }

    public boolean isRadiusHalfHeight() {
        return isRadiusHalfHeight;
    }

    /**
     * 设定radius>=height即可，如果使用该函数需要在onGloableLayout中使用，不然获取不到view的高度
     */
    @Deprecated
    public UViewStyle setRadiusHalfHeight(boolean radiusHalfHeight) {
        isRadiusHalfHeight = radiusHalfHeight;
        return this;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public UViewStyle setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public int getBackgroundPressColor() {
        return backgroundPressColor;
    }

    public UViewStyle setBackgroundPressColor(int backgroundPressColor) {
        this.backgroundPressColor = backgroundPressColor;
        return this;
    }

    public int getCornerRadius_TL() {
        return cornerRadius_TL;
    }

    public UViewStyle setCornerRadius_TL(int cornerRadius_TL) {
        this.cornerRadius_TL = cornerRadius_TL;
        return this;
    }

    public int getCornerRadius_TR() {
        return cornerRadius_TR;
    }

    public UViewStyle setCornerRadius_TR(int cornerRadius_TR) {
        this.cornerRadius_TR = cornerRadius_TR;
        return this;
    }

    public int getCornerRadius_BL() {
        return cornerRadius_BL;
    }

    public UViewStyle setCornerRadius_BL(int cornerRadius_BL) {
        this.cornerRadius_BL = cornerRadius_BL;
        return this;
    }

    public int getCornerRadius_BR() {
        return cornerRadius_BR;
    }

    public UViewStyle setCornerRadius_BR(int cornerRadius_BR) {
        this.cornerRadius_BR = cornerRadius_BR;
        return this;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public UViewStyle setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        return this;
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public UViewStyle setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        return this;
    }

    public int getStrokePressColor() {
        return strokePressColor;
    }

    public UViewStyle setStrokePressColor(int strokePressColor) {
        this.strokePressColor = strokePressColor;
        return this;
    }

    public int getTextPressColor() {
        return textPressColor;
    }

    public UViewStyle setTextPressColor(@ColorInt int textPressColor) {
        this.textPressColor = textPressColor;
        return this;
    }

    public boolean isRippleEnable() {
        return isRippleEnable;
    }

    public UViewStyle setRippleEnable(boolean rippleEnable) {
        isRippleEnable = rippleEnable;
        return this;
    }


    private void fillGradientDrawable(GradientDrawable gd, int color, int strokeColor) {
        gd.setColor(color);

        if (cornerRadius_TL > 0 || cornerRadius_TR > 0 || cornerRadius_BR > 0 || cornerRadius_BL > 0) {
            /**The corners are ordered top-left, top-right, bottom-right, bottom-left*/
            radiusArr[0] = cornerRadius_TL;
            radiusArr[1] = cornerRadius_TL;
            radiusArr[2] = cornerRadius_TR;
            radiusArr[3] = cornerRadius_TR;
            radiusArr[4] = cornerRadius_BR;
            radiusArr[5] = cornerRadius_BR;
            radiusArr[6] = cornerRadius_BL;
            radiusArr[7] = cornerRadius_BL;
            gd.setCornerRadii(radiusArr);
        } else if (cornerRadius > 0) {
            gd.setCornerRadius(cornerRadius);
        } else if (isRadiusHalfHeight) {
            gd.setCornerRadius(view.getHeight() / 2);
        }

        gd.setStroke(strokeWidth, strokeColor);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private ColorStateList getPressedColorSelector(int normalColor, int pressedColor) {
        return new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_pressed},
                        new int[]{android.R.attr.state_focused},
                        new int[]{android.R.attr.state_activated},
                        new int[]{}
                },
                new int[]{
                        pressedColor,
                        pressedColor,
                        pressedColor,
                        normalColor
                }
        );
    }

    public void apply() {
        StateListDrawable bg = new StateListDrawable();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && isRippleEnable) {


            fillGradientDrawable(gd_background, backgroundColor, strokeColor);
            RippleDrawable rippleDrawable = new RippleDrawable(
                    ColorStateList.valueOf(Color.GRAY),//灰色波纹
//						getPressedColorSelector(backgroundColor, backgroundPressColor),
                    gd_background,
                    null);
            view.setBackground(rippleDrawable);


        } else {
            fillGradientDrawable(gd_background, backgroundColor, strokeColor);
            bg.addState(new int[]{-android.R.attr.state_pressed}, gd_background);
            if (backgroundPressColor != Integer.MAX_VALUE || strokePressColor != Integer.MAX_VALUE) {
                fillGradientDrawable(gd_background_press, backgroundPressColor == Integer.MAX_VALUE ? backgroundColor : backgroundPressColor,
                        strokePressColor == Integer.MAX_VALUE ? strokeColor : strokePressColor);
                bg.addState(new int[]{android.R.attr.state_pressed}, gd_background_press);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {//16
                view.setBackground(bg);
            } else {
                //noinspection deprecation
                view.setBackgroundDrawable(bg);
            }
        }

        if (view instanceof TextView) {
            if (textPressColor != Integer.MAX_VALUE) {
                ColorStateList textColors = ((TextView) view).getTextColors();
//              Log.d("AAA", textColors.getColorForState(new int[]{-android.R.attr.state_pressed}, -1) + "");
                ColorStateList colorStateList = new ColorStateList(
                        new int[][]{new int[]{-android.R.attr.state_pressed}, new int[]{android.R.attr.state_pressed}},
                        new int[]{textColors.getDefaultColor(), textPressColor});
                ((TextView) view).setTextColor(colorStateList);
            }
        }
    }
}
