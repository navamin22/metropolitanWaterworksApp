package com.example.landmarkapp.model.Retrofit.RequestValue

import com.google.gson.annotations.SerializedName

data class ReportRequest (
    @SerializedName("request")
    val request: String = "",
    @SerializedName("queueId")
    val queueId: Int,
    @SerializedName("queueNumber")
    val queueNumber: String = "",
    @SerializedName("serviceChannel")
    val serviceChannel: String = "",
    @SerializedName("employee")
    val employee: String = "",
    @SerializedName("active")
    var active: String = ""
)