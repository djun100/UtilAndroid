package com.cy.DataStructure;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * Created by cy on 2017/4/20.
 */

public class URandom {
    public static int getInt(int min,int max){
        int count = (int) (Math.random() * (max - min + 1)) + min;
        return count;
    }

    /**
     * 获取指定长度随机简体中文
     * @param len int
     * @return String
     */
    public static String getChinese(int len) {
        String ret="";
        for(int i=0;i<len;i++){
            String str = null;
            int hightPos, lowPos; // 定义高低位
            Random random = new Random();
            hightPos = (176 + Math.abs(random.nextInt(39))); //获取高位值
            lowPos = (161 + Math.abs(random.nextInt(93))); //获取低位值
            byte[] b = new byte[2];
            b[0] = (new Integer(hightPos).byteValue());
            b[1] = (new Integer(lowPos).byteValue());
            try
            {
                str = new String(b, "GBk"); //转成中文
            }
            catch (UnsupportedEncodingException ex)
            {
                ex.printStackTrace();
            }
            ret+=str;
        }
        return ret;
    }

    public static String getLetter(int len) {
        String str = "";
        for (int i = 0; i < len; i++) {
            str += (char) ((int) (Math.random() * 26) + 'a');
        }
        return str;
    }

    public static void main(String[] args) {
        System.out.println(getInt(1,3));
    }
}
