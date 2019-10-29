package com.cy.container;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.cy.data.UtilArray;
import com.cy.data.UtilDate;
import com.cy.io.Log;
import com.cy.utilandroid.BuildConfig;

/**
 * 全局动画主题： manifest android:theme="@style/Theme.NoTitleBar.right_in_right_out"
 * com.cy.app.App
 */
public class BaseApp extends Application implements Thread.UncaughtExceptionHandler {
	public static BaseApp mInstance;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		if (!BuildConfig.DEBUG) {
			SafeLooper.install();
			SafeLooper.setUncaughtExceptionHandler(this);
			Thread.setDefaultUncaughtExceptionHandler(this);			
		}
	}

	public void uncaughtException(Thread thread, Throwable ex) {
		if (!BuildConfig.DEBUG){
			// TODOcy: 2018/10/5
//		UtilMail.sendCrashEmail(this, thread, ex);
		}else {
			logCrash(mInstance,thread,ex);
		}
	}

	private static void logCrash(Context context, Thread thread, Throwable ex) {
		String crashMessage = "Crashed in " + thread + "\n\n" + ex.getMessage();
		StackTraceElement[] traces = ex.getStackTrace();
		Toast.makeText(context, "程序异常", Toast.LENGTH_SHORT).show();
		Log.e("crashlog-" + UtilDate.getDateStrNow(null)
				+ "\n"
				+ crashMessage
				+ "\n"
				+ UtilArray.toString(traces));
	}
}
