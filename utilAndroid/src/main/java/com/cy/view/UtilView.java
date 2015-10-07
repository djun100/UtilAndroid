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
	/**EditText 只允许输入金额</br>
	 * xml需要另外配置：</br>
                android:digits="1234567890."
                android:inputType="numberDecimal"</br>                
 */
public static void etSetInputTypeMoney(EditText et){
	    et.addTextChangedListener(new TextWatcher() {
          
          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {
          }
          
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {
          }

          @Override
          public void afterTextChanged(Editable s) {
              if (s.length() > 0) {
                  int pos = s.length() - 1;
                  char c = s.charAt(pos);
                  //输入的第一位不能为.
                  if (s.toString().indexOf('.') == 0){
                      s.delete(pos, pos + 1);                        
                  }
                  //前两位不能为00
                  if (s.length()==2 && s.charAt(0)=='0' && c != '.'){
                      s.delete(pos, pos + 1);                        
                  }
                  //不能输入第二个.
                  if (c == '.' && s.toString().indexOf('.') != s.toString().lastIndexOf('.')) {
                      s.delete(pos, pos + 1);
                  }
                  //.后最多有两位
                  if (s.toString().indexOf('.') != -1 && s.toString().indexOf('.') == s.length() - 4) {
                      s.delete(pos, pos + 1);
                  }
              }
          }
      });
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
