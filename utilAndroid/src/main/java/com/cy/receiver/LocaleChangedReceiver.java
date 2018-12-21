package com.cy.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * 监听语言切换
 * usage:
 * registerReceiver(new LocaleChangedReceiver(),new IntentFilter(Intent.ACTION_LOCALE_CHANGED));
 */
public class LocaleChangedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_LOCALE_CHANGED)) {
        }
    }
}
