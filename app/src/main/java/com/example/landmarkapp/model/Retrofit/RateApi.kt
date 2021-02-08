package com.example.landmarkapp.model.Retrofit

import com.example.landmarkapp.model.Retrofit.Interface.RateService
import com.example.landmarkapp.model.Retrofit.RequestValue.RateRequest
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
}