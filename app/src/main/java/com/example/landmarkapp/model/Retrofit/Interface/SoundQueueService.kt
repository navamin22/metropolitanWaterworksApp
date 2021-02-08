package com.example.landmarkapp.model.Retrofit.Interface

import com.example.landmarkapp.model.Retrofit.RequestValue.*
import com.example.landmarkapp.model.Retrofit.response.SoundQueueResponse
import com.example.landmarkapp.utils.StaticData.Companion.sound_queue_php
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SoundQueueService {
    @POST(sound_queue_php)
    fun insertSoundQueue(
        @Body request: SoundQueueRequest
    ): Call<String>

    @POST(sound_queue_php)
    fun updateSoundQueue(
        @Body request: SoundQueueUpdateRequest
    ): Call<String>

    @POST(sound_queue_php)
    fun getSoundQueue(
        @Body request: NormalRequest
    ): Call<List<SoundQueueResponse>>
}