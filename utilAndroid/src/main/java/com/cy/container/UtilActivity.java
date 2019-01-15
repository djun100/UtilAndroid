package com.cy.container;

import android.app.Activity;
import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;

import com.cy.app.UtilContext;

public class UtilActivity {
    /**intent是否可以成功跳转，是否有目标组件接收
     * @param intent
     * @return
     */
    public boolean isIntentAvailable(Intent intent) {
        if (intent.resolveActivity(UtilContext.getContext().getPackageManager()) != null) {
            //存在
            return true;
        } else {
            //不存在
            return false;
        }
    }

    public static  void setFullScreen(Activity activity){
        //隐去标题栏（应用程序的名字）
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐去状态栏部分(电池等图标和一切修饰部分)
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
