package com.example.landmarkapp.model.Retrofit.RequestValue

import com.google.gson.annotations.SerializedName

data class InsertReportRequest (
    @SerializedName("request")
    val request: String = "",
    @SerializedName("queueId")
    val queueId: Int,
    @SerializedName("queueNumber")
    val queueNumber: String = "",
    @SerializedName("queueDate")
    val queueDate: String = "",
    @SerializedName("queueStatus")
    val queueStatus: String = "",
    @SerializedName("serviceChannel")
    val serviceChannel: String = "",
    @SerializedName("serviceCounter")
    val serviceCounter: Int,
    @SerializedName("endQueueUserId")
    val endQueueUserId: Int,
    @SerializedName("queueFinishDate")
    val queueFinishDate: String = "",
    @SerializedName("reportDate")
    val reportDate: String = "",
    @SerializedName("isTransfer")
    val isTransfer: String = "",
    @SerializedName("active")
    var active: String = ""
)
