package com.cy.view;

import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;


/**
 * Created by Administrator on 2016/10/27.
 */

public class UPopup {
    PopupWindow pop = null;
    View contentView;
    View anchorView;
    int xoff,yoff;

    public void initPopView_1(View contentView, View anchorView, int xoff, int yoff) {
        this.contentView = contentView;
        this.anchorView = anchorView;
        this.xoff=xoff;
        this.yoff=yoff;
        showPop_2();
    }

    /**
     * pop显示与隐藏状态互转
     **/
    public void showOrHidePop_2() {

        if (pop == null) {
            showPop_2();
        } else if (pop.isShowing()) {
            pop.dismiss();
        }else {
            showPop_2();
        }
    }


    /**
     * 弹出pop
     **/
    public void showPop_2() {

        if (pop == null||pop.getContentView()!=contentView) {
            if (pop!=null){
                pop.dismiss();
            }
            pop = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            pop.setBackgroundDrawable(new BitmapDrawable());
            pop.showAsDropDown(anchorView, xoff, yoff);
            // 需要设置一下此参数，点击外边可消失
            pop.setBackgroundDrawable(new BitmapDrawable());
            // 设置点击窗口外边窗口消失
            pop.setOutsideTouchable(true);
            // 设置此参数获得焦点，否则无法点击
            pop.setFocusable(false);
//            pop.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
            pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }else {
            pop.update(anchorView,xoff, yoff, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

    }

    /**
     * 隐藏pop
     */
    public void hidePop_2(){
        if (pop!=null){
            pop.dismiss();
        }
    }

}
