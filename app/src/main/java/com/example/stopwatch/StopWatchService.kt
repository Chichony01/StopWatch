package com.example.stopwatch

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.util.Timer
import java.util.TimerTask

class StopWatchService : Service() {
    private val timer =Timer()
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val time = intent.getDoubleExtra(CURRENT_TIME,0.0)
        timer.scheduleAtFixedRate(StopWatchTimerTask(time),0,1000)      // 1000 milli s ec = 1sec
        return START_NOT_STICKY // due to this system will not restart the service automatically
    }

    override fun onDestroy() {
        timer.cancel()
        super.onDestroy()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    companion object{
        const val CURRENT_TIME = "current time"
        const val UPDATED_TIME = "updated time"
    }

    private inner class StopWatchTimerTask(private var time: Double) :TimerTask(){      // this class will take current time as constructor parameter
        override fun run() {
            val intent = Intent(UPDATED_TIME)
            time++      // this will increase time by 1
            intent.putExtra(CURRENT_TIME,time)      // now this will send the updated time
            sendBroadcast(intent)   // broadcast are android components which allows to send or receive events
        }

    }
}