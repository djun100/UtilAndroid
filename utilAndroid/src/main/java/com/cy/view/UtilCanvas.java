package com.cy.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.TypedValue;

import com.cy.app.UtilContext;

/**
 * Created by wangxuechao on 2017/4/12.
 */

public class UtilCanvas {
    private Paint mPaint;
    private Canvas mCanvas;

    public UtilCanvas(Paint paint, Canvas canvas) {
        mPaint = paint;
        mCanvas = canvas;
        if (paint==null){
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
        }
    }

    /**
     * @param color16 eg: Color.WHITE 0xFF000000
     * @param textSizeDp     eg: 14dp
     * @param centerX   中心点的x坐标
     * @param baselineYDp   底部基线 Ydp
     */
    public void drawText(int color16,int textSizeDp,int centerX,int baselineYDp,String text){
        mPaint.setColor(color16);
        mPaint.setTextSize(dp(textSizeDp));
        mPaint.setTextAlign(Paint.Align.CENTER);
        mCanvas.drawText(text,centerX,dp(baselineYDp),mPaint);
    }

    /**
     * 中空圆环
     */
    public void drawStrokeCircle(int color16,int strokeWidthDp,int centerXDp,int centerYDp,int radiusDp){
        mPaint.setColor(color16);
        mPaint.setStrokeWidth(dp(strokeWidthDp));
        mPaint.setStyle(Paint.Style.STROKE);
        mCanvas.drawCircle(centerXDp, centerYDp, radiusDp, mPaint);
    }

    /**
     * 圆弧
     * @param color16
     * @param strokeWidthDp
     * @param centerXDp
     * @param centerYDp
     * @param radiusDp
     * @param startAngle  起始弧度，+-360
     * @param sweepAngle  滑动弧长，0-360
     * @param drawInnerPart 是否填充圆内部中空部分
     */
    public void drawArc(int color16,int strokeWidthDp,int centerXDp,int centerYDp,int radiusDp,
                              float startAngle, float sweepAngle,boolean drawInnerPart){
        mPaint.setColor(color16);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(dp(strokeWidthDp));

        RectF rectF = new RectF(centerXDp - radiusDp , centerYDp - radiusDp ,
                centerXDp + radiusDp , centerYDp + radiusDp );
        mCanvas.drawArc(rectF, startAngle, sweepAngle, drawInnerPart, mPaint);
    }

    public static int dp(int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,
                UtilContext.getContext().getResources().getDisplayMetrics());
    }

//    public void drawBitmap(@DrawableRes int drawableRes,RectF rectF){
//        mCanvas.drawBitmap(BitmapFactory.decodeResource(UtilContext.getContext().getResources(),drawableRes),);
//    }
}
