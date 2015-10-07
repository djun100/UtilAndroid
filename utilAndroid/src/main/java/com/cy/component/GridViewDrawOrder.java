package com.cy.component;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class GridViewDrawOrder extends GridView {
	String tag="GridViewDrawOrder";
	public static final int position_gridViewDrawOrder=0;
	public GridViewDrawOrder(Context context) {
		super(context);
		setChildrenDrawingOrderEnabled(true);
		// TODO Auto-generated constructor stub
	}
	public GridViewDrawOrder(Context context, AttributeSet attrs) {
		super(context, attrs);
		setChildrenDrawingOrderEnabled(true);
		// TODO Auto-generated constructor stub
	}
	public GridViewDrawOrder(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setChildrenDrawingOrderEnabled(true);
		// TODO Auto-generated constructor stub
	}



@Override
	protected int getChildDrawingOrder(int childCount, int i) {
		// TODO Auto-generated method stub
//    Log.v(tag, "childCount:"+childCount+"  点击position:"+ApplicationK.position_gridViewDrawOrder+"   当前遍历第"+i);
	/*	if (i==ApplicationK.position_gridViewDrawOrder) {
		return childCount-1;
	}
        return i + 1;*/
	if (i<position_gridViewDrawOrder) {
//		Log.v(tag, "该项绘画顺序："+i);
		return i;

	}else if(i>=position_gridViewDrawOrder){
//		Log.v(tag, "该项绘画顺序："+(i-1));

//		return i-1;
		return (childCount - 1 - i + position_gridViewDrawOrder);  
	}else{
		
	}
//	Log.v(tag, "该项绘画顺序："+(childCount-1));
//	return childCount-1;
	return i;

        //original
/*        if(i == childCount - 1){
            return 0;
        }
        return i + 1;*/
//		return super.getChildDrawingOrder(childCount, i);
		
	}

}
