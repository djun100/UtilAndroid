package com.cy.security;

import android.text.TextUtils;
import android.util.Base64;

/**
 * 用flag Default编码后，字符串中带有+号和\n换行符，发起网络请求时，当以base64字符串作为参数值传递时，
 * url为了传输安全会把+号全部变成空格，在接收端就会产生各种问题，且前端用的部分的Base64解码库不支持\n，
 * 当字符串中含有\n时无法解码还原为图片。所以Base64编码时建议使用NO_WRAP | URL_SAFE，这样可以避免较多问题。
 */
public class UtilBase64 {

    /**
     * 会包含换行符\n，+号，末尾以“=”结束
     */
    public static final int DEFAULT = 0;
    /**
     * 会包含换行符\n，但是末尾去掉了“=”
     */
    public static final int NO_PADDING = 1;
    /**
     * 不包含换行符\n，+号，且字符串全在一行，设置此flag后CRLF flag无效
     */
    public static final int NO_WRAP = 2;
    /**
     * Win风格的换行符，意思就是使用CR LF这一对作为一行的结尾而不是Unix风格的LF
     */
    public static final int CRLF = 4;
    /**
     * 不使用对URL和文件名有特殊意义的字符来作为加密字符，具体就是以-和_取代+和/
     */
    public static final int URL_SAFE = 8;


    public static final int NO_CLOSE = 16;

    /**
     * @param tobeEncodedStr
     * @param flag 建议使用NO_WRAP | URL_SAFE
     * @return
     */
   public static String encode(String tobeEncodedStr,int flag){
       if (TextUtils.isEmpty(tobeEncodedStr)){
           return tobeEncodedStr;
       }
       return Base64.encodeToString(tobeEncodedStr.getBytes(),flag);
   }
}
