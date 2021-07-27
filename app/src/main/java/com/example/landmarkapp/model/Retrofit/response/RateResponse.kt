package com.example.landmarkapp.model.Retrofit.response

import com.google.gson.annotations.SerializedName

data class RateResponse (
    @SerializedName("rateId")
    val rateId: Int,
    @SerializedName("rateScore")
    val rateScore: Int,
    @SerializedName("rateTitle")
    val rateTitle: String,
    @SerializedName("rateDate")
    val rateDate: String,
    @SerializedName("active")
    val active: String,
    @SerializedName("rateStatus")
    val rateStatus: String,
    @SerializedName("telNumber")
    val telNumber: String,
    @SerializedName("reasonRateLow")
    val reasonRateLow: String,
    @SerializedName("serviceCounter")
    val serviceCounter: String,
    @SerializedName("ratedAccountId")
    val ratedAccountId: Int
)