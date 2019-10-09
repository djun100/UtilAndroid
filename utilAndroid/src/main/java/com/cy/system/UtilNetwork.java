package com.cy.system;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.NetworkRequest;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.annotation.RequiresPermission;
import android.telephony.TelephonyManager;

import com.cy.app.UtilContext;
import com.cy.io.KLog;
import com.cy.view.UtilToast;

import org.greenrobot.eventbus.EventBus;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 网络工具类
 */
public final class UtilNetwork {

    /** <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
     * 判断网络是否已连接
     *
     * @return 是否已联网
     */
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) UtilContext.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (null != networkInfo && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    /** <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
     * 检测当前打开的网络类型是否WIFI
     *
     * @param context 上下文
     * @return 是否是Wifi上网
     */
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isWifiOpened(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /** <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
     * @return
     */
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    private boolean isWIFIConnected() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) UtilContext.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivityManager) {
            NetworkInfo networkInfo = connectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);//判断网络类型是否是3G时，用TYPE_MOBILE即可
            if (null != networkInfo && networkInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断是否是手机网络，非wifi
     * @param context
     * @return
     */
    public static boolean isMobileNetWork(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkINfo = cm.getActiveNetworkInfo();
        if (networkINfo != null && networkINfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }

        /**
         * IP地址校验
         *
         * @param ip 待校验是否是IP地址的字符串
         * @return 是否是IP地址
         */
    public static boolean isIP(String ip) {
        Pattern pattern = Pattern
                .compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    /**
     * IP转化成int数字
     *
     * @param addr IP地址
     * @return Integer
     */
    public static int ipToInt(String addr) {
        String[] addrArray = addr.split("\\.");
        int num = 0;
        for (int i = 0; i < addrArray.length; i++) {
            int power = 3 - i;
            num += ((Integer.parseInt(addrArray[i]) % 256 * Math
                    .pow(256, power)));
        }
        return num;
    }

    /**
     * 获取URL中参数 并返回Map
     * @param url
     * @return
     */
    public static Map<String, String> getUrlParams(String url) {
        Map<String, String> params = null;
        try {
            String[] urlParts = url.split("\\?");
            if (urlParts.length > 1) {
                params = new HashMap<>();
                String query = urlParts[1];
                for (String param : query.split("&")) {
                    String[] pair = param.split("=");
                    String key = URLDecoder.decode(pair[0], "UTF-8");
                    String value = "";
                    if (pair.length > 1) {
                        value = URLDecoder.decode(pair[1], "UTF-8");
                    }
                    params.put(key, value);
                }
            }
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return params;
    }

    /**
     * 是否是网络链接
     * @param url
     * @return
     */
    public static boolean isUrl(String url) {
        try {
            URL url1 = new URL(url);
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean isNetWorkListenerRegistered;
    private static ConnectivityManager.NetworkCallback sNetworkCallback;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void listenNetWorkChanged() {
        if (isNetWorkListenerRegistered ==false){
            isNetWorkListenerRegistered = true;
            final ConnectivityManager cm = (ConnectivityManager) UtilContext.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            cm.requestNetwork(new NetworkRequest.Builder().build(), getNetworkCallback());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void unListenNetWorkChanged() {
        if (isNetWorkListenerRegistered) {
            isNetWorkListenerRegistered = false;
            final ConnectivityManager cm = (ConnectivityManager) UtilContext.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            try {
                cm.unregisterNetworkCallback(getNetworkCallback());
            } catch (Exception e) {
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static ConnectivityManager.NetworkCallback getNetworkCallback(){
        if (sNetworkCallback == null){
            sNetworkCallback = new ConnectivityManager.NetworkCallback() {
                @Override
                public void onLost(Network network) {
                    super.onLost(network);
                    ///网络不可用的情况下的方法
                }

                @Override
                public void onAvailable(Network network) {
                    super.onAvailable(network);
                    ///网络可用的情况下的方法
                    final ConnectivityManager cm = (ConnectivityManager) UtilContext.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo info = cm.getActiveNetworkInfo();
                    // WiFi 连接
                    if (info != null && info.getType() == ConnectivityManager.TYPE_WIFI) {
                    }
                    // 手机信号连接
                    else if (info != null && info.getType() == ConnectivityManager.TYPE_MOBILE) {
                    }
                }
            };
        }
        return sNetworkCallback;
    }
}