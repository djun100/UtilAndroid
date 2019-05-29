package com.cy.system;

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
public class UtilEnvControl {
	public static PowerManager.WakeLock keepWake(Context context){
		PowerManager pm = (PowerManager)context. getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
		return mWakeLock;
	}

	private static float mNormalBrightness;
	/**
	 * 调节屏幕亮度
	 */
	public static void turnUpBrightness(Activity activity) {
		Window window = activity.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		mNormalBrightness = lp.screenBrightness;
		if(lp.screenBrightness < 0.85) {
			lp.screenBrightness = 0.85f;
		}
		window.setAttributes(lp);
	}

	/**
	 * 恢复原先的亮度
	 */
	public static void turnDownBrightness(Activity activity) {
		Window window = activity.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.screenBrightness = mNormalBrightness;
		window.setAttributes(lp);
	}
}
