package com.cy.view.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class ObserveScrollView extends ScrollView {
  
    private ScrollListener mListener;    
  
    public static interface ScrollListener {//声明接口，用于传递数据  
        public void scrollOritention(int l, int t, int oldl, int oldt);  
    }  
  
    public ObserveScrollView(Context context, AttributeSet attrs,
                             int defStyle) {
        super(context, attrs, defStyle);  
        // TODO Auto-generated constructor stub  
    }  
  
    public ObserveScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);  
        // TODO Auto-generated constructor stub  
    }  
  
    public ObserveScrollView(Context context) {
        super(context);  
        // TODO Auto-generated constructor stub  
    }  
  
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {  
        // TODO Auto-generated method stub  
        super.onScrollChanged(l, t, oldl, oldt);  
        if (mListener != null) {  
            mListener.scrollOritention(l, t, oldl, oldt);  
        }  
    }  
  
    public void setScrollListener(ScrollListener l) {  
        this.mListener = l;  
    }  
  
}  