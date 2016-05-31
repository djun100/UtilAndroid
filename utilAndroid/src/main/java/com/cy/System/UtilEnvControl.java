package com.cy.System;

import android.content.Context;
import android.os.PowerManager;

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

}
