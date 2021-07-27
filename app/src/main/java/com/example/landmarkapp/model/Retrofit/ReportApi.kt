package com.example.landmarkapp.model.Retrofit

import com.example.landmarkapp.model.Retrofit.Interface.ReportInterface
import com.example.landmarkapp.model.Retrofit.RequestValue.*
import com.example.landmarkapp.model.Retrofit.response.ReportResponse
import com.example.landmarkapp.utils.StaticData.Companion.folder
import com.example.landmarkapp.utils.StaticData.Companion.php
import com.example.landmarkapp.utils.StaticData.Companion.report_php
import com.example.landmarkapp.utils.StaticData.Companion.url
import com.example.landmarkapp.utils.TruthCertificates
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class ReportApi {
    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val api = Retrofit.Builder()
        .baseUrl(url + folder + php + report_php)
        //.client(TruthCertificates.okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
        .create(ReportInterface::class.java)

    fun insertReport(request: ReportRequest): Call<String> {
        return api.insertReport(request)
    }

    fun insertReportData(request: InsertReportRequest): Call<String> {
        return api.insertReportData(request)
    }

    fun getReport(request: NormalRequest): Call<List<ReportResponse>>{
        return api.getReport(request)
    }

    fun getReportFromDate(request: ReportDateRequest): Call<List<ReportResponse>>{
        return api.getReportFromDate(request)
    }

    fun getReportBetweenDates(request: ReportBetweenDatesRequest): Call<List<ReportResponse>>{
        return api.getReportBetweenDates(request)
    }

}