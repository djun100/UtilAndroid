package com.cy.view.component;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

/**正常情况下scrollview 嵌套viewpager会不显示
 * Created by wangxuechao on 2017/5/2.
 */

public class ViewPagerInScrollView extends ViewPager {

    public ViewPagerInScrollView(Context context) {
        super(context);
        init();
    }

    public ViewPagerInScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        addOnGlobalLayoutListener();
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                onGlobalLayoutListen();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addOnGlobalLayoutListener(){
        final View v=((Activity) getContext()).getWindow().getDecorView();
        ViewTreeObserver vto =v.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                v.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                onGlobalLayoutListen();
            }
        });
    }

    private void onGlobalLayoutListen() {
        final int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

        View view = getChildAt(getCurrentItem());
        view.measure(w, h);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.height = view.getMeasuredHeight();
        setLayoutParams(params);
    }
}
