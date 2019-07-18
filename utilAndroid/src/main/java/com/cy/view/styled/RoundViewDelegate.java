package com.cy.view.styled;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.cy.utilandroid.R;
import com.cy.view.UtilColor;


/**
 * reference：https://www.jianshu.com/p/64a825915da9
 * https://github.com/CnPeng/CnPengAndroid
 *
 *  RippleDrawable(colorStateList, content, mask)
 * 当content为null时,波纹为无界.不为null时为有界
 * mask: 按照说明,mask是不会被draw的,但是它会限制波纹的边界,如果为null,默认为content的边界,
 * 同上,当content为null就没有边界了.
 * https://www.jianshu.com/p/0ef14eda6064
 */
public class RoundViewDelegate {
    private View view;
    private Context context;
    private GradientDrawable gd_background = new GradientDrawable();
    private GradientDrawable gd_background_press = new GradientDrawable();
    private GradientDrawable gd_background_disable = new GradientDrawable();
    private int backgroundColor;
    private int backgroundPressColor;
    private int backgroundDisableColor;
    private int cornerRadius;
    private int cornerRadius_TL;
    private int cornerRadius_TR;
    private int cornerRadius_BL;
    private int cornerRadius_BR;
    private int strokeWidth;
    private int strokeColor;
    private int strokePressColor;
    private int textPressColor;
    private int textDisableColor;
    private boolean isRadiusHalfHeight;
    private boolean isWidthHeightEqual;
    private boolean isRippleEnable;
    private boolean isRippleBorderless;
    private float[] radiusArr = new float[8];

    public RoundViewDelegate(View view, Context context, AttributeSet attrs) {
        this.view = view;
        this.context = context;
        obtainAttributes(context, attrs);
    }

    private void obtainAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundTextView);
        backgroundColor = ta.getColor(R.styleable.RoundTextView_rv_backgroundColor, Color.TRANSPARENT);
        backgroundPressColor = ta.getColor(R.styleable.RoundTextView_rv_backgroundPressColor, Integer.MAX_VALUE);
        backgroundDisableColor = ta.getColor(R.styleable.RoundTextView_rv_backgroundDisableColor, Integer.MAX_VALUE);
        cornerRadius = ta.getDimensionPixelSize(R.styleable.RoundTextView_rv_cornerRadius, 0);
        strokeWidth = ta.getDimensionPixelSize(R.styleable.RoundTextView_rv_strokeWidth, 0);
        strokeColor = ta.getColor(R.styleable.RoundTextView_rv_strokeColor, Color.TRANSPARENT);
        strokePressColor = ta.getColor(R.styleable.RoundTextView_rv_strokePressColor, Integer.MAX_VALUE);
        textPressColor = ta.getColor(R.styleable.RoundTextView_rv_textPressColor, Integer.MAX_VALUE);
        textDisableColor = ta.getColor(R.styleable.RoundTextView_rv_textDisableColor, Integer.MAX_VALUE);
        isRadiusHalfHeight = ta.getBoolean(R.styleable.RoundTextView_rv_isRadiusHalfHeight, false);
        isWidthHeightEqual = ta.getBoolean(R.styleable.RoundTextView_rv_isWidthHeightEqual, false);
        cornerRadius_TL = ta.getDimensionPixelSize(R.styleable.RoundTextView_rv_cornerRadius_TL, 0);
        cornerRadius_TR = ta.getDimensionPixelSize(R.styleable.RoundTextView_rv_cornerRadius_TR, 0);
        cornerRadius_BL = ta.getDimensionPixelSize(R.styleable.RoundTextView_rv_cornerRadius_BL, 0);
        cornerRadius_BR = ta.getDimensionPixelSize(R.styleable.RoundTextView_rv_cornerRadius_BR, 0);
        isRippleEnable = ta.getBoolean(R.styleable.RoundTextView_rv_isRippleEnable, true);
        isRippleBorderless = ta.getBoolean(R.styleable.RoundTextView_rv_isRippleBorderless, false);

        ta.recycle();
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        setBgSelector();
    }

    public void setBackgroundPressColor(int backgroundPressColor) {
        this.backgroundPressColor = backgroundPressColor;
        setBgSelector();
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = dp2px(cornerRadius);
        setBgSelector();
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = dp2px(strokeWidth);
        setBgSelector();
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        setBgSelector();
    }

    public void setStrokePressColor(int strokePressColor) {
        this.strokePressColor = strokePressColor;
        setBgSelector();
    }

    public void setTextPressColor(int textPressColor) {
        this.textPressColor = textPressColor;
        setBgSelector();
    }

    public void setIsRadiusHalfHeight(boolean isRadiusHalfHeight) {
        this.isRadiusHalfHeight = isRadiusHalfHeight;
        setBgSelector();
    }

    public void setIsWidthHeightEqual(boolean isWidthHeightEqual) {
        this.isWidthHeightEqual = isWidthHeightEqual;
        setBgSelector();
    }

    public void setCornerRadius_TL(int cornerRadius_TL) {
        this.cornerRadius_TL = cornerRadius_TL;
        setBgSelector();
    }

    public void setCornerRadius_TR(int cornerRadius_TR) {
        this.cornerRadius_TR = cornerRadius_TR;
        setBgSelector();
    }

    public void setCornerRadius_BL(int cornerRadius_BL) {
        this.cornerRadius_BL = cornerRadius_BL;
        setBgSelector();
    }

    public void setCornerRadius_BR(int cornerRadius_BR) {
        this.cornerRadius_BR = cornerRadius_BR;
        setBgSelector();
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public int getBackgroundPressColor() {
        return backgroundPressColor;
    }

    public int getCornerRadius() {
        return cornerRadius;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public int getStrokePressColor() {
        return strokePressColor;
    }

    public int getTextPressColor() {
        return textPressColor;
    }

    public boolean isRadiusHalfHeight() {
        return isRadiusHalfHeight;
    }

    public boolean isWidthHeightEqual() {
        return isWidthHeightEqual;
    }

    public int getCornerRadius_TL() {
        return cornerRadius_TL;
    }

    public int getCornerRadius_TR() {
        return cornerRadius_TR;
    }

    public int getCornerRadius_BL() {
        return cornerRadius_BL;
    }

    public int getCornerRadius_BR() {
        return cornerRadius_BR;
    }

    protected int dp2px(float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public void setBgSelector() {
        if (backgroundPressColor == Integer.MAX_VALUE) {
            backgroundPressColor = UtilColor.colorLightUp(backgroundColor);
        }
        if (strokePressColor == Integer.MAX_VALUE) {
            strokePressColor = strokeColor;
        }

        gd_background = new GradientDrawable();
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

        fillGradientDrawable(gd_background, backgroundColor, strokeColor);
        StateListDrawable listDrawable = new StateListDrawable();

        //注意该处的顺序，只要有一个状态与之相配，背景就会被换掉,不要把大范围放在前面
        listDrawable.addState(new int[]{-android.R.attr.state_pressed, android.R.attr.state_enabled}, gd_background);

        //add press state
        fillGradientDrawable(gd_background_press, backgroundPressColor, strokePressColor);
        listDrawable.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, gd_background_press);

        //add disable state
        if (backgroundDisableColor != Integer.MAX_VALUE) {
            fillGradientDrawable(gd_background_disable, backgroundDisableColor, strokeColor);
            listDrawable.addState(new int[]{-android.R.attr.state_enabled}, gd_background_disable);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && isRippleEnable) {
            int[][] stateList = new int[][]{
                    new int[]{android.R.attr.state_pressed},
                    new int[]{android.R.attr.state_focused},
                    new int[]{android.R.attr.state_activated},
                    new int[]{}
            };

            //ripple color，要明显区别于backgroundColor
            int rippleColor = Color.parseColor("#80000000");

            int[] stateColorList = new int[]{
                    backgroundPressColor,
                    backgroundPressColor,
                    backgroundPressColor,
                    rippleColor
            };
            ColorStateList colorStateList = new ColorStateList(stateList, stateColorList);

            RippleDrawable rippleDrawable = null;
            if (isRippleBorderless) {
                rippleDrawable = new RippleDrawable(colorStateList, null, null);
            } else {
                rippleDrawable = new RippleDrawable(colorStateList, listDrawable, null);
            }
            view.setBackground(rippleDrawable);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {//16
                view.setBackground(listDrawable);
            } else {
                //noinspection deprecation
                view.setBackgroundDrawable(listDrawable);
            }
        }

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
            radiusArr[0] = cornerRadius;
            radiusArr[1] = cornerRadius;
            radiusArr[2] = cornerRadius;
            radiusArr[3] = cornerRadius;
            radiusArr[4] = cornerRadius;
            radiusArr[5] = cornerRadius;
            radiusArr[6] = cornerRadius;
            radiusArr[7] = cornerRadius;
            gd.setCornerRadius(cornerRadius);
        } else if (isRadiusHalfHeight) {
            radiusArr[0] = Float.MAX_VALUE;
            radiusArr[1] = Float.MAX_VALUE;
            radiusArr[2] = Float.MAX_VALUE;
            radiusArr[3] = Float.MAX_VALUE;
            radiusArr[4] = Float.MAX_VALUE;
            radiusArr[5] = Float.MAX_VALUE;
            radiusArr[6] = Float.MAX_VALUE;
            radiusArr[7] = Float.MAX_VALUE;
            gd.setCornerRadius(Float.MAX_VALUE);
        }

        gd.setStroke(strokeWidth, strokeColor);
    }
}
