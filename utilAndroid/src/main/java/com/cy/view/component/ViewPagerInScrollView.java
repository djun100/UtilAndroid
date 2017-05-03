package com.cy.view.component;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

/**
 * Created by wangxuechao on 2017/5/2.
 */

public class ViewPagerInScrollView extends ViewPager{

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
                ajustSelfHeight();
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
                ajustSelfHeight();
            }
        });
    }

    public void ajustSelfHeight() {
        final int w = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        final int h = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        View view = null;

//        View view = getChildAt(getCurrentItem());//获取的view是错误的，还有可能为空
        if (getAdapter() instanceof FragmentStatePagerAdapter){
            view=((FragmentStatePagerAdapter) getAdapter()).getItem(getCurrentItem()).getView();
        }else if (getAdapter() instanceof FragmentStatePagerAdapter){
            view=((FragmentPagerAdapter) getAdapter()).getItem(getCurrentItem()).getView();
        }
        if (view!=null) {
            view.measure(w, h);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.height = view.getMeasuredHeight();
            setLayoutParams(params);
        }
    }
}
