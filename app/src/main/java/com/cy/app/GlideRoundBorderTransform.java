package com.cy.app;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorInt;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;

import java.security.MessageDigest;

public class GlideRoundBorderTransform extends BitmapTransformation {
    private final String ID = getClass().getName();
    private Paint mBorderPaint;
    private float borderWidth;
    private float cornerRadius;

    public GlideRoundBorderTransform(float cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public GlideRoundBorderTransform setBorder(float borderWidth,@ColorInt int borderColor) {
        this.borderWidth = borderWidth;
        mBorderPaint = new Paint();
        mBorderPaint.setColor(borderColor);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setStrokeWidth(borderWidth);
        mBorderPaint.setDither(true);

        return this;
    }

    @Override
    protected Bitmap transform(BitmapPool bitmapPool, Bitmap bitmap, int outWidth, int outHeight) {
        bitmap = TransformationUtils.centerCrop(bitmapPool, bitmap, outWidth, outHeight);
        return circleCrop(bitmapPool, bitmap);
    }

    private Bitmap circleCrop(BitmapPool bitmapPool, Bitmap source) {

        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        Bitmap square = Bitmap.createBitmap(source, x, y, size, size);
        Bitmap result = bitmapPool.get(size, size, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }

        //画图
        Canvas canvas = new Canvas(result);
        float radius = size / 2f;

        //绘制一个圆
        if (cornerRadius > Math.max(source.getWidth(), source.getHeight()) / 2) {
            Paint paint = new Paint();
            //设置 Shader
            paint.setShader(new BitmapShader(square, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);

            canvas.drawCircle(radius, radius, radius, paint);
        } else {
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            paint.setShadowLayer(5, 15, 20, Color.GREEN);
            RectF rectF = new RectF(0f, 0f, source.getWidth()-20, source.getHeight()-20);
            canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint);

        }


        /************************描边*********************/
        //注意：避免出现描边被屏幕边缘裁掉
        if (mBorderPaint != null) {
            float borderRadius = radius - (borderWidth / 2);
            //画边框
            if (cornerRadius > Math.max(source.getWidth(), source.getHeight()) / 2) {
                canvas.drawCircle(radius, radius, borderRadius, mBorderPaint);
            } else {
                //https://blog.csdn.net/kuaiguixs/article/details/78753149 解决四个圆角比较粗的问题
                RectF rectF = new RectF(1.5f, 1.5f, source.getWidth() - 1.5f, source.getHeight() - 1.5f);
                canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, mBorderPaint);
            }
        }
        return result;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ID.getBytes(CHARSET));
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof GlideRoundBorderTransform;
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }


}
