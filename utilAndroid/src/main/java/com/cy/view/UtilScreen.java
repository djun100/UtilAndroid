package com.cy.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;

import com.cy.System.UtilEnv;
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

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(float dpValue) {
        final float scale = UtilContext.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static float pxToDp( int px) {
        return px / UtilContext.getContext().getResources().getDisplayMetrics().density;
    }

    @SuppressLint("NewApi")
    public static Point getScreenSize() {
        WindowManager windowManager= getWindowManager();
        Point size = new Point();
        if (false) {
//			if (Build.VERSION.SDK_INT >= 11) {
            try {
                size.x=windowManager.getDefaultDisplay().getWidth();
                size.y=windowManager.getDefaultDisplay().getHeight();
            } catch (NoSuchMethodError e) {
                Log.i("error", "it can't work");
            }

        } else {
            DisplayMetrics metrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(metrics);
            size.x = metrics.widthPixels;
            size.y = metrics.heightPixels;
        }
        Log.i("ScreenSize", "size.x=" + size.x + "  size.y=" + size.y);
        return size;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(float pxValue) {
        final float scale = UtilContext.getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(float pxValue) {
        final float fontScale =
                UtilContext.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(float spValue) {
        final float fontScale =
                UtilContext.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
    public static float getDensity(){
        final float scale = UtilContext.getContext().getResources().getDisplayMetrics().density;
        return scale;
    }

    /**打印屏幕长宽系列参数
     * @return
     */
    public static String printxyInfo(){
        int x= getScreenSize().x;
        int y= getScreenSize().y;
        int dpx= px2dp(x);
        int dpy= px2dp(y);
        float density= getDensity();
        String strOut="x:"+x+
                "  y:"+y+
                "  dpx:"+dpx+
                "  dpy:"+dpy+
                "  density:"+density;
        return strOut;
    }

    /**
     * @return WindowManager的实例，用于控制在屏幕上添加或移除悬浮窗。
     */
    private static WindowManager getWindowManager() {
        /** 用于控制在屏幕上添加或移除悬浮窗*/
        return (WindowManager) UtilContext.getContext().getSystemService(Context.WINDOW_SERVICE);
    }
}
