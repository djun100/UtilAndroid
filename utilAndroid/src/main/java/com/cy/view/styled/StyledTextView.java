package com.cy.view.styled;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 用于需要圆角矩形框背景的TextView的情况,减少直接使用TextView时引入的shape资源文件
 */
public class StyledTextView extends TextView {
    private StyledViewDelegate delegate;

    public StyledTextView(Context context) {
        this(context, null);
    }

    public StyledTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StyledTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        delegate = new StyledViewDelegate(this, context, attrs);
    }

    /**
     * use delegate to set attr
     */
    public StyledViewDelegate getDelegate() {
        return delegate;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (delegate.isWidthHeightEqual() && getMeasuredWidth() > 0 && getMeasuredHeight() > 0) {
            int max = Math.max(getMeasuredWidth(), getMeasuredHeight());
            setMeasuredDimension(max, max);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (delegate.isRadiusHalfHeight()) {
            delegate.setCornerRadius(getHeight() / 2);
        } else {
            delegate.setBgSelector();
        }
    }
}
