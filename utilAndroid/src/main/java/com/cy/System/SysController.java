package com.cy.System;

import android.app.Activity;
import android.content.Context;
import android.os.PowerManager;
import android.view.Window;
import android.view.WindowManager;

/**<
 * uses-permission android:name="android.permission.WAKE_LOCK" /> 
 * // in onResume() call
mWakeLock.acquire(); 
// in onPause() call
 *  
mWakeLock.release();
 * @author Administrator
 *
 */
public class SysController {
	public static PowerManager.WakeLock keepWake(Context context){
		PowerManager pm = (PowerManager)context. getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
		return mWakeLock;
	}
	public static  void setFullScreen(Context context){
		  //隐去标题栏（应用程序的名字）  
      ((Activity)context).requestWindowFeature(Window.FEATURE_NO_TITLE);
      //隐去状态栏部分(电池等图标和一切修饰部分)
      ((Activity)context).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
              WindowManager.LayoutParams.FLAG_FULLSCREEN);
      
	}
}
