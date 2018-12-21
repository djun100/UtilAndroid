/**
怎样获得系统的广播，比如屏幕的关闭与开启广播
------Solutions------
屏幕被关闭 android.intent.action.SCREEN_OFF
屏幕已经被打开 android.intent.action.SCREEN_ON 

新建类MyReceiver.java，代码如下：


package org.shuxiang.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver
{
@Override 
 public void onReceive(Context context, Intent intent)
 {
Log.i("Test", "收到广播");
                //你需要增加的代码放在这里
 }
} 



在AndroidManifest.xml里面注册广播接收机


<receiver android:name="MyReceiver"> 
<intent-filter>
<action android:name="android.intent.action.SCREEN_OFF" />
<action android:name="android.intent.actionandroid.intent.action.SCREEN_ON" />
</intent-filter>
</receiver> 
 */
package com.cy.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.cy.io.Log;


/**usage：
 * in activity:<br>
 * onCreate():<br>
 * ReceiverScreenOnOff mReceiver=new ReceiverScreenOnOff (this,this);<br>
 * onResume():<br>
 * mReceiver.register();<br>
 * onPause():<br>
 * mReceiver.unRegister();
 * 
   @author cy <a href="https://github.com/djun100">https://github.com/djun100</a>
 */
public class ReceiverScreenOnOff {
	Context mContext;
	IntentFilter mFilter;
	BroadcastReceiver mReceiverScreenOnOff;
	public void register(){
		mContext.registerReceiver(mReceiverScreenOnOff, mFilter);
	}
	public void unRegister(){
		mContext.unregisterReceiver(mReceiverScreenOnOff);
	}
	/**参数设计没问题，如在fragment中ReceiverScreenOnOff(getActivity(), this);
	 * @param context
	 * @param iReceiver
	 */
	public ReceiverScreenOnOff(Context context,final IReceiveScreenOnOff iReceiver) {
			mContext=context;
			
			mFilter = new IntentFilter();  
		    // 屏幕灭屏广播  
			mFilter.addAction(Intent.ACTION_SCREEN_OFF);  
		    // 屏幕亮屏广播  
			mFilter.addAction(Intent.ACTION_SCREEN_ON);  
		    // 屏幕解锁广播  
			mFilter.addAction(Intent.ACTION_USER_PRESENT);  
		    // 当长按电源键弹出“关机”对话或者锁屏时系统会发出这个广播  
		    // example：有时候会用到系统对话框，权限可能很高，会覆盖在锁屏界面或者“关机”对话框之上，  
		    // 所以监听这个广播，当收到时就隐藏自己的对话，如点击pad右下角部分弹出的对话框   
			mFilter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);  
		  
		    mReceiverScreenOnOff = new BroadcastReceiver() {  
		        @Override  
		        public void onReceive(final Context context, final Intent intent) {  
		            Log.d( "onReceive");
		            String action = intent.getAction();  
		            iReceiver.onScreenEvent(action);
		            if (Intent.ACTION_SCREEN_ON.equals(action)) {  
		                Log.d("screen on");
		            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {  
		                Log.d("screen off");  
		            } else if (Intent.ACTION_USER_PRESENT.equals(action)) {  
		                Log.d( "screen unlock");  
		            } else if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction())) {  
		                Log.i( " receive Intent.ACTION_CLOSE_SYSTEM_DIALOGS");  
		            }  
		        }  
		    };  
	}
	public interface IReceiveScreenOnOff{
		/**
		 * 屏幕灭、亮、解锁事件
		 */
		void onScreenEvent(String action);
	}
}
