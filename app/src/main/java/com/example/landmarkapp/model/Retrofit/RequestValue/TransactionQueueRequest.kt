package com.example.landmarkapp.model.Retrofit.RequestValue

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class TransactionQueueRequest (
    @SerializedName("request")
    val request: String = "",
    @SerializedName("queueId")
    val queueId: String = "",
    @SerializedName("queueNumber")
    val queueNum: String = "",
    @SerializedName("queueDate")
    val queueDate: String = "",
    @SerializedName("queueStatus")
    val queueStatus: String = "",
    @SerializedName("serviceChannel")
    val serviceChannel: String = "",
    @SerializedName("serviceCounter")
    val serviceCounter: Int,
    @SerializedName("pressQueueType")
    val pressQueueType: String,
    @SerializedName("calledUserId")
    val calledUserId: Int,
    @SerializedName("transferUserId")
    val transferUserId: Int,
    @SerializedName("queueChangeDate")
    val queueChangeDate: String = "",
    @SerializedName("queueTransferDate")
    val queueTransferDate: String = "",
    @SerializedName("queueFinishDate")
    val queueFinishDate: String = "",
    @SerializedName("transactionDate")
    val transactionDate: String = ""
)