package com.example.landmarkapp.model.Retrofit.RequestValue

import com.google.gson.annotations.SerializedName

data class InsertTLoginRequest (
    @SerializedName("request")
    val request: String = "",
    @SerializedName("accountId")
    val accountId: Int,
    @SerializedName("userType")
    val userType: String = "",
    @SerializedName("active")
    val active: String = "",
    @SerializedName("status")
    val status: String = "",
    @SerializedName("serviceChannel")
    val serviceChannel: String = "",
    @SerializedName("serviceCounter")
    val serviceCounter: Int,
    @SerializedName("create_date")
    val create_date: String = "",
    @SerializedName("login_date")
    val login_date: String = "",
    @SerializedName("logout_date")
    val logout_date: String = ""
)