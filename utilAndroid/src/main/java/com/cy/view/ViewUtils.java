package com.cy.view;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class ViewUtils {

    /**
     * @param tip
     * @param context
     * @param start
     * @param ended
     *            高亮“哈哈温馨提示：” 2-7
     * @param R_color
     */
    public static final void highlightTip(TextView tip, Context context, int start, int ended, int R_color) {
        CharSequence str = tip.getText();
        if (str.length() < 5) {
            return;
        }
        int color = context.getResources().getColor(R_color);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(1.3f);
        SpannableString text = new SpannableString(str);
        text.setSpan(colorSpan, start, ended, 0);
        text.setSpan(sizeSpan, start, ended, 0);
        tip.setText(text);
    }

    /**
     * 将dip转换成px<br>
     * tips:<br>
     * 1、getDimension()函数会自动识别参数为px或dp、sp，如果为后两者，则自动计算为px，无需用转换函数二次转转。<br>
     * 2、getDimensionPixelOffset ()函数不不会自动计算，需要用转换函数二次转换。<br>
     * 3、eg: int margin=(int)getResources().getDimension(R.dimen.layout_margin);
     */
    public static final int getPixByDip(float dip, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) (dip * displayMetrics.density);
    }
}
