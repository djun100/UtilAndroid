package com.cy.view.component;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ViewPagerScrollCtrl extends ViewPager {
 
    private boolean canSlide = true;
 
    public ViewPagerScrollCtrl(Context context) {
        super(context);
    }
 
    public ViewPagerScrollCtrl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
 
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.canSlide && super.onTouchEvent(event);
    }
 
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.canSlide && super.onInterceptTouchEvent(event);
    }
 
    public void setCanSlide(boolean b) {
        this.canSlide = b;
    }}