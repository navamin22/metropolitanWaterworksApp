package com.example.landmarkapp.model.Retrofit.RequestValue

import com.google.gson.annotations.SerializedName

data class RateBetweenDateRequest (
    @SerializedName("request")
    val request: String = "",
    @SerializedName("firstDate")
    val firstDate: String = "",
    @SerializedName("lastDate")
    val lastDate: String = "",
    @SerializedName("ratedAccountId")
    val ratedAccountId: Int
)