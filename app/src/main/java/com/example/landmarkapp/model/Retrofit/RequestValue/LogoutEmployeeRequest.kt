package com.example.landmarkapp.model.Retrofit.RequestValue

import com.google.gson.annotations.SerializedName

data class LogoutEmployeeRequest (
    @SerializedName("request")
    val request: String = "",
    @SerializedName("accountId")
    val accountId: Int,
    @SerializedName("status")
    val status: String = "",
    @SerializedName("serviceChannel")
    val serviceChannel: String = "",
    @SerializedName("serviceCounter")
    val serviceCounter: Int
)