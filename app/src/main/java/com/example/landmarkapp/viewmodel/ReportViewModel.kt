package com.example.landmarkapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.landmarkapp.Network.Repository
import com.example.landmarkapp.model.Retrofit.response.AccountResponse
import com.example.landmarkapp.model.Retrofit.response.QueueResponse
import com.example.landmarkapp.model.Retrofit.response.RateResponse
import com.example.landmarkapp.model.Retrofit.response.ReportResponse
import com.example.landmarkapp.utils.StaticData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ReportViewModel: ViewModel() {
    private val repo: Repository = Repository()
    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)
    private var queueList = ArrayList<QueueResponse>()
    private var accountList = ArrayList<AccountResponse>()
    private var reportList = ArrayList<ReportResponse>()
    private var rateList = ArrayList<RateResponse>()
    private var belowRateList = ArrayList<RateResponse>()
    private var rateHome = ArrayList<RateResponse>()
    var spinnerAccount = ArrayList<String>()
    var betweenReportLiveDate: MutableLiveData<ArrayList<ReportResponse>> = repo.getReportBetweenDates("","","","","0")
    var betweenRateLiveData: MutableLiveData<ArrayList<RateResponse>> = repo.getRateBetweenDates("", "", 0)
    var timeReportLiveData: MutableLiveData<ArrayList<ReportResponse>> = repo.getReport()
    var accountLiveData: MutableLiveData<ArrayList<AccountResponse>> = repo.getAccount()
    var calendarLiveData: MutableLiveData<Calendar> = MutableLiveData()
    var calendarListLiveData: MutableLiveData<List<Calendar>> = MutableLiveData()
    var firstDateLiveData: MutableLiveData<CharSequence> = MutableLiveData()
    var lastDateLiveData: MutableLiveData<CharSequence> = MutableLiveData()
    var firstDayLiveData: MutableLiveData<CharSequence> = MutableLiveData()
    var firstMonthLiveData: MutableLiveData<CharSequence> = MutableLiveData()
    var firstYearLiveData: MutableLiveData<CharSequence> = MutableLiveData()
    var lastDayLiveData: MutableLiveData<CharSequence> = MutableLiveData()
    var lastMonthLiveData: MutableLiveData<CharSequence> = MutableLiveData()
    var lastYearLiveData: MutableLiveData<CharSequence> = MutableLiveData()

    fun setCoupleDates(firstDay: String, firstMonth: String, firstYear: String, lastDay: String, lastMonth: String, lastYear: String){
        setFirstDay(firstDay)
        setLastDay(lastDay)
        setFirstMonth(firstMonth)
        setLastMonth(lastMonth)
        setFirstYear(firstYear)
        setLastYear(lastYear)
    }

    fun getReportBetweenDates(firstDate: String, lastDate: String, typeSelected: String, serviceChannel: String, serviceCounter: String){
        println("firstDate is : $firstDate")
        println("lastDate is : $lastDate")
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.US)
        val sdf2 = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val date = sdf.parse(firstDate)
        val date2 = sdf.parse(lastDate)

        val day1 = sdf2.format(date)
        val day2 = sdf2.format(date2)
        Log.v("First Date", day1)
        Log.v("Last Date", day2)

        repo.getReportBetweenDates(day1, day2, typeSelected, serviceChannel, serviceCounter)
    }

    fun getRateBetweenDates(firstDate: String, lastDate: String, rateAccountId: Int){
        println("firstDate is : $firstDate")
        println("lastDate is : $lastDate")
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.US)
        val sdf2 = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val date = sdf.parse(firstDate)
        val date2 = sdf.parse(lastDate)

        val day1 = sdf2.format(date)
        val day2 = sdf2.format(date2)
        Log.v("First Date", day1)
        Log.v("Last Date", day2)


        repo.getRateBetweenDates(day1, day2, rateAccountId)
    }

    fun setReportList(list: ArrayList<ReportResponse>){
        this.reportList = list
    }

    fun setRateList(list: ArrayList<RateResponse>){
        this.rateList = list
    }

    fun setAccountList(list: ArrayList<AccountResponse>){
        this.accountList = list
    }

    fun getRateFromYearMonth(year: Int, month: Int){
        repo.getRateFromYearMonthLiveData(year, month, 0)
    }

    fun setAccountSpinnerData(){
        for (i in accountList.indices){
            spinnerAccount.add("${accountList[i].name} ${accountList[i].surname}")
        }
    }

    fun setFirstDay(date: CharSequence){
        firstDayLiveData.value = date
    }

    fun getFirstDay(): LiveData<CharSequence> {
        return firstDayLiveData
    }

    fun setFirstYear(date: CharSequence){
        firstYearLiveData.value = date
    }

    fun getFirstYear(): LiveData<CharSequence> {
        return firstYearLiveData
    }

    fun setLastDay(date: CharSequence){
        lastDayLiveData.value = date
    }

    fun getLastDay(): LiveData<CharSequence> {
        return lastDayLiveData
    }

    fun setLastMonth(date: CharSequence){
        lastMonthLiveData.value = date
    }

    fun getLastMonth(): LiveData<CharSequence> {
        return lastMonthLiveData
    }

    fun setLastYear(date: CharSequence){
        lastYearLiveData.value = date
    }

    fun getLastYear(): LiveData<CharSequence> {
        return lastYearLiveData
    }

    fun setFirstMonth(date: CharSequence){
        firstMonthLiveData.value = date
    }

    fun getFirstMonth(): LiveData<CharSequence> {
        return firstMonthLiveData
    }

    fun setFirstDate(date: CharSequence){
        firstDateLiveData.value = date
    }

    fun getFirstDate(): LiveData<CharSequence> {
        return firstDateLiveData
    }

    fun setLastDate(date: CharSequence){
        lastDateLiveData.value = date
    }

    fun getLastDate(): LiveData<CharSequence> {
        return lastDateLiveData
    }

    fun setCalendar(calendar: Calendar){
        calendarLiveData.value = calendar
    }

    fun getCalendar(): LiveData<Calendar> {
        return calendarLiveData
    }

    fun setCalendarList(calendar: List<Calendar>){
        calendarListLiveData.value = calendar
    }

    fun getCalendarList(): LiveData<List<Calendar>> {
        return calendarListLiveData
    }

    fun setCalendarFromYear(){
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, StaticData.year_sel)
        calendar.set(Calendar.MONTH, StaticData.month_sel - 1)
        setCalendar(calendar)
        //selectDate.binding.dayView.setDate(calendar)
    }
}