package com.example.landmarkapp.ui.Activity.Report

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.landmarkapp.R
import com.example.landmarkapp.Receiver.ResetQueueReceiver
import com.example.landmarkapp.databinding.ActivityAccountBinding
import com.example.landmarkapp.ui.Activity.BaseActivity
import com.example.landmarkapp.ui.Fragment.Report.LoginFragment
import com.example.landmarkapp.utils.StaticData.Companion.screen_height
import com.example.landmarkapp.utils.StaticData.Companion.screen_width
import java.util.*

class AccountActivity: BaseActivity() {
    private lateinit var binding: ActivityAccountBinding
    private lateinit var alarmManager: AlarmManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_account)
        initInstance()
    }

    private fun initInstance(){
        //setAlarmReset()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flfragment, LoginFragment(this@AccountActivity))
            commit()
        }
    }

    private fun setAlarmReset(){
        val cal = Calendar.getInstance()
        cal.set(Calendar.SECOND, 0)

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(applicationContext, ResetQueueReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, 0, intent, 0)
        Log.d("Dashboard Activity","Dashboard: Create..." + Date().toString())
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis(),
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        screen_width = binding.root.measuredWidth
        screen_height = binding.root.measuredHeight
        println("Width of screen = $screen_width")
        println("Height of screen = $screen_height")
    }
}