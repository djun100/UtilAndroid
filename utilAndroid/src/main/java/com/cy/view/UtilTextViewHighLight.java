package com.cy.view;

import android.support.annotation.ColorInt;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

public class UtilTextViewHighLight {

    /**
     * @param tip
     * @param start
     * @param ended
     *            高亮“哈哈温馨提示：” 2-7
     */
    public static final void highlightTip(TextView tip, int start, int ended, @ColorInt int color) {
        CharSequence str = tip.getText();

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(1.3f);
        SpannableString text = new SpannableString(str);
        text.setSpan(colorSpan, start, ended, 0);
        text.setSpan(sizeSpan, start, ended, 0);
        tip.setText(text);
    }
}
