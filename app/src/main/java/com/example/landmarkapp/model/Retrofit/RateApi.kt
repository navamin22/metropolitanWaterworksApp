package com.example.landmarkapp.model.Retrofit

import com.example.landmarkapp.model.Retrofit.Interface.RateService
import com.example.landmarkapp.model.Retrofit.RequestValue.*
import com.example.landmarkapp.model.Retrofit.response.RateResponse
import com.example.landmarkapp.utils.StaticData.Companion.folder
import com.example.landmarkapp.utils.StaticData.Companion.php
import com.example.landmarkapp.utils.StaticData.Companion.rate_php
import com.example.landmarkapp.utils.StaticData.Companion.url
import com.example.landmarkapp.utils.TruthCertificates
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RateApi {
    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val api = Retrofit.Builder()
        .baseUrl(url + folder + php + rate_php)
        //.client(TruthCertificates.okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
        .create(RateService::class.java)

    fun insertRate(request: RateRequest): Call<String> {
        return api.insertRate(request)
    }

    fun getRate(request: NormalRequest): Call<List<RateResponse>> {
        return api.getRate(request)
    }

    fun getRateFromDate(request: RateFromYearMonthRequest): Call<List<RateResponse>> {
        return api.getRateFromDate(request)
    }

    fun getRateFromYearMonth(request: RateFromYearMonthRequest): Call<List<RateResponse>>{
        return api.getRateFromYearMonth(request)
    }

    fun getRateBetweenDates(request: RateBetweenDateRequest): Call<List<RateResponse>>{
        return api.getRateBetweenDates(request)
    }

    fun updateRate(request: RateUpdateRequest): Call<String> {
        return api.updateRate(request)
    }
}