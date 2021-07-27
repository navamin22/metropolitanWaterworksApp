package com.example.landmarkapp.model.Retrofit.RequestValue

import com.google.gson.annotations.SerializedName

data class InsertAccountRequest (
    @SerializedName("request")
    val request: String = "",
    @SerializedName("username")
    val username: String = "",
    @SerializedName("password")
    val password: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("surname")
    val surname: String = "",
    @SerializedName("tel")
    val tel: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("userType")
    val userType: String = "",
    @SerializedName("status")
    val status: String = "",
    @SerializedName("active")
    val active: String = ""
)