package com.example.landmarkapp.model.Retrofit.Interface

import com.example.landmarkapp.model.Retrofit.RequestValue.NormalRequest
import com.example.landmarkapp.model.Retrofit.RequestValue.RateRequest
import com.example.landmarkapp.model.Retrofit.RequestValue.ReportRequest
import com.example.landmarkapp.model.Retrofit.response.ReportResponse
import com.example.landmarkapp.utils.StaticData.Companion.report_php
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ReportInterface {

    @POST(report_php)
    fun insertReport(
        @Body request: ReportRequest
    ): Call<String>

    @POST(report_php)
    fun getReport(
        @Body request: NormalRequest
    ): Call<List<ReportResponse>>

}