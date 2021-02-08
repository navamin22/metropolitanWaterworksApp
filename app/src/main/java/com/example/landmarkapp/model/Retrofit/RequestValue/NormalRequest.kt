package com.example.landmarkapp.model.Retrofit.RequestValue

import com.google.gson.annotations.SerializedName

data class NormalRequest (
    @SerializedName("request")
    val request: String = ""
)