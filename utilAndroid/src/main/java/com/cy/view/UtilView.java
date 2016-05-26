package com.cy.view;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

public class UtilView {
	/**view获取焦点
	 * @param v
	 */
	public static void setFocus(View v){
		v.setFocusable(true);
		v.setFocusableInTouchMode(true);
		v.requestFocus();
//		v.requestFocusFromTouch();
	}


	static long timeLast = 0;

	/**
	 * 是否为快速调用
	 * 
	 * @param last
	 *            快速的时长依据标准
	 * @return
	 */
	public boolean isFastCall(long last) {
		long time = System.currentTimeMillis();
		if (time - timeLast > last) {// 不是快速调用
			timeLast = time;
			return false;
		}
		return true;
	}
	/**TODO 此函数算法严重耗时，measure与getview为主要耗时地方，总平均1.5s左右，致item过多时UI卡死
	 * 动态设置listview的高度，适用上下两个listview布局
	 * 
	 * @param listView
	 */
	public void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}
}
