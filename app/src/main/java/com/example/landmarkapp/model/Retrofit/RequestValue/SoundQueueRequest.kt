package com.example.landmarkapp.model.Retrofit.RequestValue

import com.google.gson.annotations.SerializedName

data class SoundQueueRequest (
    @SerializedName("request")
    val request: String = "",
    @SerializedName("queueId")
    val queueId: Int,
    @SerializedName("queueNumber")
    val queueNum: String = "",
    @SerializedName("message")
    val msg: String = "",
    @SerializedName("active")
    val active: String = ""

    )