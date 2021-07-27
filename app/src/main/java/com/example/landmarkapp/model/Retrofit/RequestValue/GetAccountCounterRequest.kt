package com.example.landmarkapp.model.Retrofit.RequestValue

import com.google.gson.annotations.SerializedName

data class GetAccountCounterRequest (
    @SerializedName("request")
    val request: String = "",
    @SerializedName("serviceCounter")
    val serviceCounter: Int
)