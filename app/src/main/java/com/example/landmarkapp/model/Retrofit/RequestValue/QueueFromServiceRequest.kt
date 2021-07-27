package com.example.landmarkapp.model.Retrofit.RequestValue

import com.google.gson.annotations.SerializedName

data class QueueFromServiceRequest (
    @SerializedName("request")
    val request: String = "",
    @SerializedName("serviceChannel")
    val serviceChannel: String = "",
    @SerializedName("calledUserId")
    val calledUserId: Int,
)