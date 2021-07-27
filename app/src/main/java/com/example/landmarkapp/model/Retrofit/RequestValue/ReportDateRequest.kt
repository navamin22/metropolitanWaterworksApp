package com.example.landmarkapp.model.Retrofit.RequestValue

import com.google.gson.annotations.SerializedName

data class ReportDateRequest (
    @SerializedName("request")
    val request: String = "",
    @SerializedName("year")
    val year: Int,
    @SerializedName("month")
    val month: Int,
    @SerializedName("day")
    val day: Int
)