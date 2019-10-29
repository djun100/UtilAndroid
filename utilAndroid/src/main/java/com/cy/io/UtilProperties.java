package com.cy.io;

import android.os.Environment;

import com.cy.app.UtilContext;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 */
public class UtilProperties {
    private static String PATH;

    private static String getPath(){
        if (PATH == null){
            PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/appbox/properties.dat";
        }
        File file=new File(PATH);
        if (!file.exists()){
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return PATH;
    }

    private static Properties loadConfig() {
        Properties properties = new Properties();
        try {
            FileInputStream s = new FileInputStream(getPath());
            properties.load(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }

    private static void saveConfig(Properties properties) {
        try {
            FileOutputStream s = new FileOutputStream(getPath(), false);
            properties.store(s, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String[] PERM_GROUP = new String[]{
            Permission.READ_EXTERNAL_STORAGE,
            Permission.WRITE_EXTERNAL_STORAGE};

    public static void write(String key, String value) {
        if (!AndPermission.hasPermissions(UtilContext.getContext(),PERM_GROUP)){
            return;
        }
        Properties prop = loadConfig();
        prop.put(key, value);
        saveConfig(prop);
    }

    public static  <T> T  read(String key,T defaultObject){
        if (!AndPermission.hasPermissions(UtilContext.getContext(),PERM_GROUP)){
            return defaultObject;
        }

        Properties prop = loadConfig();
        String value = prop.getProperty(key,""+defaultObject);

        if (defaultObject instanceof String) {
            return (T) value;
        }
        else if (defaultObject instanceof Integer) {
            int result = Integer.parseInt(value);
            return (T) new Integer(result);
        }
        else if (defaultObject instanceof Boolean) {
            boolean result = Boolean.parseBoolean(value);
            return (T) new Boolean(result);
        }
        else if (defaultObject instanceof Float) {
            float result = Float.parseFloat(value);
            return (T) new Float(result);
        }
        else if (defaultObject instanceof Long) {
            long result = Long.parseLong(value);
            return (T) new Long(result);
        }
        else if (defaultObject == null){
            return (T) value;
        }

        return defaultObject;
    }

    public static void main(String[] args) {
        write("key","prop");
        String key1=read("key",null);
        String key2=read("key","");
//        String key3=read("key",1);  cannot compile
        KLog.i(String.format("key1:%s,key2:%s",key1,key2));
    }
}
