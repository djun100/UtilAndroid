package com.cy.view.component;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 宽度自适应ImageView，宽度始终充满显示区域，高度成比例缩放
 */
public class AutoScaleImageView extends ImageView {

    public AutoScaleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        Drawable drawable = getDrawable();

        if (drawable != null) {
            int width = drawable.getMinimumWidth();
            int height = drawable.getMinimumHeight();


            if (widthMode == MeasureSpec.AT_MOST) {//宽度wrap_content
                float scale = (float) width / height;

                //强制根据图片原有比例，重新计算ImageView显示区域宽度
                int heightMeasure = MeasureSpec.getSize(heightMeasureSpec);
                int widthMeasure = (int) (heightMeasure * scale);

                //并设置为MeasureSpec.EXACTLY精确模式保证之后的super.onMeasure()不再调整
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthMeasure, MeasureSpec.EXACTLY);


            } else if (heightMode== MeasureSpec.AT_MOST){//高度wrap_content
                float scale = (float) height / width;

                int widthMeasure = MeasureSpec.getSize(widthMeasureSpec);
                int heightMeasure = (int) (widthMeasure * scale);

                heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightMeasure, MeasureSpec.EXACTLY);
            }
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
}