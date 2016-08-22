package com.cy.view.component;
import android.content.Context;  
import android.util.AttributeSet;  
import android.widget.GridView;  
  
/**  
* GridViewNoScroll适用于ScrollView与多个GridView嵌套的情况，否则GridView只会显示一行数据
* 有其他布局在上方的时候，可以使用sv.smoothScrollTo(0, 0);自动滚动到顶部
*/  
public class GridViewNoScroll extends GridView{  
  
     public GridViewNoScroll(Context context, AttributeSet attrs){  
          super(context, attrs);  
     }  
  
     public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){  
          int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);  
          super.onMeasure(widthMeasureSpec, mExpandSpec);  
     }  
}  
  