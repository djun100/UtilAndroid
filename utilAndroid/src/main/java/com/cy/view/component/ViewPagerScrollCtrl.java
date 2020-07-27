package com.cy.view.component;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ViewPagerScrollCtrl extends ViewPager {

    private boolean canSlide = true;
    private boolean forbidSwipeLeft = false;

    public ViewPagerScrollCtrl(Context context) {
        this(context, null);
    }

    public ViewPagerScrollCtrl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    float beforeX = 0;

    //-----禁止左滑-------左滑：上一次坐标 > 当前坐标
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(!forbidSwipeLeft){
            return super.dispatchTouchEvent(ev);
        }else  {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN://按下如果‘仅’作为‘上次坐标’，不妥，因为可能存在左滑，motionValue大于0的情况（来回滑，只要停止坐标在按下坐标的右边，左滑仍然能滑过去）
                    beforeX = ev.getX();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (ev.getX() < beforeX) {//禁止左滑
                        return true;
                    }
                    beforeX = ev.getX();//手指移动时，再把当前的坐标作为下一次的‘上次坐标’，解决上述问题

                    break;
                default:
                    break;
            }
            return super.dispatchTouchEvent(ev);
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.canSlide && super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.canSlide && super.onTouchEvent(event);
    }

    public void setCanSlide(boolean b) {
        canSlide = b;
        forbidSwipeLeft = false;
    }

    public void setForbidSwipeLeft(boolean forbid) {
        forbidSwipeLeft = forbid;
        canSlide = true;

    }


}