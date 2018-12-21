package com.cy.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**usage:
 *         //创建广播
 *         innerReceiver = new HomeKeyRecevier();
 *         //动态注册广播
 *         IntentFilter intentFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
 *         //启动广播
 *         registerReceiver(innerReceiver, intentFilter);
 *
 */
public class HomeKeyRecevier extends BroadcastReceiver {

        final String SYSTEM_DIALOG_REASON_KEY = "reason";

        final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";

        final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action)) {
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                if (reason != null) {
                    if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
                        //Home键被监听

                    } else if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
                        //多任务键被监听
                    }
                }
            }
        }
    }