package com.example.landmarkapp.model.Retrofit.RequestValue

import com.google.gson.annotations.SerializedName

data class QueueRequest (
    @SerializedName("request")
    val request: String = "",
    @SerializedName("queueNumber")
    val queueNum: String = "",
    @SerializedName("queueDate")
    val queueDate: String = "",
    @SerializedName("queueStatus")
    val queueStatus: String = ""
)