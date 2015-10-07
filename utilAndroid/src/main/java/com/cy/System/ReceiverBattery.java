package com.cy.System;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
	  	//注册广播接受者java代码<br>
		IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);<br>
		//创建广播接受者对象<br>
		BatteryReceiver batteryReceiver = new BatteryReceiver();<br>
		
		//注册receiver<br>
		registerReceiver(batteryReceiver, intentFilter);<br>
	 */
	public class ReceiverBattery extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			//判断它是否是为电量变化的Broadcast Action
			if(Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())){
				//获取当前电量
				int level = intent.getIntExtra("level", 0);
				//电量的总刻度
				int scale = intent.getIntExtra("scale", 100);
				//电量值： (level*100)/scale
				//把它转成百分比
//				tv.setText("电池电量为"+((level*100)/scale)+"%");
//				ActivityPlayerScreen.getInstance();
			}
		}
		
	}