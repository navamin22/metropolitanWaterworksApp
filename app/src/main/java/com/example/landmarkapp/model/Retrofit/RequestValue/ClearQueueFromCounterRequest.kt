package com.example.landmarkapp.model.Retrofit.RequestValue

import com.google.gson.annotations.SerializedName

class ClearQueueFromCounterRequest (
    val request: String = "",
    @SerializedName("serviceCounter")
    val serviceCounter: Int
)