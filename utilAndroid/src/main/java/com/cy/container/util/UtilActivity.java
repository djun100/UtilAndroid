package com.cy.container.util;

import android.app.Activity;
import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;

import com.cy.app.UtilContext;
import com.cy.system.statusbar.UtilStatusBar;

public class UtilActivity {
    /**intent是否可以成功跳转，是否有目标组件接收
     * @param intent
     * @return
     */
    public boolean isIntentActExist(Intent intent) {
        return intent.resolveActivity(UtilContext.getContext().getPackageManager()) != null;
    }

    public static  void setFullScreen(Activity activity){
        //隐去标题栏（应用程序的名字）
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐去状态栏部分(电池等图标和一切修饰部分)
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static void setTransparentStatusBar(Activity activity){
        Window window = activity.getWindow();
        // 设置状态栏背景透明
        UtilStatusBar.transparentStatusBar(window);
    }

    public static void setMode(Activity activity,boolean darkMode){
        Window window = activity.getWindow();
        // 设置图标主题
        if (darkMode) {
            UtilStatusBar.setDarkMode(window);
        }else {
            UtilStatusBar.setLightMode(window);
        }
    }
}
