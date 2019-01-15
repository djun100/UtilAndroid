package com.cy.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.cy.security.UtilMD5;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/12/15.
 */

public class UtilApp {
    /**
     * 安装apk
     *
     * @param file
     */
    protected static void installApk(Context context, File file) {
        Intent intent = new Intent();
        // 执行动作
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//如果不加，最后安装完成，点打开，无法打开新版本应用。
        // 执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());//如果不加，最后不会提示完成、打开。
    }

    /**获取进程名
     * @return
     */
    public static String getProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 启动App
     */
    public static void launchApp(String packageName) {
        Context context= UtilContext.getContext();
        // 判断是否安装过App，否则去市场下载
        if (isAppInstalled(packageName)) {
            Intent intent=context.getPackageManager().getLaunchIntentForPackage(packageName);
            if (intent!=null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        } else {
            goToMarket(context, packageName);
        }
    }

    /**
     * 检测某个应用是否安装
     *
     * @param packageName
     * @return
     */
    public static boolean isAppInstalled(String packageName) {
        try {
            UtilContext.getContext().getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 去市场下载页面
     */
    public static void goToMarket(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
        }
    }

    /**将context应用页面bring到前台
     * 需要权限：<p>
     <uses-permission android:name="android.permission.GET_TASKS"/>
     <uses-permission android:name="android.permission.INTERACT_ACROSS_USERS_FULL"/>
     <uses-permission android:name="android.permission.GET_TOP_ACTIVITY_INFO"/>
     <uses-permission android:name="android.permission.REORDER_TASKS"/>
     * Android实现微信、QQ的程序前后台切换   http://www.ithtw.com/5684.html
     */
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public static void bringToFront(Class defaultActToLaunch){
        Context context= UtilContext.getContext();
        //获取ActivityManager
        ActivityManager mAm = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        //获得当前运行的task
        List<ActivityManager.RunningTaskInfo> taskList = mAm.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo rti : taskList) {
            //找到当前应用的task，并启动task的栈顶activity，达到程序切换到前台
            if(rti.topActivity.getPackageName().equals(context.getPackageName())) {
                mAm.moveTaskToFront(rti.id,0);
                return;
            }
        }
        //若没有找到运行的task，用户结束了task或被系统释放，则重新启动mainactivity
        Intent resultIntent = new Intent(context, defaultActToLaunch);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(resultIntent);
    }

    /**app退到后台
     * @param activity
     */
    public static void gotoBackGround(Activity activity){
        Intent intent= new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        activity.startActivity(intent);
    }

    /**    public static Snackbar showToAppDetailSettingsSnackBar(final Context context, View view, int resId) {
     Snackbar snackbar = Snackbar.make(view, resId, Snackbar.LENGTH_LONG).setAction("去设置", new View.OnClickListener() {
    @Override
    public void onClick(View v) {
    Uri uri = Uri.parse("package:" + context.getPackageName());
    Intent mIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri);
    context.startActivity(mIntent);
    }
    });
     snackbar.show();
     return snackbar;
     }
     使用：
     mSnackbar = DialogManager.showToAppDetailSettingsSnackBar(this, findViewById(R.id.root) ,R.string.storage_permission_not_granted);
     * @param activity
     */
    public static void goAppDetailSetting(Activity activity){
        Uri uri = Uri.parse("package:" + activity.getPackageName());
        Intent mIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri);
        activity.startActivity(mIntent);
    }

    /**
     * 返回当前程序版本名 android:versionName="flyTV 0.5.0"
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        int versioncode;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            versioncode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    public static String getVersionName(Activity context) throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(
                context.getPackageName(), 0);
        String version = packInfo.versionName;
        return version;
    }

    private static Signature getSelfSignature(){
        try {
            PackageInfo packageInfo = UtilContext.getContext().getPackageManager().getPackageInfo(UtilContext.getContext().getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            return signs[0];
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**得到本app签名md5值
     * @return
     */
    public static String getSignatureMD5(){
        String signMd5 = UtilMD5.getMessageDigest(getSelfSignature().toByteArray());
        return signMd5;
    }

    public static HashMap<String ,String> getSignatureInfo() {
        HashMap<String,String> hashMap=new HashMap<String,String>();
        try {
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) certFactory
                    .generateCertificate(new ByteArrayInputStream(getSelfSignature().toByteArray()));
            String pubKey = cert.getPublicKey().toString();
            String signNumber = cert.getSerialNumber().toString();
//			com.cy.app.Log.writeW("signName:" + cert.getSigAlgName());//算法名称
//			com.cy.app.Log.writeW("pubKey:" + pubKey);//很长的一串公钥
//			com.cy.app.Log.writeW("signNumber:" + signNumber);//签名序列号
//			com.cy.app.Log.writeW("subjectDN:"+cert.getSubjectDN().toString());//所有者信息
            hashMap.put("algName",cert.getSigAlgName());//算法名称
            hashMap.put("pubKey",pubKey);//很长的一串公钥
            hashMap.put("serialNumber",signNumber);//签名序列号
            hashMap.put("subjectDN",cert.getSubjectDN().toString());//所有者信息
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        return hashMap;
    }

}
