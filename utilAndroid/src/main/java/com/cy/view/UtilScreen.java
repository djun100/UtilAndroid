package com.cy.view;

import android.app.Activity;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;

import com.cy.app.UtilContext;

/**将dip转换成px<br>
 * tips:<br>
 * 1、getDimension()函数会自动识别参数为px或dp、sp，如果为后两者，则自动计算为px，无需用转换函数二次转转。<br>
 * 2、getDimensionPixelOffset ()函数不不会自动计算，需要用转换函数二次转换。<br>
 * 3、eg: int margin=(int)getResources().getDimension(R.dimen.layout_margin);
 * ScreenUtils
 * <ul>
 * <strong>Convert between dp and sp</strong>
 * </ul>
 * 
 */
public class UtilScreen {

    private UtilScreen() {
        throw new AssertionError();
    }

    /**dp值得到px值 dp(50): 50dp的像素
     * @param dp
     * @return
     */
    public static int dp(float dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,
                UtilContext.getContext().getResources().getDisplayMetrics());
    }

//    public static float dpToPx( float dp) {
//
//        return dp * UtilContext.getContext().getResources().getDisplayMetrics().density;
//    }

    public static float pxToDp( int px) {
        return px / UtilContext.getContext().getResources().getDisplayMetrics().density;
    }

    public static  void setFullScreen(Activity activity){
        //隐去标题栏（应用程序的名字）
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐去状态栏部分(电池等图标和一切修饰部分)
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

}
