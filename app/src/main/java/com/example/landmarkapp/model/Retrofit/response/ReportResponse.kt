package com.example.landmarkapp.model.Retrofit.response

import com.google.gson.annotations.SerializedName

data class ReportResponse (
    @SerializedName("reportId")
    val reportId: Int,
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
    @SerializedName("latestCallDate")
    val latestCallDate: String = "",
    @SerializedName("queueFinishDate")
    val queueFinishDate: String = "",
    @SerializedName("reportDate")
    val reportDate: String = "",
    @SerializedName("isTransfer")
    val isTransfer: String = "",
    @SerializedName("active")
    var active: String = ""
)