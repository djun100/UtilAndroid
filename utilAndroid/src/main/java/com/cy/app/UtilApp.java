package com.cy.app;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;

import com.cy.io.Log;

import org.apache.commons.codec.Charsets;

import java.io.File;
import java.io.FileInputStream;
import java.lang.ref.WeakReference;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/12/15.
 */

public class UtilApp {
    public static boolean isAppForground() {
        return mActsStartedAndNotStopedSum > 0;
    }

    private static int mActsStartedAndNotStopedSum = 0;
    private static Application.ActivityLifecycleCallbacks mActivityLifecycleCallbacks;
    private static WeakReference<Activity> mAppTopActivityRef;

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static void registerActLifecycleCallback(Application application) {
        application.registerActivityLifecycleCallbacks(
                mActivityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
                    @Override
                    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

                    }

                    @Override
                    public void onActivityStarted(Activity activity) {
                        mActsStartedAndNotStopedSum++;
                        mAppTopActivityRef = new WeakReference(activity);
                    }

                    @Override
                    public void onActivityResumed(Activity activity) {

                    }

                    @Override
                    public void onActivityPaused(Activity activity) {

                    }

                    @Override
                    public void onActivityStopped(Activity activity) {
                        mActsStartedAndNotStopedSum--;
                    }

                    @Override
                    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                    }

                    @Override
                    public void onActivityDestroyed(Activity activity) {

                    }
                });
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static void unRegisterActivityLifecycleCallbacks(Application application) {
        application.unregisterActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
    }

    public static Activity getAppTopActivity(){
        return mAppTopActivityRef.get();
    }

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
    @SuppressLint("MissingPermission")
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

    public static String getPkgName(int pid) {
        String topProcessName = getProcessNameByPid(UtilContext.getContext(), pid);
        Log.d("processChanged. pid=" + pid + " pName=" + topProcessName);
        if (TextUtils.isEmpty(topProcessName)) {
            Log.d("start active pkg is null");
            return null;
        }
        int index = topProcessName.indexOf(":");
        if (index > 0) {
            topProcessName = topProcessName.substring(0, index);
        }
        return topProcessName;
    }

    private static String getProcessNameByPid(Context context, int pid) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List list = activityManager.getRunningAppProcesses();
        Iterator i = list.iterator();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pid) {
                    // 根据进程的信息获取当前进程的名字
                    return info.processName;
                }
            } catch (Exception e) {
                Log.d("getAppNameByPid:", e);
            }
        }
        return "";
    }

    /**
     * 返回当前的进程名
     * @return
     */
    public static String getCurrentProcessName() {
        FileInputStream in = null;
        try {
            String fn = "/proc/self/cmdline";
            in = new FileInputStream(fn);
            byte[] buffer = new byte[256];
            int len = 0;
            int b;
            while ((b = in.read()) > 0 && len < buffer.length) {
                buffer[len++] = (byte) b;
            }
            if (len > 0) {
                return new String(buffer, 0, len, Charset.forName("utf-8"));
            }
        } catch (Throwable e) {
        } finally {
//            IoStreamUtils.closeSilently(in);
        }
        return null;
    }

    public static boolean isMainProcess() {
        String processname = getCurrentProcessName();
        return !TextUtils.isEmpty(processname) && UtilContext.getContext().getPackageName().equals(processname);
    }
}
