package com.cy.view;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by cy on 2017/8/14.
 */

public class UtilEdittext {

    /**﻿在页面刚开启后需要延时500-1000ms才生效，不是addOnGlobalLayoutListener能监视的。
     * 页面已开启就需要的话建议使用﻿getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
     * @param view
     */
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

    public static abstract class OnTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            onTextChanged(s.toString());
        }

        public abstract void onTextChanged(String after);
    }

    /**
     * 给edittext设置过滤器 过滤emoji
     *
     * @param et
     */
    public static void setEmojiFilter(EditText et) {
        et.setFilters(new InputFilter[]{new EmojiExcludeFilter()});
    }

    /**
     * https://stackoverflow.com/questions/22990870/how-to-disable-emoji-from-being-entered-in-android-edittext
     * */
    private static class EmojiExcludeFilter implements InputFilter {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                int type = Character.getType(source.charAt(i));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return "";
                }
            }
            return null;
        }
    }
}
