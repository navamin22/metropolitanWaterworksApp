package com.example.landmarkapp.Network.Services

import android.app.Service
import android.content.Intent
import android.os.CountDownTimer
import android.os.Handler
import android.os.IBinder
import com.example.landmarkapp.Network.Repository
import com.example.landmarkapp.ui.Activity.Report.DashboardActivity

class RateCheckService: Service() {
    private lateinit var handler: Handler
    private lateinit var countDown: CountDownTimer
    private val repo = Repository()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        timing()
        //countDownLoop()
        //onTaskRemoved(intent)

        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    private fun checkRateBelow(){
        repo.getBelowRateLiveData(applicationContext)
    }

    private fun timing(){
        handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                repo.getBelowRateLiveData(applicationContext)
                handler.postDelayed(this, 10000)
            }
        }, 10000)
    }

    private fun countDownLoop(){
        countDown = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                //DashboardActivity.dashBoardViewModel.belowRateCheck(applicationContext)
                start()
            }
        }
        countDown.start()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        val restartServiceIntent = Intent(applicationContext, this.javaClass)
        restartServiceIntent.setPackage(packageName)
        startService(restartServiceIntent)
        super.onTaskRemoved(rootIntent)
    }


}