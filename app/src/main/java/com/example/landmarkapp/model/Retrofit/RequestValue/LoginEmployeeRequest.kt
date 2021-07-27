package com.example.landmarkapp.model.Retrofit.RequestValue

import com.google.gson.annotations.SerializedName

data class LoginEmployeeRequest (
    @SerializedName("request")
    val request: String = "",
    @SerializedName("username")
    val username: String = "",
    @SerializedName("password")
    val password: String = "",
    @SerializedName("serviceChannel")
    val serviceChannel: String = "",
    @SerializedName("serviceCounter")
    val serviceCounter: Int
)