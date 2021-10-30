package com.cy.app

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import com.cy.io.Log
import com.cy.system.UtilShell
import android.app.PendingIntent

import com.cy.app.main.MainActivity

import android.app.Notification
import android.graphics.BitmapFactory

import android.app.NotificationManager

import android.app.NotificationChannel
import android.content.Context

import android.os.Build





class MyService : Service() {

    override fun onCreate() {
        super.onCreate()
        startNotificationIfNeed()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var runnable = object :Runnable {
            override fun run() {
                var result = UtilShell.execCommand("input tap 500 500",true,true)
                Log.d("tag",result)
                Handler().postDelayed(this,3000)
            }
        }
        Handler().postDelayed(runnable,3000)
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startNotificationIfNeed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("im_channel_id",
                    "System", NotificationManager.IMPORTANCE_LOW)
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
            val notification: Notification = Notification.Builder(this, "im_channel_id")
                    .setSmallIcon(R.mipmap.ic_launcher) // the status icon
                    .setWhen(System.currentTimeMillis()) // the time stamp
                    .setContentText("服务正在运行") // the contents of the entry
                    .build()
            startForeground(1, notification)
        }
    }
}