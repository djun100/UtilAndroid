package com.cy.communication;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.text.TextUtils;

import com.cy.app.UtilContext;

import java.util.Map;

/**
 * Created by Administrator on 2016/8/22.
 */
public class UtilAlarm {
    //开启轮询服务
    public static <V> void controlService(int seconds, Class<?> service, String action, Map<String,V> map, boolean isEnable,boolean isTriggerImmediately) {
        Context context= UtilContext.getContext();
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        //需要执行Service的Intent
        Intent intent = new Intent(context, service);
        if (!TextUtils.isEmpty(action)) {
            intent.setAction(action);
        }
        if (map!=null) {
            for (Map.Entry<String,V> entry : map.entrySet()) {
                String key= entry.getKey();
                V v=entry.getValue();
                if (v instanceof String){
                    intent.putExtra( key, (String)v);
                }else if (v instanceof Integer){
                    intent.putExtra( key, (Integer) v);
                }else {
                    //                intent.putExtra( key, v);
                }
            }
        }

//        intent.setPackage(getPackageName());//这里你需要设置你应用的包名
        /**
         * 如果是通过启动服务来实现闹钟提示的话， PendingIntent对象的获取就应该采用Pending.getService (Context c,int i,Intent intent,int j)方法；
         如果是通过广播来实现闹钟提示的话， PendingIntent对象的获取就应该采用 PendingIntent.getBroadcast (Context c,int i,Intent intent,int j)方法；
         如果是采用Activity的方式来实现闹钟提示的话，PendingIntent对象的获取 就应该采用 PendingIntent.getActivity(Context c,int i,Intent intent,int j) 方法。
         如果这三种方法错用了的话，虽然不会报错，但是看不到闹钟提示效果。
         */
        /**
         FLAG_CANCEL_CURRENT:如果描述的PendingIntent对象已经存在时，会先取消当前的PendingIntent对象再生成新的。
         FLAG_NO_CREATE:如果描述的PendingIntent对象不存在，它会返回null而不是去创建它。
         FLAG_ONE_SHOT:创建的PendingIntent对象只使用一次。
         FLAG_UPDATE_CURRENT:如果描述的PendingIntent对象存在，则保留它，并将新的PendingIntent对象的数据替换进去。
         * */
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (isEnable) {
            //触发服务的起始时间
//          long triggerAtSystemTime = SystemClock.elapsedRealtime();
            long triggerAtRealTime = SystemClock.elapsedRealtime();
            if (!isTriggerImmediately){
                triggerAtRealTime+=seconds * 1000;
            }
            /**
             * type: 有五个可选值:
             AlarmManager.ELAPSED_REALTIME: 闹钟在手机睡眠状态下不可用，该状态下闹钟使用相对时间（相对于系统启动开始），状态值为3;
             AlarmManager.ELAPSED_REALTIME_WAKEUP 闹钟在睡眠状态下会唤醒系统并执行提示功能，该状态下闹钟也使用相对时间，状态值为2；
             AlarmManager.RTC 闹钟在睡眠状态下不可用，该状态下闹钟使用绝对时间，即当前系统时间，状态值为1；
             AlarmManager.RTC_WAKEUP 表示闹钟在睡眠状态下会唤醒系统并执行提示功能，该状态下闹钟使用绝对时间，状态值为0;
             AlarmManager.POWER_OFF_WAKEUP 表示闹钟在手机关机状态下也能正常进行提示功能，所以是5个状态中用的最多的状态之一， 该状态下闹钟也是用绝对时间，状态值为4；不过本状态好像受SDK版本影响，某些版本并不支持；

             从4.4版本后(API 19),Alarm任务的触发时间可能变得不准确,有可能会延时,是系统 对于耗电性的优化,如果需要准确无误可以调用setExtra()方法
             */
            //使用AlarmManger的setRepeating方法设置定期执行的时间间隔（seconds秒）和需要执行的Service
            manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtRealTime, seconds * 1000, pendingIntent);
            /**
             * 第一个参数决定第二个参数的类型,如果是REALTIME的话就用： SystemClock.elapsedRealtime( )方法可以获得系统开机到现在经历的毫秒数 如果是RTC的就用:System.currentTimeMillis()可获得从1970.1.1 0点到 现在做经历的毫秒数
             */
//          manager.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtSystemTime, seconds * 1000, pendingIntent);
            /**
             * 它是不准确的，相对于setRepeating()，也更加节能。因为系统会将差不多的闹钟进行合并，以避免在不必要地唤醒设备。
             * */
//          manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), seconds * 1000, pendingIntent);
        } else {//取消
            //取消正在执行的服务
            manager.cancel(pendingIntent);
        }
    }

    public static void controlActivity(Class<? extends Activity> activity,boolean isCancel,long milliseconds_after) {
        Context context= UtilContext.getContext();
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        long triggerAtTime = SystemClock.elapsedRealtime() + milliseconds_after;
        Intent intent = new Intent(context, activity);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        if (isCancel){
            manager.cancel(pendingIntent);
        }else {
            //用于设置一次闹钟
            manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent);
        }
    }

    public static void controlBroadcast(boolean isCancel,String action) {
        Context context= UtilContext.getContext();
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Intent intent = new Intent(context,AlarmReceiver.class);
//        Intent intent = new Intent("com.zfq.receiver.ALARMRECEIVER");
        Intent intent = new Intent(action);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        if (isCancel){
            manager.cancel(pendingIntent);
        }else{
            manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 6 * 1000, pendingIntent);

        }
    }

    /**
     <receiver android:name=".MainActivity$AlarmReceiver">
     <intent-filter>
     <action android:name="com.zfq.receiver.ALARMRECEIVER" />
     </intent-filter>
     </receiver>
     */
    public static class AlarmReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
}
