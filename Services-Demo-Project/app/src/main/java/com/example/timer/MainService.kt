package com.example.timer

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MainService : Service() {

    init{
        Log.i("MYTAG","Service has been created")
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Log.i("MYTAG", "Service started")
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("MYTAG", "Service destroyed")
    }
}