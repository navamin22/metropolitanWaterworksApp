package com.example.landmarkapp.Network.WorkManager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Exception

class ResetQueueManager(context: Context, workerParams: WorkerParameters) : Worker(context,
    workerParams
) {
    override fun doWork(): Result {
        try {

        } catch (e: Exception){

        }
        return Result.failure()
    }
}