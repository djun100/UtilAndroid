package com.cy.System;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.cy.app.UtilContext;
import com.cy.utils.UtilReflect;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;


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

	/**
	 * 获取网络状态，wifi,wap,2g,3g. 需要权限 <br>
	 * uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
	 * 
	 * @param context
	 *            上下文
	 * @return int 网络状态
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

	public static boolean hasSDcard() {
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
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
		String versionName=	UtilReflect.reflect(android.os.Build.class).method("getString","ro.miui.ui.version.name").toString();
		return TextUtils.isEmpty(versionName)?false:true;
	}

	private static boolean isHuawei_EMUI(){
		String value=	UtilReflect.reflect(android.os.Build.class).method("getString","ro.build.hw_emui_api_level").toString();
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

	/**获取系统当前语言下应该使用的values文件夹名称，而不是正在使用的values文件夹资源
	 * @return
	 */
	public static String getCurrLanguageValuesFolder() {
		Locale locale = Locale.getDefault();
		String language = locale.getLanguage().toLowerCase();
		String country = locale.getCountry().toUpperCase();
		//如果是英语，应该返回values文件夹
		if (language.equals("en")){
			return "values";
		}else if (language.equals("ar")
				||language.equals("fa")
				||language.equals("fr")
				||language.equals("ha")
				||language.equals("hi")
				||language.equals("in")
				||language.equals("iw")
				||language.equals("ru")
				||language.equals("so")
				||language.equals("sw")
				||language.equals("tl")
				||language.equals("tr")){
			return "values-"+language;
		} else {
			return "values-"+language+"-r"+country;
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
	public static List<ActivityManager.RunningAppProcessInfo> getProgressList(Context context){
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> runningProgresses = manager.getRunningAppProcesses();
		return runningProgresses;
/*        for(RunningAppProcessInfo progress:runningProgresses){
     	   com.k.app.Log.w(progress.processName);
        }*/
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
	 * GPS是否打开
	 *
	 * @return Gps是否可用
	 */
	public static boolean isGpsEnabled() {
		LocationManager lm = (LocationManager) UtilContext.getContext()
				.getSystemService(Context.LOCATION_SERVICE);
		return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	public static String getMemInfo() {

		//最大分配内存获取方法2
		double maxMemory = Runtime.getRuntime().maxMemory() * 1.0 / (1024 * 1024);
		//当前分配的总内存
		double totalMemory = Runtime.getRuntime().totalMemory() * 1.0 / (1024 * 1024);
		//剩余内存
		double freeMemory = Runtime.getRuntime().freeMemory() * 1.0 / (1024 * 1024);
		String result = String.format("maxMemory: %s\n" +
				"totalMemory: %s\n" +
				"freeMemory: %s",maxMemory,totalMemory,freeMemory);
		return result;
	}
}