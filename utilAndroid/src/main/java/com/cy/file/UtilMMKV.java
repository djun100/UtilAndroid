package com.cy.file;

import com.cy.app.UtilContext;
import com.tencent.mmkv.MMKV;

/**
 * 1、用户登录后需要调用setUserName()以便后续保存用户相关信息
 * 2、setUserRelated()\getUserRelated()是保存\获取用户相关的信息
 * 3、set()\get()是保存\获取非用户相关的信息
 * */
public class UtilMMKV {

    private static boolean sInited;

    /**
     * 自动初始化，无需手动调用，多个进程都要初始化
     */
    private static void ifInit(){
        if (!sInited){
            sInited=true;
            String rootDir = MMKV.initialize(UtilContext.getContext());
            System.out.println("mmkv root: " + rootDir);
        }
    }
    //此userName只用作区分不同用户在mmkv存储的数据区分
    public static void setUserName(String userName) {
        set("userName", userName);
    }

    private static String getUserName() {
        return get("userName",null);
    }

    private static MMKV getMMKV() {
        ifInit();
        return MMKV.mmkvWithID(null, MMKV.MULTI_PROCESS_MODE);
    }

    private static MMKV getUserRelatedMMKV() {
        ifInit();
        return MMKV.mmkvWithID(getUserName(), MMKV.MULTI_PROCESS_MODE);
    }

    public static <T> void set(String key, T value) {
        setInternal(getMMKV(),key,value);
    }

    public static <T> void setUserRelated(String key, T value) {
        setInternal(getUserRelatedMMKV(),key,value);
    }

    private static <T> void setInternal(MMKV mmkv,String key, T value) {
        if (value instanceof String) {
            mmkv.encode(key, (String) value);
        } else if (value instanceof Integer) {
            mmkv.encode(key, (Integer) value);
        } else if (value instanceof Boolean) {
            mmkv.encode(key, (Boolean) value);
        } else if (value instanceof Float) {
            mmkv.encode(key, (Float) value);
        } else if (value instanceof Long) {
            mmkv.encode(key, (Long) value);
        } else if (value == null) {
            mmkv.encode(key, "");
        }

    }

    public static <T> T get(String key, T defaultValue) {
        return getInternal(getMMKV(),key,defaultValue);
    }

    public static <T> T getUserRelated(String key, T defaultValue) {
        return getInternal(getUserRelatedMMKV(),key,defaultValue);
    }


    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    private static <T> T getInternal(MMKV mmkv,String key, T defaultValue) {
        if (defaultValue instanceof String) {
            String value = mmkv.decodeString(key, (String) defaultValue);
            return (T) value;
        } else if (defaultValue instanceof Integer) {
            Integer value = mmkv.decodeInt(key, (Integer) defaultValue);
            return (T) value;
        } else if (defaultValue instanceof Boolean) {
            Boolean value = mmkv.decodeBool(key, (Boolean) defaultValue);
            return (T) value;
        } else if (defaultValue instanceof Float) {
            Float value = mmkv.decodeFloat(key, (Float) defaultValue);
            return (T) value;

        } else if (defaultValue instanceof Long) {
            Long value = mmkv.decodeLong(key, (Long) defaultValue);
            return (T) value;
        } else if (defaultValue == null) {
            String value = mmkv.decodeString(key, null);
            return (T) value;
        }
        return defaultValue;
    }
}
