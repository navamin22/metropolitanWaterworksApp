package com.example.landmarkapp.model.Retrofit.Interface

import com.example.landmarkapp.model.Retrofit.RequestValue.*
import com.example.landmarkapp.model.Retrofit.response.RateResponse
import com.example.landmarkapp.utils.StaticData.Companion.rate_php
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RateService {
    @POST(rate_php)
    fun insertRate(
        @Body request: RateRequest
    ): Call<String>

    @POST(rate_php)
    fun getRate(
        @Body request: NormalRequest
    ): Call<List<RateResponse>>

    @POST(rate_php)
    fun getRateFromYearMonth(
        @Body request: RateFromYearMonthRequest
    ): Call<List<RateResponse>>

    @POST(rate_php)
    fun getRateFromDate(
        @Body request: RateFromYearMonthRequest
    ): Call<List<RateResponse>>

    @POST(rate_php)
    fun getRateBetweenDates(
        @Body request: RateBetweenDateRequest
    ): Call<List<RateResponse>>

    @POST(rate_php)
    fun updateRate(
        @Body request: RateUpdateRequest
    ): Call<String>
}