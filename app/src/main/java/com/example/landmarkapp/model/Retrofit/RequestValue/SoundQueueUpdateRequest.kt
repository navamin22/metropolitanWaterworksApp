package com.example.landmarkapp.model.Retrofit.RequestValue

import com.google.gson.annotations.SerializedName

data class SoundQueueUpdateRequest (
    @SerializedName("request")
    val request: String = "",
    @SerializedName("sound_queueId")
    val soundQueueId: Int,
    @SerializedName("queueId")
    val queueId: Int,
    @SerializedName("active")
    val active: String = ""
)