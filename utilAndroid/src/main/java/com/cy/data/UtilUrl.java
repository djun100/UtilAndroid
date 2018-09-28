package com.cy.data;

import android.text.TextUtils;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/18.
 */
public class UtilUrl {
    /**
     * 解析出url参数中的键值对
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     *
     * @param url url地址
     * @return url请求参数部分
     */
    public static Map<String, String> extractParamsMap(String url) {
        Map<String, String> mapRequest = new LinkedHashMap<String, String>();

        String[] arrSplit = null;

        String strUrlParam = extractParamsStr(url);
        if (strUrlParam == null) {
            return mapRequest;
        }
        //每个键值为一组
        arrSplit = strUrlParam.split("&");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("=");

            //解析出键值
            if (arrSplitEqual.length > 1) {
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

            } else {
                if (arrSplitEqual[0] != "") {
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }

    /**
     * 去掉url中的路径，留下请求参数部分
     *
     * @param strURL url地址
     * @return url请求参数部分
     */
    private static String extractParamsStr(String strURL) {
        if (TextUtils.isEmpty(strURL) || !strURL.contains("?")) return strURL;
        String strAllParam = null;
        String[] arrSplit = null;

        strURL = strURL.trim();

        arrSplit = strURL.split("\\?");
        if (strURL.length() > 1) {
            if (arrSplit.length > 1) {
                if (arrSplit[1] != null) {
                    strAllParam = arrSplit[1];
                }
            }
        }

        return strAllParam;
    }

    /**
     * 解析出url请求的路径，包括页面
     * @param strURL url地址
     * @return url路径
     */
    public static String extractUrlWithoutParams(String strURL)
    {
        String strPage=null;
        String[] arrSplit=null;

        strURL=strURL.trim();

        arrSplit=strURL.split("\\?");
        if(strURL.length()>0)
        {
            if(arrSplit.length>1)
            {
                if(arrSplit[0]!=null)
                {
                    strPage=arrSplit[0];
                }
            }
        }

        return strPage;
    }

    public static String removeParam(String url, String param){
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(param) || !url.contains(param)) return url;
        String urlPath=extractUrlWithoutParams(url);
        Map paramsMap=extractParamsMap(url);
        StringBuilder finalUrlBuilder=new StringBuilder(TextUtils.isEmpty(urlPath)?"":urlPath);
        for (Iterator<Map.Entry<String, String>> it = paramsMap.entrySet().iterator();
             it.hasNext();){
            Map.Entry<String, String> item = it.next();
            if (!item.getKey().equals(param)){
                if (!TextUtils.isEmpty(finalUrlBuilder)) {
                    finalUrlBuilder.append('&');
                }
                finalUrlBuilder.append(item.getKey())
                        .append('=')
                        .append(item.getValue());
            }
        }
        return finalUrlBuilder.toString();
    }
}
