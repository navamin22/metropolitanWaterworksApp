package com.example.landmarkapp.model.Retrofit.RequestValue

import com.google.gson.annotations.SerializedName

data class ReportBetweenDatesRequest (
    @SerializedName("request")
    val request: String = "",
    @SerializedName("firstDate")
    val firstDate: String = "",
    @SerializedName("lastDate")
    val lastDate: String = "",
    @SerializedName("serviceChannel")
    val serviceChannel: String = "",
    @SerializedName("serviceCounter")
    val serviceCounter: Int

)