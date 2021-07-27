package com.example.landmarkapp.model.Retrofit.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse (
    @SerializedName("success")
    val isSuccess: Boolean,
    @SerializedName("message")
    val message: String = "",
)