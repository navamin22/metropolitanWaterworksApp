package com.example.landmarkapp.model.Retrofit.response

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class LoginEmployeeResponse (
    @SerializedName("success")
    val isSuccess: Boolean,
    @SerializedName("message")
    val message: String = "",
    @SerializedName("accountId")
    val accountId: Int,
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