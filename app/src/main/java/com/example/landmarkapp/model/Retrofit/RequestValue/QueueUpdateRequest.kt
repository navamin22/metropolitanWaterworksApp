package com.example.landmarkapp.model.Retrofit.RequestValue

import com.google.gson.annotations.SerializedName

data class QueueUpdateRequest (
    @SerializedName("request")
    val request: String = "",
    @SerializedName("queueId")
    val queueId: String = "",
    @SerializedName("queueStatus")
    val queueStatus: String = ""
)