package com.cy.view;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by Administrator on 2016/4/5.
 */
public class UMoneyEditText {
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
}
