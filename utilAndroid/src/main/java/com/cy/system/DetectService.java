package com.cy.system;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

/**注册
 <service
 android:name=".DetectService"
 android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE">

 <intent-filter>
 <action android:name="android.accessibilityservice.AccessibilityService" />
 </intent-filter>

 <meta-data
 android:name="android.accessibilityservice"
 android:resource="@xml/detection_service_config" />
 </service>
 */
public class DetectService extends AccessibilityService {

    private static String mForegroundPackageName;
    private static DetectService mInstance = null;

    public DetectService() {
    }

    public static DetectService getInstance() {
        if (mInstance == null) {
            synchronized (DetectService.class) {
                if (mInstance == null) {
                    mInstance = new DetectService();
                }
            }
        }
        return mInstance;
    }

    /**
     * 监听窗口焦点,并且获取焦点窗口的包名
     *
     * @param event
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            mForegroundPackageName = event.getPackageName().toString();
        }
    }

    @Override
    public void onInterrupt() {
    }

    public String getForegroundPackage() {
        return mForegroundPackageName;
    }


    /**
     * 此方法用来判断当前应用的辅助功能服务是否开启
     *
     * @param context
     * @return
     */
    public static boolean isAccessibilitySettingsOn(Context context) {
        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(context.getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            Log.d("wenming", e.getMessage());
        }

        if (accessibilityEnabled == 1) {
            String services = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (services != null) {
                return services.toLowerCase().contains(context.getPackageName().toLowerCase());
            }
        }
        return false;
    }
}