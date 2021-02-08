package com.example.landmarkapp.model.Retrofit.Interface

import com.example.landmarkapp.model.Retrofit.RequestValue.NormalRequest
import com.example.landmarkapp.model.Retrofit.RequestValue.QueueRequest
import com.example.landmarkapp.model.Retrofit.RequestValue.QueueUpdateRequest
import com.example.landmarkapp.model.Retrofit.response.QueueResponse
import com.example.landmarkapp.utils.StaticData.Companion.queue_php
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface QueueService {
    @POST(queue_php)
    fun insertQueue(
        @Body request: QueueRequest
    ): Call<String>

    @POST(queue_php)
    fun updateQueue(
        @Body request: QueueUpdateRequest
    ): Call<String>

    @POST(queue_php)
    fun getQueue(
        @Body request: NormalRequest
    ): Call<List<QueueResponse>>

    @POST(queue_php)
    fun clearQueue(
        @Body request: NormalRequest
    ): Call<String>

}