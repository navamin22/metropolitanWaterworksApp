package com.example.landmarkapp.model.Retrofit.RequestValue

import com.google.gson.annotations.SerializedName

data class QueueFinishRequest (
    @SerializedName("request")
    val request: String = "",
    @SerializedName("queueId")
    val queueId: String = "",
    @SerializedName("queueStatus")
    val queueStatus: String = "",
    @SerializedName("serviceCounter")
    val serviceCounter: Int,
    @SerializedName("calledUserId")
    val callUserId: Int,
    @SerializedName("queueFinishDate")
    val queueFinishDate: String
)