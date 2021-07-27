package com.example.landmarkapp.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.landmarkapp.Network.Repository
import com.example.landmarkapp.Network.Services.NotificationsService
import com.example.landmarkapp.model.Retrofit.response.QueueResponse
import com.example.landmarkapp.model.Retrofit.response.RateResponse
import com.example.landmarkapp.model.Retrofit.response.ReportResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DashBoardViewModel: ViewModel() {
    private val repo: Repository = Repository()
    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)
    private var queueList = ArrayList<QueueResponse>()
    private var rateList = ArrayList<RateResponse>()
    private var belowRateList = ArrayList<RateResponse>()
    private var reportHome = ArrayList<ReportResponse>()
    private var rateHome = ArrayList<RateResponse>()
    val sdf = SimpleDateFormat("dd/MM/yyyy")
    val currentDate = sdf.format(Date())
    val day = SimpleDateFormat("dd").format(Date()).toInt()
    val month = SimpleDateFormat("MM").format(Date()).toInt()
    val year = SimpleDateFormat("yyyy").format(Date()).toInt()
    var mutableLiveData: MutableLiveData<ArrayList<QueueResponse>> = repo.getQueueWithChannel()
    var mutableRateLiveData: MutableLiveData<ArrayList<RateResponse>> = repo.getRate()
    //var belowRateLiveData: MutableLiveData<ArrayList<RateResponse>> = repo.getBelowRateLiveData()

    var reportHomeLiveData: MutableLiveData<ArrayList<ReportResponse>> = repo.getReportFromDate(year, month, day)
    var rateHomeLiveData: MutableLiveData<ArrayList<RateResponse>> = repo.getRateFromDate(year, month, day)

    fun getRateFromYearMonth(year: Int, month: Int){
        repo.getRateFromYearMonthLiveData(year, month, 0)
    }

    fun getRateFromDate(year: Int, month: Int, day: Int){
        repo.getRateFromDate(year, month, day)
    }

    fun getReportFromDate(year: Int, month: Int, day: Int){
        repo.getReportFromDate(year, month, day)
    }

//    fun getBelowRate(){
//        repo.getBelowRateLiveData()
//    }

    fun belowRateCheck(context: Context){
        if (belowRateList.isNotEmpty() && belowRateList.size != 0){
            if (belowRateList[0].rateStatus == "Below"){
                val intent = Intent(context, NotificationsService::class.java)
                intent.putExtra("rateScore",belowRateList[0].rateScore)
                intent.putExtra("rateTitle",belowRateList[0].rateTitle)
                intent.putExtra("date",belowRateList[0].rateDate)
                intent.putExtra("serviceCounter",belowRateList[0].serviceCounter)
                intent.putExtra("noticeType","RateNotification")
                intent.putExtra("reasonRateLow",belowRateList[0].reasonRateLow)
                context.startService(intent)

                repo.updateBelowRate(belowRateList[0].rateId,belowRateList[0].rateStatus)

//                if (Notification(context).belowRateNotification(belowRateList[0].rateId,belowRateList[0].rateTitle,belowRateList[0].rateDate)){
//                    repo.updateBelowRate(belowRateList[0].rateId,belowRateList[0].rateStatus)
//                }
            }
        }
    }

    fun setRateList(list: ArrayList<RateResponse>){
        this.rateList = list
    }

    fun setBelowRateList(list: ArrayList<RateResponse>){
        this.belowRateList = list
    }

    fun setReportFromDateList(list: ArrayList<ReportResponse>){
        this.reportHome = list
    }

    fun setRateFromDateList(list: ArrayList<RateResponse>){
        this.rateHome = list
    }

}