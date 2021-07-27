package com.example.landmarkapp.model.Retrofit.response

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class QueueResponse (
    @SerializedName("queueId")
    val queueId: Int,
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
    @SerializedName("calledUserId")
    val calledUserId: Int,
    @SerializedName("transferUserId")
    val transferUserId: BigInteger,
    @SerializedName("queueChangeDate")
    val queueChangeDate: String = "",
    @SerializedName("queueFinishDate")
    val queueFinishDate: String = "",
    @SerializedName("queueTransferDate")
    val queueTransferDate: String = ""
)
