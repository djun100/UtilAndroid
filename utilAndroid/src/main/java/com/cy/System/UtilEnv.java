package com.cy.System;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.cy.app.UtilContext;
import com.cy.security.UtilMD5;
import com.cy.utils.Reflect;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.cy.utils.Reflect.on;

/**
 * print开头函数为探针工具，返回String
 * @author djun100
 *
 */
public class UtilEnv {
	private static int sArmArchitecture = -1;
	private static int mHasNeon = 0;
	public static String pathRoot = Environment.getExternalStorageDirectory().getPath();
	public static final String OS_XIAOMI ="OS_XIAOMI";//小米系统
	public static final String OS_MEIZU ="OS_MEIZU";//魅族系统
	public static final String OS_HUAWEI="OS_HUAWEI";//华为系统

	/**
	 * 手机型号
	 */
	public static String PRODUCT = android.os.Build.PRODUCT;
	/**
	 * 
	 */
	public static String CPU_ABI = android.os.Build.CPU_ABI;
	/**
	 * 
	 */
	public static String TAGS = android.os.Build.TAGS;
	/**
	 * 
	 */
	public static String MODEL = android.os.Build.MODEL;
	/**
	 * 手机sdk版本 eg:16
	 */
	public static String SDK = android.os.Build.VERSION.SDK;
	/**
	 * 
	 */
	public static String DEVICE = android.os.Build.DEVICE;
	/**
	 * 
	 */
	public static String DISPLAY = android.os.Build.DISPLAY;
	/**
	 * 手机系统版本 eg: 4.1.2
	 */
	public static String VERSION_RELEASE = android.os.Build.VERSION.RELEASE;

	public static String FINGERPRINT = android.os.Build.FINGERPRINT;

	/**
	 * @return Product: GT-I9220, 手机型号 <br>
	 *         CPU_ABI: armeabi-v7a, 手机cpu<br>
	 *         TAGS: release-keys, <br>
	 *         VERSION_CODES.BASE: 1,<br>
	 *         MODEL: GT-I9220, <br>
	 *         SDK: 16, 手机sdk版本<br>
	 *         VERSION.RELEASE: 4.1.2, 手机版本<br>
	 *         DEVICE: GT-I9220, <br>
	 *         DISPLAY: JZO54K.I9220ZCLSF,<br>
	 *         BRAND: samsung, <br>
	 *         BOARD: smdk4210, <br>
	 *         FINGERPRINT:
	 *         samsung/GT-I9220/GT-I9220:4.1.2/JZO54K/I9220ZCLSF:user
	 *         /release-keys,<br>
	 *         ID: JZO54K, <br>
	 *         MANUFACTURER: samsung, <br>
	 *         USER: se.infra<br>
	 */
	public static String getPhoneInfo() {
		String phoneInfo = "Product: " + android.os.Build.PRODUCT;
		phoneInfo += ", CPU_ABI: " + android.os.Build.CPU_ABI;
		phoneInfo += ", TAGS: " + android.os.Build.TAGS;
		phoneInfo += ", VERSION_CODES.BASE: "
				+ android.os.Build.VERSION_CODES.BASE;
		phoneInfo += ", MODEL: " + android.os.Build.MODEL;
		phoneInfo += ", SDK: " + android.os.Build.VERSION.SDK;
		phoneInfo += ", VERSION.RELEASE: " + android.os.Build.VERSION.RELEASE;
		phoneInfo += ", DEVICE: " + android.os.Build.DEVICE;
		phoneInfo += ", DISPLAY: " + android.os.Build.DISPLAY;
		phoneInfo += ", BRAND: " + android.os.Build.BRAND;
		phoneInfo += ", BOARD: " + android.os.Build.BOARD;
		phoneInfo += ", FINGERPRINT: " + android.os.Build.FINGERPRINT;
		phoneInfo += ", ID: " + android.os.Build.ID;
		phoneInfo += ", MANUFACTURER: " + android.os.Build.MANUFACTURER;
		phoneInfo += ", USER: " + android.os.Build.USER;
		return phoneInfo;

	}

	@SuppressLint("NewApi")
	public static Point getScreenSize(Context context) {
		Point size = new Point();
		if (false) {
//			if (Build.VERSION.SDK_INT >= 11) {
			try {
				size.x=getWindowManager(context).getDefaultDisplay().getWidth();
				size.y=getWindowManager(context).getDefaultDisplay().getHeight();
			} catch (NoSuchMethodError e) {
				Log.i("error", "it can't work");
			}

		} else {
			DisplayMetrics metrics = new DisplayMetrics();
			getWindowManager(context).getDefaultDisplay().getMetrics(metrics);
			size.x = metrics.widthPixels;
			size.y = metrics.heightPixels;
		}
		Log.i("ScreenSize", "size.x=" + size.x + "  size.y=" + size.y);
		return size;
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

	/**
	 * 获取网络状态，wifi,wap,2g,3g. 需要权限 <br>
	 * uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
	 * 
	 * @param context
	 *            上下文
	 * @return int 网络状态 {@link #NETWORKTYPE_2G},{@link #NETWORKTYPE_3G},          *
	 *         {@link #NETWORKTYPE_INVALID},{@link #NETWORKTYPE_WAP}*
	 *         <p>
	 *         {@link #NETWORKTYPE_WIFI}
	 */

	public static String getNetWorkType(Context context) {
		/** 没有网络 */
		final int NETWORKTYPE_INVALID = 0;
		/** wap网络 */
		final int NETWORKTYPE_WAP = 1;
		/** 2G网络 */
		final int NETWORKTYPE_2G = 2;
		/** 3G和3G以上网络，或统称为快速网络 */
		final int NETWORKTYPE_3G = 3;
		/** wifi网络 */
		final int NETWORKTYPE_WIFI = 4;
		/** ithernet网线 */
		final int NETWORKTYPE_ETHERNET = 5;
		int mNetWorkType = 0;
		String temp = null;
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();

		if (networkInfo != null && networkInfo.isConnected()) {
			String type = networkInfo.getTypeName();
//			Log.v("tag", "网络类型type:" + type);
			if (type.equalsIgnoreCase("WIFI")) {
				mNetWorkType = NETWORKTYPE_WIFI;
			} else if (type.equalsIgnoreCase("MOBILE")) {
				String proxyHost = android.net.Proxy.getDefaultHost();

				mNetWorkType = TextUtils.isEmpty(proxyHost) ? (isFastMobileNetwork(context) ? NETWORKTYPE_3G
						: NETWORKTYPE_2G)
						: NETWORKTYPE_WAP;
			} else if (type.equalsIgnoreCase("ethernet")) {
				mNetWorkType = NETWORKTYPE_ETHERNET;
			} else {
				mNetWorkType = NETWORKTYPE_INVALID;
			}
			switch (mNetWorkType) {
			case 0:
				return "NETWORKTYPE_INVALID";
			case 1:
				return "NETWORKTYPE_WAP";
			case 2:
				return "NETWORKTYPE_2G";
			case 3:
				return "NETWORKTYPE_3G";
			case 4:
				return "NETWORKTYPE_WIFI";
			case 5:
				return "NETWORKTYPE_ETHERNET";
			}
		}
		return temp;
	}

	public static boolean isFastMobileNetwork(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		switch (telephonyManager.getNetworkType()) {
		case TelephonyManager.NETWORK_TYPE_1xRTT:
			return false; // ~ 50-100 kbps
		case TelephonyManager.NETWORK_TYPE_CDMA:
			return false; // ~ 14-64 kbps
		case TelephonyManager.NETWORK_TYPE_EDGE:
			return false; // ~ 50-100 kbps
		case TelephonyManager.NETWORK_TYPE_EVDO_0:
			return true; // ~ 400-1000 kbps
		case TelephonyManager.NETWORK_TYPE_EVDO_A:
			return true; // ~ 600-1400 kbps
		case TelephonyManager.NETWORK_TYPE_GPRS:
			return false; // ~ 100 kbps
		case TelephonyManager.NETWORK_TYPE_HSDPA:
			return true; // ~ 2-14 Mbps
		case TelephonyManager.NETWORK_TYPE_HSPA:
			return true; // ~ 700-1700 kbps
		case TelephonyManager.NETWORK_TYPE_HSUPA:
			return true; // ~ 1-23 Mbps
		case TelephonyManager.NETWORK_TYPE_UMTS:
			return true; // ~ 400-7000 kbps
		case TelephonyManager.NETWORK_TYPE_EHRPD:
			return true; // ~ 1-2 Mbps
		case TelephonyManager.NETWORK_TYPE_EVDO_B:
			return true; // ~ 5 Mbps
		case TelephonyManager.NETWORK_TYPE_HSPAP:
			return true; // ~ 10-20 Mbps
		case TelephonyManager.NETWORK_TYPE_IDEN:
			return false; // ~25 kbps
		case TelephonyManager.NETWORK_TYPE_LTE:
			return true; // ~ 10+ Mbps
		case TelephonyManager.NETWORK_TYPE_UNKNOWN:
			return false;
		default:
			return false;
		}
	}

	/**
	 * 得到包含系统程序在内的所有应用程序名称，如 联系人，日历
	 * 
	 * @param context
	 * @return
	 */
	public static List<String> getAppNameList(Context context) {
		List<String> listResult = new ArrayList<String>();
		PackageManager pm = context.getPackageManager();
		Intent intent = new Intent(Intent.ACTION_MAIN, null);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);

		List<ResolveInfo> list = pm.queryIntentActivities(intent,
				PackageManager.PERMISSION_GRANTED);

		for (ResolveInfo rInfo : list) {
			listResult.add(rInfo.activityInfo.applicationInfo.loadLabel(pm)
					.toString());
		}
		return listResult;
	}

	/**
	 * 获取设备imei号<br>
	 * < uses-permission android:name="android.permission.READ_PHONE_STATE" />
	 * 
	 * @param context
	 * @return
	 */
	public static String getImei(Context context) {
		return ((TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dp2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dp(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 * @param pxValue
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param spValue
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}
	public static float getDensity(Context context){
		final float scale = context.getResources().getDisplayMetrics().density;
		return scale;
	}
	/**
	 * 获取CPU的架构 ARMV7还是ARMV6
	 * @return
	 */
	public static int getArmArchitecture() {

		if (sArmArchitecture != -1)
			return sArmArchitecture;
		try {
			InputStream is = new FileInputStream("/proc/cpuinfo");
			InputStreamReader ir = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(ir);
			try {
				String name = "CPU architecture";
				while (true) {
					String line = br.readLine();
					String[] pair = line.split(":");
					if (pair.length != 2)
						continue;
					String key = pair[0].trim();
					String val = pair[1].trim();
					if (key.compareToIgnoreCase(name) == 0) {
						String n = val.substring(0, 1);
						sArmArchitecture = Integer.parseInt(n);
						break;
					}
				}
			} finally {
				br.close();
				ir.close();
				is.close();
				if (sArmArchitecture == -1)
					sArmArchitecture = 6;
			}
		} catch (Exception e) {
			sArmArchitecture = 6;
		}
		return sArmArchitecture;
	}
	
	/**
	 * 检测是否支持NEON、VFP
	 * @return
	 */
	public static int getArmFeatures() {
		try {
			InputStream is = new FileInputStream("/proc/cpuinfo");
			InputStreamReader ir = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(ir);
			try {
				String name = "Features";
				while (true) {
					String line = br.readLine();
					String[] pair = line.split(":");
					if (pair.length != 2)
						continue;
					String key = pair[0].trim();
					String val = pair[1].trim();
					if (key.compareToIgnoreCase(name) == 0) {
						mHasNeon = val.indexOf("neon");
						break;
					}
				}
			} finally {
				br.close();
				ir.close();
				is.close();
			}
		} catch (Exception e) {
			mHasNeon = -1;
		}
		return mHasNeon;
	}
	/**打印屏幕长宽系列参数
	 * @param context 这里必须是activity实例
	 * @return
	 */
	public static String printxyInfo(Activity context){
		int x= UtilEnv.getScreenSize(context).x;
		int y= UtilEnv.getScreenSize(context).y;
		int dpx= UtilEnv.px2dp(context, x);
		int dpy= UtilEnv.px2dp(context, y);
		float density= UtilEnv.getDensity(context);
		String strOut="x:"+x+
				"  y:"+y+
				"  dpx:"+dpx+
				"  dpy:"+dpy+
				"  density:"+density;
		return strOut;
	}
	public static void gotoWiFiSetPage(Context context){
		 //直接调用系统自带的WIFI设置界面与Android的版本有关系
		//在Android版本10以下，调用的是：ACTION_WIRELESS_SETTINGS，版本在10以上的调用：ACTION_SETTINGS。
		//代码如下：
		if(android.os.Build.VERSION.SDK_INT > 10) {
		// 3.0以上打开设置界面，也可以直接用ACTION_WIRELESS_SETTINGS打开到wifi界面 
			context.startActivity(new Intent( android.provider.Settings.ACTION_SETTINGS)); 
		} else { 
			context.startActivity(new Intent( android.provider.Settings.ACTION_WIRELESS_SETTINGS)); 
		}

	}
	/**获取正在运行的进程<br>
	 * use:<br>
	 * List<RunningAppProcessInfo> progresses= SysInfo.getProgressList(this);<br>
	 *  for(RunningAppProcessInfo progress:runningProgresses){<br>
     	   com.k.app.Log.w(progress.processName);<br>
        }<br>
	 * @param context
	 * @return List RunningAppProcessInfo
	 */
	public static List<RunningAppProcessInfo> getProgressList(Context context){
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> runningProgresses = manager.getRunningAppProcesses();
        return runningProgresses;
/*        for(RunningAppProcessInfo progress:runningProgresses){
     	   com.k.app.Log.w(progress.processName);
        }*/
	}
	public static boolean hasSDcard() {
		if (!Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			return false;
		} else {
			return true;
		}

	}

	/**< uses-permission android:name="android.permission.READ_PHONE_STATE" /><br>
	 * 根据测试：
	 * 
	 * 所有的设备都可以返回一个 TelephonyManager.getDeviceId() 所有的GSM设备 (测试设备都装载有SIM卡)
	 * 可以返回一个TelephonyManager.getSimSerialNumber() 所有的CDMA 设备对于
	 * getSimSerialNumber() 却返回一个空值！ 所有添加有谷歌账户的设备可以返回一个 ANDROID_ID 所有的CDMA设备对于
	 * ANDROID_ID 和 TelephonyManager.getDeviceId() 返回相同的值（只要在设置时添加了谷歌账户）
	 * 目前尚未测试的：没有SIM卡的GSM设备、没有添加谷歌账户的GSM设备、处于飞行模式的设备。 所以如果你想得到设备的唯一序号，
	 * TelephonyManager.getDeviceId() 就足够了。但很明显暴露了DeviceID会使一些用户不满
	 * ，所以最好把这些id加密了。实际上加密后的序号仍然可以唯一的识别该设备，并且不会明显的暴露用户的特定设备， 例如，使用
	 * String.hashCode() ，结合UUID：
	 * 最后的deviceID可能是这样的结果: 00000000-54b3-e7c7-0000-000046bffd97
	 * @param context
	 * @return
	 */
	public static String getUniqueId(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		final String tmDevice, tmSerial, tmPhone, androidId;
		tmDevice = "" + tm.getDeviceId();
		tmSerial = "" + tm.getSimSerialNumber();
		androidId = ""+ android.provider.Settings.Secure.getString(context.getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);

		UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
		String uniqueId = deviceUuid.toString();
		return uniqueId;
	}



	/**
	 * 用于控制在屏幕上添加或移除悬浮窗
	 */
	private static WindowManager mWindowManager;
	/**
	 * 如果WindowManager还未创建，则创建一个新的WindowManager返回。否则返回当前已创建的WindowManager。
	 *
	 * @param context
	 *            必须为应用程序的Context.
	 * @return WindowManager的实例，用于控制在屏幕上添加或移除悬浮窗。
	 */
	private static WindowManager getWindowManager(Context context) {
		if (mWindowManager == null) {
			mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		}
		return mWindowManager;
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

	/**获取系统种类，MIUI，flyme...
	 * @return
	 */
	public static String getOsType(){
		if (isMeizu_Flyme()){
			return OS_MEIZU;
		}else if (isXiaomi_MIUI()){
			return OS_XIAOMI;
		}else if (isHuawei_EMUI()){
			return OS_HUAWEI;
		}
		return "";
	}

	private static boolean isXiaomi_MIUI(){
		String versionName=	Reflect.on(android.os.Build.class).call("getString","ro.miui.ui.version.name").toString();
		return TextUtils.isEmpty(versionName)?false:true;
	}

	private static boolean isHuawei_EMUI(){
		String value=	Reflect.on(android.os.Build.class).call("getString","ro.build.hw_emui_api_level").toString();
		return TextUtils.isEmpty(value)?false:true;
	}

	private static boolean isMeizu_Flyme(){
/* 获取魅族系统操作版本标识*/
		String meizuFlymeOSFlag  = getSystemProperty("ro.build.display.id","");
		if (TextUtils.isEmpty(meizuFlymeOSFlag)){
			return false;
		}else if (meizuFlymeOSFlag.contains("flyme") || meizuFlymeOSFlag.toLowerCase().contains("flyme")){
			return  true;
		}else {
			return false;
		}
	}

	/**获取系统属性
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	private static String getSystemProperty(String key, String defaultValue) {
		try {
			Class<?> clz = Class.forName("android.os.SystemProperties");
			Method get = clz.getMethod("get", String.class, String.class);
			return (String)get.invoke(clz, key, defaultValue);
		} catch (ClassNotFoundException e) {
			return null;
		} catch (NoSuchMethodException e) {
			return null;
		} catch (IllegalAccessException e) {
			return null;
		} catch (IllegalArgumentException e) {
			return null;
		} catch (InvocationTargetException e) {
			return null;
		}
	}
}
