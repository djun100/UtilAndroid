package com.cy.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.cy.view.UtilScreen;


/**
 模糊阴影控件
 android:layout_width="0dp"
 android:layout_height="60dp"
 app:blurColor="#25000000"
 app:blurRadius="20dp"
 适合高度较低的UI的阴影
 android:layout_width="0dp"
 android:layout_height="70dp"
 app:blurColor="#40000000"
 app:blurRadius="20dp"
 适合高度较大的UI的阴影
 */
public class ShaderView extends View {
    private float blurRadius;
    private int blurColor;
    private RectF rectF = new RectF();
    public ShaderView(Context context) {
        this(context, null);
    }

    public ShaderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShaderView);
        blurRadius = typedArray.getDimension(R.styleable.ShaderView_blurRadius, UtilScreen.dp(20));
        blurColor = typedArray.getColor(R.styleable.ShaderView_blurColor,0x40000000);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float space = blurRadius / 1.45f;
        rectF.left = space;
        rectF.top = space;
        rectF.right = getMeasuredWidth() - space;
        rectF.bottom = getMeasuredHeight() - space;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint4 = new Paint();
        paint4.setColor(blurColor);
        paint4.setStyle(Paint.Style.FILL);
        paint4.setMaskFilter(new BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL));
        canvas.drawOval(rectF, paint4);
    }
}
