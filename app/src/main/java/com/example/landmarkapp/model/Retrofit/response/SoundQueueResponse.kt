package com.example.landmarkapp.model.Retrofit.response

import com.google.gson.annotations.SerializedName

data class SoundQueueResponse (
    @SerializedName("sound_queueId")
    val sound_queueId: Int,
    @SerializedName("queueId")
    val queueId: Int,
    @SerializedName("queueNumber")
    val queueNum: String = "",
    @SerializedName("message")
    val msg: String = "",
    @SerializedName("active")
    val active: String = "",
    @SerializedName("sound_date")
    val sound_date: String = "",
    @SerializedName("sound_date_finish")
    val sound_date_finish: String = ""
)