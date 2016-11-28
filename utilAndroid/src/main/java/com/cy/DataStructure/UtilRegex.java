package com.cy.DataStructure;

import java.util.regex.Pattern;

public class UtilRegex {
    /**
     * 判断string是否是2-4字的姓名
     */
    public static boolean isChineseName(String name) {
        String regx = "[\u4E00-\u9FA5]{2,4}";
        return Pattern.matches(regx, name);
    }
    /**
     * 匹配真实姓名(2~4个中文或者3~10个英文)
     */
    public static boolean isName(String name) {
        String regx = "(([\u4E00-\u9FA5]{2,4})|([a-zA-Z]{3,10}))";
        return Pattern.matches(regx, name);
    }

    /**是否全为空格
     * @param key
     * @return
     */
    public static boolean isAllSpace(String key){
        if (key==null||key.length()==0){
            return false;
        }
        return (key.matches("\\s+"));
    }
}
