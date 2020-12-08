package com.cy.view;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.cy.app.UtilContext;
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


	/**只影响颜色，不影响shape等其他设置
	 * @param view
	 * @param color
	 */
	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public static void setBgColorIgnoreLessShape(View view,@ColorInt int color){
		view.setBackgroundTintList(ColorStateList.valueOf(color));
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

	public static View inflate(@LayoutRes int layout){
		View view=LayoutInflater.from(UtilContext.getContext()).inflate(layout,null);
		return view;
	}

	/**
	 * 检测制定View是否被完全遮住
	 * @return
	 */
	public static boolean isFullyInvisable(View view) {
		boolean cover = false;
		Rect rect = new Rect();
		cover = view.getGlobalVisibleRect(rect);
		return !cover;
	}
}
