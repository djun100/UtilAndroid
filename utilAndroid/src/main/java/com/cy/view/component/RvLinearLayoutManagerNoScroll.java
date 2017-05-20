package com.cy.view.component;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 *  RvLinearLayoutManagerNoScroll linearLayoutManager = new RvLinearLayoutManagerNoScroll(mContext);
 linearLayoutManager.setScrollEnabled(false);
 mRv.setLayoutManager(linearLayoutManager);
 */
public class RvLinearLayoutManagerNoScroll extends LinearLayoutManager {
    private boolean isScrollEnabled = true;

    public RvLinearLayoutManagerNoScroll(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }
}