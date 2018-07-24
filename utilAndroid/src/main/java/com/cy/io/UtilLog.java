package com.cy.io;

import android.content.Intent;
import android.os.Bundle;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class UtilLog extends Log {
    public static boolean allowLog = true;
    /**
     * traversal bundle with string values.
     *
     * @param bundle
     * @return
     */
    public static String bundle2String(Bundle bundle) {
        if (bundle==null) {
            return "";
        }
        StringBuilder sb = new StringBuilder("");
        Set<String> keys = bundle.keySet();
        for (String key : keys) {
            sb.append(key).append(":").append(bundle.get(key)).append(";");
        }
        return sb.toString();
    }
    public static void printBundle(Bundle b){
        e(1,bundle2String(b));
    }
    public static <K,T> void printMap(Map<K,T> map){
        if (!allowLog) return;
        if (map==null) {
            return;
        }
        StringBuilder sb = new StringBuilder("");
        for (Map.Entry<K, T> entry : map.entrySet()) {
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append(";");
        }
        e(1,sb.toString());
    }
    public static void printArray(Object[] array){
        if (!allowLog) return;
        if (array==null) {
            return;
        }
        StringBuilder sb = new StringBuilder("");
        for (int i=0;i<array.length;i++) {
            sb.append("["+i+"]").append(array[i]).append("\n");
        }
        e(1,sb.toString());
    }
    public static void printList(List lists){
        if (!allowLog) return;
        if (lists==null) {
            return;
        }
        StringBuilder sb = new StringBuilder("");
        for(Object object:lists){
            sb.append(object.toString()).append(";");
        }
        e(1,sb.toString());
    }

    public static String intentToString(Intent intent){
        if (intent == null) return null;
        if (intent.getExtras()!=null){
            return intent.toString()+" EXTRA: "+bundle2String(intent.getExtras());
        }else {
            return intent.toString();
        }
    }
}
