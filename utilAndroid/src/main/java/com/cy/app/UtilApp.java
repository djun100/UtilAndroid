package com.cy.app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

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
}
