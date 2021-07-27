package com.example.landmarkapp.Receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.landmarkapp.Network.Repository
import java.util.*

class ResetQueueReceiver: BroadcastReceiver() {
    private val repo = Repository()
    override fun onReceive(p0: Context?, p1: Intent?) {
        Log.d("Broadcast Receiver","Receiver: Resetting data..." + Date().toString())
        //resetQueueSystem()
    }

    private fun resetQueueSystem(){
        repo.resetQueueOperation()
    }
}