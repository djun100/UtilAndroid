package com.cy.file;

import android.content.Context;
import android.content.SharedPreferences;

import com.cy.app.UtilContext;
import com.cy.io.KLog;

/**
 * SharedPreferences的一个工具类，调用setParam就能保存String, Integer, Boolean, Float, Long类型的参数
 * 同样调用getParam就能获取到保存在手机里面的数据
 * @author wangxuechao
 *
 */
public class UtilSP {
	/**
	 * 保存在手机里面的文件名
	 */
	private static final String FILE_NAME = "share_date";

	/**
	 * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
	 * @param key
	 * @param object
	 */
	public static void set(String key, Object object){
		set(FILE_NAME, key, object);
	}
	/**
	 * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
	 * @param key
	 * @param value
	 */
	public static <T> void set(String fileName, String key, T value){

		SharedPreferences sp = UtilContext.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();

		if(value instanceof String){
			editor.putString(key, (String)value);
		}
		else if(value instanceof Integer){
			editor.putInt(key, (Integer)value);
		}
		else if(value instanceof Boolean){
			editor.putBoolean(key, (Boolean)value);
		}
		else if(value instanceof Float){
			editor.putFloat(key, (Float)value);
		}
		else if(value instanceof Long){
			editor.putLong(key, (Long)value);
		}
		else if (value == null){
			editor.putString(key, null);
		}

		editor.commit();
//		UtilProperties.write(key,value+"");
	}


	/**
	 * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static <T> T get(String fileName, String key, T defaultValue){
		SharedPreferences sp = UtilContext.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);

		if (sp.contains(key)) {
			if (defaultValue instanceof String) {
				return (T) sp.getString(key, (String) defaultValue);
			}
			else if (defaultValue instanceof Integer) {
				int result = sp.getInt(key, (Integer) defaultValue);
				return (T) new Integer(result);
			}
			else if (defaultValue instanceof Boolean) {
				return (T) new Boolean(sp.getBoolean(key, (Boolean) defaultValue));
			}
			else if (defaultValue instanceof Float) {
				return (T) new Float(sp.getFloat(key, (Float) defaultValue));
			}
			else if (defaultValue instanceof Long) {
				return (T) new Long(sp.getLong(key, (Long) defaultValue));
			}
			else if (defaultValue == null){
				return (T) sp.getString(key, null);
			}
		}/*else {
			T result= UtilProperties.read(key,defaultValue);
			KLog.i("从Properties取出值：" + result);
			return result;
		}*/
		return defaultValue;
	}
	/**
	 * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
	 * @param key
	 * @param defaultObject
	 * @return
	 */
	public static <T> T get( String key, T defaultObject){
		return get(FILE_NAME, key, defaultObject);
	}

	public static void main(String[] args) {
		set("keySp","prop");
		String key1 = get("keySp",null);
		String key2 = get("keySp","");
//        String key3=getParam("key",1);  cannot compile
		KLog.i(String.format("key1:%s,key2:%s",key1,key2));
	}
}

