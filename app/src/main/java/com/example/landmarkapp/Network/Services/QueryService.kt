package com.example.landmarkapp.Network.Services

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.widget.Toast

class QueryService: Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val mHandler = Handler()
        val mRunnable = Runnable {
            Toast.makeText(
                applicationContext,
                "Hello World",
                Toast.LENGTH_LONG
            ).show()
        }
        mHandler.postDelayed(mRunnable, 10 * 1000)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}