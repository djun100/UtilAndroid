package com.cy.view;

import android.support.v7.widget.RecyclerView;

public class UtilRecyclerView {
    /*
    判断是否滑到底了
     */
    protected boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent()
                + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange()){
            return true;
        }
        return false;
    }

}
