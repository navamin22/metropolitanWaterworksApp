package com.example.landmarkapp.model.Retrofit.response

import com.google.gson.annotations.SerializedName

data class QueueResponse (
    @SerializedName("queueId")
    val queueId: Int,
    @SerializedName("queueNumber")
    val queueNum: String = "",
    @SerializedName("queueDate")
    val queueDate: String = "",
    @SerializedName("queueStatus")
    val queueStatus: String = ""
)
