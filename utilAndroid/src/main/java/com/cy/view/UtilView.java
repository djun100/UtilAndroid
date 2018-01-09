package com.cy.view;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.cy.io.Log;


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

	public static void stateLizeBtnRes(Button view, @ColorRes int bgColor, @ColorRes int bgColorDisabled,
									   @ColorRes int txtColor, @ColorRes int txtColorDisabled,
									   boolean isRippleColorDeep, int roundRadius){
		Resources resources=view.getResources();
		stateLizeBtnInt(view,resources.getColor(bgColor),resources.getColor(bgColorDisabled)
				,resources.getColor(txtColor),resources.getColor(txtColorDisabled),isRippleColorDeep,roundRadius);
	}

	/**view添加是否按下可用状态效果
	 * @param bgColor button背景色,颜色的resource id ,not color value
	 * @param bgColorDisabled disable状态背景色 resource id ,not color value
	 * @param isRippleColorDeep ripple效果颜色是否是在原颜色上加深
	 * @param roundRadius 背景圆角弧度*/
	public static void stateLizeBtnInt(Button view, @ColorInt int bgColor, @ColorInt int bgColorDisabled,
									   @ColorInt int txtColor, @ColorInt int txtColorDisabled,
									   boolean isRippleColorDeep, int roundRadius){
		if (view==null){
			Log.e("view cannot be null,ripple error!");
			return;
		}
		if (view instanceof Button){
			//去掉圆角时的矩形阴影
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				view.setStateListAnimator(null);
			}
		}
		roundRadius=roundRadius>0?roundRadius:0;

		StateListDrawable stateListDrawable=new StateListDrawable();

		GradientDrawable normalDrawable = new GradientDrawable();
		normalDrawable.setColor(bgColor);
		normalDrawable.setCornerRadius(roundRadius);

		GradientDrawable disabledDrawable = new GradientDrawable();
		disabledDrawable.setColor(bgColorDisabled);
		disabledDrawable.setCornerRadius(roundRadius);

		GradientDrawable colorDownDrawable = new GradientDrawable();
		colorDownDrawable.setColor(UtilColor.colorLightDown(bgColor));
		colorDownDrawable.setCornerRadius(roundRadius);

		GradientDrawable colorUpDrawable = new GradientDrawable();
		colorUpDrawable.setColor(UtilColor.colorLightUp(bgColor));
		colorUpDrawable.setCornerRadius(roundRadius);

		stateListDrawable.addState(new int[]{-android.R.attr.state_enabled}, disabledDrawable);//
		if (isRippleColorDeep){
			stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, colorDownDrawable);
		}else{
			stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, colorUpDrawable);
		}
		stateListDrawable.addState(new int[]{}, normalDrawable);//

		view.setBackgroundDrawable(stateListDrawable);
		stateLizeBtnTxtColorInt(view, txtColor, txtColorDisabled);
	}

	private static void  stateLizeBtnTxtColorInt(Button view, @ColorInt int txtColor, @ColorInt int txtColorDisabled){
		int[][] states = new int[][] {
				new int[] { android.R.attr.state_enabled}, // enabled
				new int[] {-android.R.attr.state_enabled}, // disabled
//				new int[] {-android.R.attr.state_checked}, // unchecked
//				new int[] { android.R.attr.state_pressed}  // pressed
		};

		int[] colors = new int[] {
				txtColor,
				txtColorDisabled

//				Color.GREEN,
//				Color.BLUE
		};

		ColorStateList colorList = new ColorStateList(states, colors);

		view.setTextColor(colorList);
	}

	/**
	 * @deprecated see {@link UtilViewStyle}
	 * @param view
	 * @param roundRadius
	 * @param fillColor
	 */
	public static void setBg(View view,
							 int roundRadius,
							 @ColorInt int fillColor){

		setBg(view, roundRadius, -1, -1, fillColor,false);
	}

	/**
	 * @deprecated see {@link UtilViewStyle}
	 * @param view
	 * @param roundRadius
	 * @param strokeColor
	 * @param strokeWidth
	 */
	public static void setBg(View view,
							 int roundRadius,
							 @ColorInt int strokeColor,int strokeWidth){

		setBg(view, roundRadius, strokeColor, strokeWidth, -1,false);
	}

	/**
	 * @deprecated see {@link UtilViewStyle}
	 * @param view
	 * @param isRadiusHalfHeight
	 * @param strokeColor
	 * @param strokeWidth
	 * @param fillColor
	 */
	public static void setBg(View view,
							 boolean isRadiusHalfHeight,
							 @ColorInt int strokeColor,int strokeWidth,
							 @ColorInt int fillColor){
		setBg(view, -1, strokeColor, strokeWidth, fillColor,isRadiusHalfHeight);
	}

	/**
	 * @deprecated see {@link UtilViewStyle}
	 * @param view
	 * @param roundRadius
	 * @param strokeColor
	 * @param strokeWidth
	 * @param fillColor
	 */
	public static void setBg(View view,
							 int roundRadius,
							 @ColorInt int strokeColor,int strokeWidth,
							 @ColorInt int fillColor){
		setBg(view, roundRadius, strokeColor, strokeWidth, fillColor, false);
	}

	/**
	 * @deprecated see {@link UtilViewStyle}
	 * @param view
	 * @param roundRadius
	 * @param strokeColor
	 * @param strokeWidth
	 * @param fillColor
	 * @param isRadiusHalfHeight
	 */
	public static void setBg(View view,
							 int roundRadius,
							 @ColorInt int strokeColor,int strokeWidth,
							 @ColorInt int fillColor,
							 boolean isRadiusHalfHeight){
		GradientDrawable gd = new GradientDrawable();//创建drawable
		gd.setColor(fillColor);
		if (isRadiusHalfHeight) {
			gd.setCornerRadius(view.getHeight()/2);
		}else {
			gd.setCornerRadius(roundRadius);
		}
		if (strokeWidth>0) {
			gd.setStroke(strokeWidth, strokeColor);
		}
		view.setBackgroundDrawable(gd);
	}
}
