package com.cy.view;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.view.View;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.cy.app.UtilContext;

/**
 * 注意：最后一定要调用apply();容易忘记调用，不调用不生效
 * 推荐使用 api 'com.flyco.roundview:FlycoRoundView_Lib:1.1.4@aar'，利于解耦，ui绘制尽量放到xml
 * <p>
 * extract from https://github.com/H07000223/FlycoRoundView
 * RippleDrawable 知识参考： http://www.jianshu.com/p/0ef14eda6064
 * 使用方法：
 * new UtilViewStyle(mbtn).setBackgroundColor(0xff22dd90)
 * .setStrokeColor(Color.GREEN)
 * .setStrokeWidth(1)
 * //.setBackgroundPressColor(0xffff0000)
 * //.setRadiusHalfHeight(true) //强烈建议使用{@link #setCornerRadius(int)}替代，值设为>=view 高度即可
 * .setRippleEnable(true)
 * .apply();
 * <p>
 * Created by cy on 2017/7/17.
 * fixme xml设置的背景色会被丢失，应获取并设为默认
 */

public class UtilViewStyle {

    private View view;
    private float cornerRadius;
    private boolean isRadiusHalfHeight;
    private int backgroundColor = Integer.MAX_VALUE;
    private int backgroundPressColor = Integer.MAX_VALUE;
    private int backgroundDisableColor = Integer.MAX_VALUE;
    private int cornerRadius_TL;
    private int cornerRadius_TR;
    private int cornerRadius_BL;
    private int cornerRadius_BR;
    private int strokeWidth;
    private int strokeColor = Integer.MAX_VALUE;
    private int strokePressColor = Integer.MAX_VALUE;
    private int textPressColor = Integer.MAX_VALUE;
    private int textDisableColor = Integer.MAX_VALUE;
    private boolean isRippleEnable;
    private float[] radiusArr = new float[8];
    private GradientDrawable.Orientation orientation;
    private int startColor = Integer.MAX_VALUE;
    private int endColor = Integer.MAX_VALUE;
    private GradientDrawable gd_background;
    private GradientDrawable gd_background_press;
    private GradientDrawable gd_background_disable;

    public static UtilViewStyle view(View view) {
        return new UtilViewStyle(view);
    }

    public UtilViewStyle(View view) {
        this.view = view;
    }

    public float getCornerRadius() {
        return cornerRadius;
    }

    public UtilViewStyle setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        return this;
    }

    public boolean isRadiusHalfHeight() {
        return isRadiusHalfHeight;
    }

    public UtilViewStyle setCornerRadiusHalfHeight() {
        isRadiusHalfHeight = true;
        return this;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public UtilViewStyle setBackgroundColor(@ColorInt int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public UtilViewStyle setBackgroundColorRes(@ColorRes int backgroundColorRes) {
        this.backgroundColor = UtilContext.getContext().getResources().getColor(backgroundColorRes);
        return this;
    }

    public int getBackgroundPressColor() {
        return backgroundPressColor;
    }

    public UtilViewStyle setBackgroundPressColor(@ColorInt int backgroundPressColor) {
        this.backgroundPressColor = backgroundPressColor;
        return this;
    }

    public UtilViewStyle setBackgroundPressColorRes(@ColorRes int backgroundPressColorRes) {
        this.backgroundPressColor = UtilContext.getContext().getResources().getColor(backgroundPressColorRes);
        return this;
    }

    public int getBackgroundDisableColor() {
        return backgroundDisableColor;
    }

    public UtilViewStyle setBackgroundDisableColor(@ColorInt int backgroundDisableColor) {
        this.backgroundDisableColor = backgroundDisableColor;
        return this;
    }

    public UtilViewStyle setBackgroundDisableColorRes(@ColorRes int backgroundDisableColorRes) {
        this.backgroundDisableColor = UtilContext.getContext().getResources().getColor(backgroundDisableColorRes);
        return this;
    }

    public int getCornerRadius_TL() {
        return cornerRadius_TL;
    }

    public UtilViewStyle setCornerRadius_TL(int cornerRadius_TL) {
        this.cornerRadius_TL = cornerRadius_TL;
        return this;
    }

    public int getCornerRadius_TR() {
        return cornerRadius_TR;
    }

    public UtilViewStyle setCornerRadius_TR(int cornerRadius_TR) {
        this.cornerRadius_TR = cornerRadius_TR;
        return this;
    }

    public int getCornerRadius_BL() {
        return cornerRadius_BL;
    }

    public UtilViewStyle setCornerRadius_BL(int cornerRadius_BL) {
        this.cornerRadius_BL = cornerRadius_BL;
        return this;
    }

    public int getCornerRadius_BR() {
        return cornerRadius_BR;
    }

    public UtilViewStyle setCornerRadius_BR(int cornerRadius_BR) {
        this.cornerRadius_BR = cornerRadius_BR;
        return this;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public UtilViewStyle setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        return this;
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public UtilViewStyle setStrokeColor(@ColorInt int strokeColor) {
        this.strokeColor = strokeColor;
        return this;
    }

    public UtilViewStyle setStrokeColorRes(@ColorRes int strokeColorRes) {
        this.strokeColor = UtilContext.getContext().getResources().getColor(strokeColorRes);
        return this;
    }

    public int getStrokePressColor() {
        return strokePressColor;
    }

    public UtilViewStyle setStrokePressColor(@ColorInt int strokePressColor) {
        this.strokePressColor = strokePressColor;
        return this;
    }

    public UtilViewStyle setStrokePressColorRes(@ColorRes int strokePressColorRes) {
        this.strokePressColor = UtilContext.getContext().getResources().getColor(strokePressColorRes);
        return this;
    }

    public int getTextPressColor() {
        return textPressColor;
    }

    public UtilViewStyle setTextPressColor(@ColorInt int textPressColor) {
        this.textPressColor = textPressColor;
        return this;
    }

    public UtilViewStyle setTextPressColorRes(@ColorRes int textPressColorRes) {
        this.textPressColor = UtilContext.getContext().getResources().getColor(textPressColorRes);
        return this;
    }

    public int getTextDisableColor() {
        return textDisableColor;
    }

    public UtilViewStyle setTextDisableColor(@ColorInt int textDisableColor) {
        this.textDisableColor = textDisableColor;
        return this;
    }

    public UtilViewStyle setTextDisableColorRes(@ColorRes int textDisableColorRes) {
        this.textDisableColor = UtilContext.getContext().getResources().getColor(textDisableColorRes);
        return this;
    }

    public boolean isRippleEnable() {
        return isRippleEnable;
    }

    public UtilViewStyle setRippleEnable(boolean rippleEnable) {
        isRippleEnable = rippleEnable;
        return this;
    }

    public GradientDrawable.Orientation getOrientation() {
        return orientation;
    }

    public UtilViewStyle setOrientation(GradientDrawable.Orientation orientation) {
        this.orientation = orientation;
        return this;
    }

    public int getStartColor() {
        return startColor;
    }

    public UtilViewStyle setStartColor(int startColor) {
        this.startColor = startColor;
        return this;
    }

    public int getEndColor() {
        return endColor;
    }

    public UtilViewStyle setEndColor(int endColor) {
        this.endColor = endColor;
        return this;
    }

    private void fillGradientDrawable(GradientDrawable gd, int color, int strokeColor) {
        if (color != Integer.MAX_VALUE) {
            gd.setColor(color);
        }

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
            gd.setCornerRadius(Float.MAX_VALUE);
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
        if (strokePressColor == Integer.MAX_VALUE) {
            strokePressColor = strokeColor;
        }
        if (orientation != null && startColor != Integer.MAX_VALUE &&
                endColor != Integer.MAX_VALUE) {
            gd_background = new GradientDrawable(orientation, new int[]{startColor, endColor});
        } else {
            gd_background = new GradientDrawable();
        }
        gd_background_press = new GradientDrawable();
        gd_background_disable = new GradientDrawable();

        try {
            Drawable drawable = view.getBackground();//如果是button的话可能是rippleDrawable
            if (drawable != null && drawable instanceof ColorDrawable) {
                backgroundColor = ((ColorDrawable) drawable).getColor();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (backgroundPressColor == Integer.MAX_VALUE) {
            backgroundPressColor = backgroundColor;
        }
        fillGradientDrawable(gd_background, backgroundColor, strokeColor);
        StateListDrawable bg = new StateListDrawable();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && isRippleEnable) {
            MaterialRippleLayout.on(view)
                    .rippleColor(Color.parseColor("#000000"))
//                    .rippleColor(isRippleColorDeep ? Color.parseColor("#000000") : Color.parseColor("#ffffff"))
                    .rippleAlpha(0.2f)
                    .rippleHover(true)
                    .rippleOverlay(true)
//                .rippleRoundedCorners(100)//大于某一数值为圆角
                    .create();

        }
//         else {
        //注意该处的顺序，只要有一个状态与之相配，背景就会被换掉,不要把大范围放在前面
        bg.addState(new int[]{-android.R.attr.state_pressed, android.R.attr.state_enabled}, gd_background);

        //add press state
        if (backgroundPressColor != Integer.MAX_VALUE || strokePressColor != Integer.MAX_VALUE) {
            fillGradientDrawable(gd_background_press,
                    backgroundPressColor == Integer.MAX_VALUE ? backgroundColor : backgroundPressColor,
                    strokePressColor == Integer.MAX_VALUE ? strokeColor : strokePressColor);
            bg.addState(new int[]{android.R.attr.state_pressed}, gd_background_press);
        }

        addDisableState(bg);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {//16
            view.setBackground(bg);
        } else {
            //noinspection deprecation
            view.setBackgroundDrawable(bg);
        }
//        }

        if (view instanceof TextView) {

            ColorStateList textColors = ((TextView) view).getTextColors();
            int defaultColor = textColors.getDefaultColor();

            textDisableColor = textDisableColor == Integer.MAX_VALUE ? defaultColor : textDisableColor;
            textPressColor = textPressColor == Integer.MAX_VALUE ? defaultColor : textPressColor;

            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{
                            new int[]{-android.R.attr.state_enabled},
                            new int[]{-android.R.attr.state_pressed},
                            new int[]{android.R.attr.state_pressed}
                    },
                    new int[]{textDisableColor,
                            defaultColor,
                            textPressColor}
            );

            ((TextView) view).setTextColor(colorStateList);

        }
    }

    private void addDisableState(StateListDrawable bg) {
        //add disable state
        if (backgroundDisableColor != Integer.MAX_VALUE) {
            fillGradientDrawable(gd_background_disable,
                    backgroundDisableColor,
                    backgroundDisableColor);
            bg.addState(new int[]{-android.R.attr.state_enabled}, gd_background_disable);
        }
    }
}
