package com.example.landmarkapp.model.Retrofit

import com.example.landmarkapp.model.Retrofit.Interface.QueueService
import com.example.landmarkapp.model.Retrofit.RequestValue.NormalRequest
import com.example.landmarkapp.model.Retrofit.RequestValue.QueueRequest
import com.example.landmarkapp.model.Retrofit.RequestValue.QueueUpdateRequest
import com.example.landmarkapp.model.Retrofit.response.QueueResponse
import com.example.landmarkapp.utils.StaticData.Companion.folder
import com.example.landmarkapp.utils.StaticData.Companion.php
import com.example.landmarkapp.utils.StaticData.Companion.queue_php
import com.example.landmarkapp.utils.StaticData.Companion.url
import com.example.landmarkapp.utils.TruthCertificates.Companion.okHttpClient
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class QueueApi {
    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val api = Retrofit.Builder()
        .baseUrl(url + folder + php + queue_php)
        //.client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
        .create(QueueService::class.java)

    fun insertQueue(request: QueueRequest): Call<String>{
        return api.insertQueue(request)
    }

    fun updateQueue(request: QueueUpdateRequest): Call<String>{
        return api.updateQueue(request)
    }

    fun getQueue(request: NormalRequest): Call<List<QueueResponse>>{
        return api.getQueue(request)
    }

    fun clearQueue(request: NormalRequest): Call<String>{
        return api.clearQueue(request)
    }
}