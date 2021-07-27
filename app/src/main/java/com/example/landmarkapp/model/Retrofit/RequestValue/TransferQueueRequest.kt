package com.example.landmarkapp.model.Retrofit.RequestValue

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class TransferQueueRequest (
    @SerializedName("request")
    val request: String = "",
    @SerializedName("queueId")
    val queueId: String = "",
    @SerializedName("queueStatus")
    val queueStatus: String = "",
    @SerializedName("serviceChannel")
    val serviceChannel: String = "",
    @SerializedName("serviceCounter")
    val serviceCounter: Int,
    @SerializedName("transferUserId")
    val transferUserId: Int,
    @SerializedName("queueChangeDate")
    val queueChangeDate: String = "",
    @SerializedName("queueTransferDate")
    val queueTransferDate: String = ""

)