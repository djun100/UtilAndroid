package com.cy.view;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.cy.app.UtilContext;

/**
 * ScreenUtils
 * <ul>
 * <strong>Convert between dp and sp</strong>
 * <li>{@link UtilScreen#dpToPx(float)}</li>
 * <li>{@link UtilScreen#pxToDp(float)}</li>
 * </ul>
 * 
 */
public class UtilScreen {

    private UtilScreen() {
        throw new AssertionError();
    }

    public static float dp(int px){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,px,
                UtilContext.getContext().getResources().getDisplayMetrics());
    }

    public static float dpToPx( float dp) {

        return dp * UtilContext.getContext().getResources().getDisplayMetrics().density;
    }

    public static float pxToDp( float px) {

        return px / UtilContext.getContext().getResources().getDisplayMetrics().density;
    }

    public static float dpToPxInt( float dp) {
        return (int)(dpToPx( dp) + 0.5f);
    }

    public static float pxToDpCeilInt( float px) {
        return (int)(pxToDp( px) + 0.5f);
    }
    /**
     * 将dip转换成px<br>
     * tips:<br>
     * 1、getDimension()函数会自动识别参数为px或dp、sp，如果为后两者，则自动计算为px，无需用转换函数二次转转。<br>
     * 2、getDimensionPixelOffset ()函数不不会自动计算，需要用转换函数二次转换。<br>
     * 3、eg: int margin=(int)getResources().getDimension(R.dimen.layout_margin);
     */
    public static final int getPixByDip(float dip) {
        DisplayMetrics displayMetrics = UtilContext.getContext().getResources().getDisplayMetrics();
        return (int) (dip * displayMetrics.density);
    }

    public static  void setFullScreen(Activity activity){
        //隐去标题栏（应用程序的名字）
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐去状态栏部分(电池等图标和一切修饰部分)
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    public static void showKeyboard(View view) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1 && view.hasFocus()) {
            view.clearFocus();
        }
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }

    public static void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
