package com.example.landmarkapp.model.Retrofit.RequestValue

import com.google.gson.annotations.SerializedName

data class RateRequest (
    @SerializedName("request")
    val request: String = "",
    @SerializedName("rateScore")
    val rateScore: Int,
    @SerializedName("rateTitle")
    val rateTitle: String = "",
    @SerializedName("active")
    val active: String = "",
    @SerializedName("rateStatus")
    val rateStatus: String = ""

)