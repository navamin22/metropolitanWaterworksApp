package com.example.landmarkapp.model.Retrofit.RequestValue

import com.google.gson.annotations.SerializedName

data class RateUpdateRequest (
    @SerializedName("request")
    val request: String = "",
    @SerializedName("rateId")
    val rateId: Int,
    @SerializedName("rateStatus")
    val rateStatus: String = ""
)