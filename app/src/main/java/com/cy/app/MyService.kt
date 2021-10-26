package com.cy.app

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import com.cy.io.Log
import com.cy.system.UtilShell

class MyService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var runnable = object :Runnable {
            override fun run() {
                var result = UtilShell.execCommand("int tap 500 500",false,true)
                Log.d("tag",result)
                Handler().postDelayed(this,3000)
            }
        }
        Handler().postDelayed(runnable,3000)
        return super.onStartCommand(intent, flags, startId)
    }
}