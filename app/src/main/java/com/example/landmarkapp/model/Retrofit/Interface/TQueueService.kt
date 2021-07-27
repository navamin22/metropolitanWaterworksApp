package com.example.landmarkapp.model.Retrofit.Interface

import com.example.landmarkapp.model.Retrofit.RequestValue.QueueRequest
import com.example.landmarkapp.model.Retrofit.RequestValue.TransactionQueueRequest
import com.example.landmarkapp.utils.StaticData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface TQueueService {
    @POST(StaticData.queue_php)
    fun insertTQueue(
        @Body request: TransactionQueueRequest
    ): Call<String>


}