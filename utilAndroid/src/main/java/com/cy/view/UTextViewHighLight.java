package com.cy.view;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

public class UTextViewHighLight {

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
}
