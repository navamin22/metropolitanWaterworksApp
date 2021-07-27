package com.example.landmarkapp.model.Retrofit.response

import com.google.gson.annotations.SerializedName

data class AccountResponse (
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
    @SerializedName("active")
    val active: String = "",
    @SerializedName("status")
    val status: String = "",
    @SerializedName("create_date")
    val create_date: String = "",
    @SerializedName("modify_date")
    val modify_date: String = ""

)