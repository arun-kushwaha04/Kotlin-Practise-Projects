package com.example.timer

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import java.util.*

class MainService : Service() {
    private val timer = Timer()
    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        val time = intent.getDoubleExtra(CURRENT_TIME,0.0);
        timer.scheduleAtFixedRate(StopWatchTimerTask(time),0,1000)
        return START_NOT_STICKY
    }

    private inner class StopWatchTimerTask(private var time: Double):TimerTask(){
        override fun run() {
            if(!isServicePause.value){
                val intent = Intent(UPDATED_TIME)
                time++
                intent.putExtra(CURRENT_TIME, time)
                sendBroadcast(intent)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
        Log.i(TAG, "Service destroyed")
    }


    companion object{
        const val TAG = "MY-TAG"
        const val CURRENT_TIME = "currentTime"
        const val UPDATED_TIME = "updatedTime"
        var isServiceRunning = mutableStateOf(false)
        var isServicePause = mutableStateOf(false)
    }
}