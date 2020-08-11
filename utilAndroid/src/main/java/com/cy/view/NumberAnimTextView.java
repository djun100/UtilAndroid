package com.cy.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * 数字增加动画的　TextView
 * @author wangxuechao
 */
@SuppressLint("AppCompatCustomView")
public class NumberAnimTextView extends TextView {

    /**
     * 起始值 默认 0
     */
    private float mNumStart;
    /**
     * 结束值
     */
    private float mNumEnd;
    /**
     * 动画总时间 默认 2000 毫秒
     */
    private long mDuration = 2000;
    /**
     * 前缀
     */
    private String mPrefixString = "";
    /**
     * 后缀
     */
    private String mPostfixString = "";
    /**
     * 是否开启动画
     */
    private boolean mIsEnableAnim = true;
    private ValueAnimator animator;
    private DecimalFormat formater = new DecimalFormat("0");

    public NumberAnimTextView(Context context) {
        super(context);
    }

    public NumberAnimTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NumberAnimTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setNumber(float number) {
        setNumber(0, number);
    }

    public void setNumber(float numberStart, float numberEnd) {
        if (mNumStart==numberStart && mNumEnd==numberEnd){
            setText(mPrefixString + formater.format(numberEnd) + mPostfixString);
            return;
        }
        mNumStart = numberStart;
        mNumEnd = numberEnd;
        start();
    }

    public void setEnableAnim(boolean enableAnim) {
        mIsEnableAnim = enableAnim;
    }

    public void setDuration(long mDuration) {
        this.mDuration = mDuration;
    }

    public void setPrefixString(String mPrefixString) {
        this.mPrefixString = mPrefixString;
    }

    public void setPostfixString(String mPostfixString) {
        this.mPostfixString = mPostfixString;
    }

    public void setFormat(String pattern) {
        formater = new DecimalFormat(pattern);
    }

    private float currValue;
    float jump=0;
    int jumped=0;
    private void start() {
        if (!mIsEnableAnim) {
            // 禁止动画
            setText(mPrefixString + formater.format(mNumEnd) + mPostfixString);
            return;
        }
        if (currValue!=0){
            animator = ValueAnimator.ofFloat(currValue,mNumEnd);
        }else {
            animator = ValueAnimator.ofFloat(mNumStart, mNumEnd);
        }
        animator.setDuration(mDuration);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                currValue = (float) valueAnimator.getAnimatedValue();
                //使数字变化看上去越来越慢
                if (currValue>mNumEnd*0.75) {
                    if (jumped<jump) {
//                        Log.i("tag", "anim trace -->跳过该帧 jump:"+jump+" jumped:"+jumped);
                        jumped++;
                    }else {
                        // TODO_cy: 2020/8/10 待尝试改为指数级增加
                        jump+=currValue/mNumEnd;
                        jumped=0;
                        setText();
//                        Log.i("tag", "anim trace -->show currValue："+currValue);
                    }
                }else {
                    setText();
                }
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
//                Log.i("tag", "trace --> text anim end");
                setText();
            }
        });

        if (animator.isPaused()) {
            animator.resume();
        }else {
            animator.start();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (animator != null) {
            animator.cancel();
        }
    }

    private void setText(){
        if (!TextUtils.isEmpty(mPrefixString) || !TextUtils.isEmpty(mPostfixString)) {
            setText(mPrefixString + formater.format(currValue) + mPostfixString);
        }else {
            setText(formater.format(currValue));
        }
    }
}