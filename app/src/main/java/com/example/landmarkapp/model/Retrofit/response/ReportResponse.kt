package com.example.landmarkapp.model.Retrofit.response

data class ReportResponse (
    val rateId: Int,
    val rateScore: Int,
    val rateTitle: String = "",
    val rateDate: String = "",
    var active: String = ""
)