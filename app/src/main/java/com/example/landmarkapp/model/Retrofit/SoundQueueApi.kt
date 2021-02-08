package com.example.landmarkapp.model.Retrofit

import com.example.landmarkapp.model.Retrofit.Interface.SoundQueueService
import com.example.landmarkapp.model.Retrofit.RequestValue.*
import com.example.landmarkapp.model.Retrofit.response.SoundQueueResponse
import com.example.landmarkapp.utils.StaticData.Companion.folder
import com.example.landmarkapp.utils.StaticData.Companion.php
import com.example.landmarkapp.utils.StaticData.Companion.sound_queue_php
import com.example.landmarkapp.utils.StaticData.Companion.url
import com.example.landmarkapp.utils.TruthCertificates
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class SoundQueueApi {
    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val api = Retrofit.Builder()
        .baseUrl(url + folder + php + sound_queue_php)
        //.client(TruthCertificates.okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
        .create(SoundQueueService::class.java)

    fun insertSoundQueue(request: SoundQueueRequest): Call<String> {
        return api.insertSoundQueue(request)
    }

    fun updateSoundQueue(request: SoundQueueUpdateRequest): Call<String> {
        return api.updateSoundQueue(request)
    }

    fun getSoundQueue(request: NormalRequest): Call<List<SoundQueueResponse>> {
        return api.getSoundQueue(request)
    }
}