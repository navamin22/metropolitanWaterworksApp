package com.example.landmarkapp.model.Retrofit.Interface

import com.example.landmarkapp.model.Retrofit.RequestValue.RateRequest
import com.example.landmarkapp.utils.StaticData.Companion.rate_php
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RateService {
    @POST(rate_php)
    fun insertRate(
        @Body request: RateRequest
    ): Call<String>
}