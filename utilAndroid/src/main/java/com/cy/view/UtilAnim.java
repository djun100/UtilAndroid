package com.cy.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

public class UtilAnim {
    private View view;
    private float[] factors1;
    private float[] factors2;
    private float[] factors3;
    private long duration;
    private long delay;
    private AnimatorEndListener listener;
    private String attr1;
    private String attr2;
    private String attr3;
    private int pivotGravity;

    private UtilAnim() {
    }

    public UtilAnim setView(View view) {
        this.view = view;
        return this;
    }

    public UtilAnim setFactors1(float[] factors1) {
        this.factors1 = factors1;
        return this;
    }

    public UtilAnim setFactors2(float[] factors2) {
        this.factors2 = factors2;
        return this;
    }

    public UtilAnim setFactors3(float[] factors3) {
        this.factors3 = factors3;
        return this;
    }

    public UtilAnim setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    public UtilAnim setDelay(long delay) {
        this.delay = delay;
        return this;
    }

    public UtilAnim setListener(AnimatorEndListener listener) {
        this.listener = listener;
        return this;
    }

    public UtilAnim setAttr1(String attr1) {
        this.attr1 = attr1;
        return this;
    }

    public UtilAnim setAttr2(String attr2) {
        this.attr2 = attr2;
        return this;
    }

    public UtilAnim setAttr3(String attr3) {
        this.attr3 = attr3;
        return this;
    }

    public UtilAnim setPivotGravity(int pivotGravity) {
        this.pivotGravity = pivotGravity;
        return this;
    }

    public UtilAnim start() {
        if (pivotGravity == Gravity.CENTER) {
            view.setPivotX(view.getWidth() / 2);
            view.setPivotY(view.getHeight() / 2);
        }

        ObjectAnimator attr1Anim = null;
        if (!TextUtils.isEmpty(attr1)) {
            attr1Anim = ObjectAnimator.ofFloat(view, attr1, factors1);
        }

        ObjectAnimator attr2Anim = null;
        if (!TextUtils.isEmpty(attr2)) {
            attr2Anim = ObjectAnimator.ofFloat(view, attr2, factors2 == null ? factors1 : factors2);
        }

        ObjectAnimator attr3Anim = null;
        if (!TextUtils.isEmpty(attr3)) {
            attr3Anim = ObjectAnimator.ofFloat(view, attr3, factors3 == null ? factors1 : factors3);
        }

        AnimatorSet animSet = new AnimatorSet();
        if (attr2Anim == null && attr3Anim == null) {
            animSet.playTogether(attr1Anim);
        } else if (attr3Anim == null) {
            animSet.playTogether(attr1Anim, attr2Anim);
        } else {
            animSet.playTogether(attr1Anim, attr2Anim, attr3Anim);
        }
        animSet.setDuration(duration);
        animSet.setStartDelay(delay);
        if (listener != null) {
            animSet.addListener(listener);
        }
        animSet.start();
        return this;
    }

    public static UtilAnim newInstance() {
        return new UtilAnim();
    }
}
