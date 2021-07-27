package com.example.landmarkapp.Network

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.landmarkapp.Network.Services.NotificationsService
import com.example.landmarkapp.model.Retrofit.*
import com.example.landmarkapp.model.Retrofit.RequestValue.*
import com.example.landmarkapp.model.Retrofit.response.*
import com.example.landmarkapp.ui.Activity.LoginCounterActivity
import com.example.landmarkapp.ui.Activity.PRActivity2
import com.example.landmarkapp.utils.StaticData
import com.example.landmarkapp.utils.toast
import com.example.landmarkapp.viewmodel.PRViewModel
import com.example.landmarkapp.viewmodel.PRViewModel2
import com.example.landmarkapp.viewmodel.RateViewModel
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Repository {
    private var queueList = ArrayList<QueueResponse>()
    private var waitingQueueList = ArrayList<QueueResponse>()
    private var soundQueueList = ArrayList<SoundQueueResponse>()
    private var accountList = ArrayList<AccountResponse>()
    private var reportList = ArrayList<ReportResponse>()
    private var rateList = ArrayList<RateResponse>()
    private var belowRateList = ArrayList<RateResponse>()
    private var mutableLiveData = MutableLiveData<ArrayList<QueueResponse>>()
    private var mutableLiveData2 = MutableLiveData<ArrayList<QueueResponse>>()
    private var soundQueueLiveData = MutableLiveData<ArrayList<SoundQueueResponse>>()
    private var accountLiveData = MutableLiveData<ArrayList<AccountResponse>>()
    private var reportLiveData = MutableLiveData<ArrayList<ReportResponse>>()
    private var rateLiveData = MutableLiveData<ArrayList<RateResponse>>()
    private var accountRateCounter = MutableLiveData<AccountResponse>()
    var job: Job? = null

    fun loginEmployee(activity: LoginCounterActivity, context: Context, username: String, password: String, serviceChannel: String, serviceCounter: Int){
        val call = AccountApi()
            .employeeLogin(LoginEmployeeRequest("login_employee",username, password, serviceChannel, serviceCounter))
        call.enqueue(object : retrofit2.Callback<LoginEmployeeResponse>{
            override fun onResponse(call: Call<LoginEmployeeResponse>, response: Response<LoginEmployeeResponse>) {
                val result = response.body()
                if (result!!.isSuccess){
                    val sp: SharedPreferences = context.getSharedPreferences("LOGIN_EMPLOYEE", Context.MODE_PRIVATE)
                    val editor = sp.edit()
                    editor.putInt("accountId",result.accountId)
                    editor.putString("username",result.username)
                    editor.putString("password",result.password)
                    editor.putString("name",result.name)
                    editor.putString("surname",result.surname)
                    editor.putString("email",result.email)
                    editor.putString("tel",result.tel)
                    editor.putString("userType",result.userType)
                    editor.putString("status",result.status)
                    editor.putString("active",result.active)

                    StaticData.accountId = result.accountId
                    StaticData.username = result.username
                    StaticData.password = result.password
                    StaticData.name = result.name
                    StaticData.surname = result.surname
                    StaticData.email = result.email
                    StaticData.tel = result.tel
                    StaticData.userType = result.userType
                    StaticData.status = result.status
                    StaticData.active = result.active

                    editor.apply()
                    println(result.message)
                    context.toast(result.message)

                    val intent = Intent(context,PRActivity2::class.java)
                    context.startActivity(intent)
                    activity.finish()

                } else {
                    println(result.message)
                }
                Log.v("Error retrofit","Result is $result")
            }

            override fun onFailure(call: Call<LoginEmployeeResponse>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })
    }

    fun logoutEmployee(activity: PRActivity2, context: Context, accountId: Int, status: String, serviceChannel: String, serviceCounter: Int){
        val call = AccountApi()
            .employeeLogout(LogoutEmployeeRequest("logout_employee",accountId, status, serviceChannel, serviceCounter))
        call.enqueue(object : retrofit2.Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val result = response.body()
                Log.v("Show Result","Result is $result")
                val sp: SharedPreferences = context.getSharedPreferences("LOGIN_EMPLOYEE", Context.MODE_PRIVATE)
                val editor = sp.edit()
                editor.clear().apply()
                val intent = Intent(activity,LoginCounterActivity::class.java)
                context.startActivity(intent)
                activity.finish()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })
    }

    fun insertAccount(username: String, password: String, name: String, surname: String, tel: String, email: String, userType: String, status: String, active: String){
        val call = AccountApi()
            .insertAccount(InsertAccountRequest("insert_account",username, password, name, surname, tel, email, userType, status, active))
        call.enqueue(object : retrofit2.Callback<RegisterResponse>{
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                val result = response.body()
                Log.v("Show Result","Result is $result")
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })
    }

    fun insertTransactionLogin(accountId: Int, userType: String, active: String, status: String, serviceChannel: String, serviceCounter: Int, create_date: String, login_date: String, logout_date: String){
        val call = AccountApi()
            .insertTLogin(InsertTLoginRequest("",accountId,userType,active,status,serviceChannel,serviceCounter,create_date,login_date,logout_date))
        call.enqueue(object : retrofit2.Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val result = response.body()
                Log.v("Show Result","Result is $result")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })
    }

    fun insertQueue(queueNum: String, time: String, serviceChannel: String){
        val call = QueueApi()
            .insertQueue(QueueRequest("insert_queue",queueNum,time,"Prepare",serviceChannel))
        call.enqueue(object : retrofit2.Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val result = response.body()
                Log.v("Error retrofit","Result is $result")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })
    }

    fun insertSoundQueue(queueId: Int, queueNum: String, message: String, active: String, serviceChannel: String, serviceCounter: Int){
        val call = SoundQueueApi()
            .insertSoundQueue(SoundQueueRequest("insert_sound_queue", queueId, queueNum, message, active, serviceChannel, serviceCounter))
        call.enqueue(object : retrofit2.Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val result = response.body()
                Log.v("Error retrofit","Result is $result")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })
    }

    fun insertTransactionQueue(request: String, queueId: String, queueNum: String, queueDate: String, queueStatus: String, serviceChannel: String, serviceCounter: Int
                               ,pressQueueType: String, calledUserId: Int, transferUserId: Int, queueChangeDate: String, queueTransferDate: String, queueFinishDate: String, transactionDate: String){
        val call = TransactionQueueApi()
            .insertTQueue(TransactionQueueRequest(request, queueId, queueNum, queueDate, queueStatus, serviceChannel, serviceCounter, pressQueueType, calledUserId, transferUserId, queueChangeDate, queueTransferDate, queueFinishDate, transactionDate))
        call.enqueue(object : retrofit2.Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val result = response.body()
                Log.v("Show Result","Result is $result")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })
    }

    fun insertRate(rateScore: Int, rateTitle: String, active: String, rateStatus: String, telNumber: String, reasonRateLow: String, serviceChannel: Int, userId: Int){
        val call = RateApi()
            .insertRate(RateRequest("insert_rate", rateScore, rateTitle, active, rateStatus, telNumber, reasonRateLow, serviceChannel, userId))
        call.enqueue(object : retrofit2.Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val result = response.body()
                Log.v("Error retrofit","Result is $result")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })
    }

    fun insertReportData(queueId: Int, queueNum: String, queueDate: String, queueStatus: String, serviceChannel: String, serviceCounter: Int,endQueueUserId: Int, queueFinishDate: String, reportDate: String, isTransfer: String, active: String){
        val call = ReportApi()
            .insertReportData(InsertReportRequest("insert_report_data", queueId,queueNum,queueDate, queueStatus, serviceChannel, serviceCounter, endQueueUserId, queueFinishDate, reportDate, isTransfer, active))
        call.enqueue(object : retrofit2.Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val result = response.body()
                Log.v("Error retrofit","Result is $result")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })
    }

    fun insertReport(queueId: Int, queueNum: String, serviceChannel: String, employee: String, active: String){
        val call = ReportApi()
            .insertReport(ReportRequest("insert_report", queueId, queueNum, serviceChannel, employee, active))
        call.enqueue(object : retrofit2.Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val result = response.body()
                Log.v("Error retrofit","Result is $result")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })
    }

    fun updateSoundQueueData(soundQueueId: Int, queueId: Int, active: String){
        val call = SoundQueueApi().updateSoundQueue(SoundQueueUpdateRequest("update_sound_queue",soundQueueId,queueId,active))
        call.enqueue(object : retrofit2.Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val result = response.body()
                println("Result is $result")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })
    }

    fun updateQueueData(queueId: String, queueStatus: String, channelNo: Int, userId: Int, changeQueueDate: String){
        val call = QueueApi()
            .updateQueue(QueueUpdateRequest("update_queue",queueId,queueStatus,channelNo,userId,changeQueueDate))
        call.enqueue(object : retrofit2.Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val result = response.body()
                println("Result is $result")
//                println("UserId is $userId")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })
    }

    fun transferQueueData(queueId: String, queueStatus: String, serviceChannel: String, channelNo: Int, userId: Int, changeQueueDate: String, transferQueueDate: String){
        val call = QueueApi()
            .transferQueue(TransferQueueRequest("transfer_queue",queueId,queueStatus,serviceChannel,channelNo,userId,changeQueueDate,transferQueueDate))
        call.enqueue(object : retrofit2.Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val result = response.body()
                println("Result is $result")
//                println("UserId is $userId")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })
    }

    fun finishQueueData(queueId: String, queueStatus: String, channelNo: Int, userId: Int, finishDate: String){
        val call = QueueApi()
            .finishQueue(QueueFinishRequest("finish_queue",queueId,queueStatus,channelNo,userId,finishDate))
        call.enqueue(object : retrofit2.Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val result = response.body()
                println("Result is $result")
//                println("UserId is $userId")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })
    }

    fun getAccountFromCounter(serviceCounter: Int, viewModel: RateViewModel): MutableLiveData<AccountResponse>{
        val call = AccountApi()
            .getAccountFromCounter(GetAccountCounterRequest("get_account_from_counter",serviceCounter))
        call.enqueue(object : retrofit2.Callback<AccountResponse>{
            override fun onResponse(
                call: Call<AccountResponse>,
                response: Response<AccountResponse>
            ) {
                val result = response.body()
                result.let {
                    if (it != null){
                        accountRateCounter.value = it
                        viewModel.setEmpId(it.accountId.toString())
                        viewModel.setName(it.name)
                        viewModel.setSurname(it.surname)
                    }
                }
                Log.v("Show Result","Result is $result")
            }

            override fun onFailure(call: Call<AccountResponse>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })

        return accountRateCounter
    }

    fun getSoundQueueLiveData(): MutableLiveData<ArrayList<SoundQueueResponse>>{
        val call = SoundQueueApi().getSoundQueue(NormalRequest("get_sound_queue"))
        call.enqueue(object : retrofit2.Callback<List<SoundQueueResponse>> {
            override fun onResponse(
                call: Call<List<SoundQueueResponse>>,
                response: Response<List<SoundQueueResponse>>

            ) {
                val result = response.body()
                soundQueueList.clear()
                println("Result is $result")
                result?.let {
                    soundQueueList.addAll(it)
                    soundQueueLiveData.value = soundQueueList
                }

            }

            override fun onFailure(call: Call<List<SoundQueueResponse>>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })

        return soundQueueLiveData
    }

    fun getQueueLiveData(): MutableLiveData<ArrayList<QueueResponse>>{
        val call = QueueApi().getQueue(NormalRequest("get_queue"))
        call.enqueue(object : retrofit2.Callback<List<QueueResponse>> {
            override fun onResponse(
                call: Call<List<QueueResponse>>,
                response: Response<List<QueueResponse>>
            ) {
                val result = response.body()
                queueList.clear()
                //println("Result is $result")
                result?.let { queueList.addAll(it) }
                mutableLiveData.value = queueList
            }

            override fun onFailure(call: Call<List<QueueResponse>>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })

        return mutableLiveData
    }

    fun getWaitingQueueLiveData(): MutableLiveData<ArrayList<QueueResponse>>{
        val call = QueueApi().getQueue(NormalRequest("get_queue"))
        call.enqueue(object : retrofit2.Callback<List<QueueResponse>> {
            override fun onResponse(
                call: Call<List<QueueResponse>>,
                response: Response<List<QueueResponse>>
            ) {
                val result = response.body()
                waitingQueueList.clear()
                //println("Result is $result")
                result?.let { waitingQueueList.addAll(it) }
                mutableLiveData2.value = waitingQueueList
            }

            override fun onFailure(call: Call<List<QueueResponse>>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })

        return mutableLiveData2
    }

    fun getQueueWithChannel(): MutableLiveData<ArrayList<QueueResponse>>{
        val call = QueueApi().getQueue(NormalRequest("get_queue_with_channel"))
        call.enqueue(object : retrofit2.Callback<List<QueueResponse>> {
            override fun onResponse(
                call: Call<List<QueueResponse>>,
                response: Response<List<QueueResponse>>
            ) {
                val result = response.body()
                queueList.clear()
                //println("Result is $result")
                result?.let { queueList.addAll(it) }
                mutableLiveData.value = sortLatestDate(queueList)
//                mutableLiveData.value = queueList
            }

            override fun onFailure(call: Call<List<QueueResponse>>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })

        return mutableLiveData
    }

    fun sortLatestDate(list: ArrayList<QueueResponse>): ArrayList<QueueResponse>{
        //val arrList = list
        val arrList = ArrayList<QueueResponse>()
        val secondList = ArrayList<Long>()
        val secondListSort = ArrayList<Long>()
        val secondRevSort = ArrayList<Long>()
        val indexListSorted = ArrayList<Int>()
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val currentTime = Calendar.getInstance().time

        for (i in list.indices){
            //println("Queue is ${list[i].queueNum}")
            val date = sdf.parse(list[i].queueChangeDate)
            val diff = currentTime.time - date.time
            val seconds = diff / 1000
            secondList.add(seconds)
            secondListSort.add(seconds)
        }

        secondListSort.sort()
        for (index in secondList.size -1 downTo 0){
            secondRevSort.add(secondListSort[index])
        }


        println("Second list is $secondList")
        println("Second list sort is $secondListSort")
        println("Second list rev is $secondRevSort")

        for (i in secondList.indices){
            for (j in secondRevSort.indices){
                if (secondList[i] == secondRevSort[j]){
                    indexListSorted.add(j)
                }
            }
        }

        println("Index sort is $indexListSorted")

        for (i in indexListSorted.size -1 downTo 0){
            arrList.add(list[indexListSorted[i]])
        }

        return arrList

//        val diff: Long = date1.getTime() - date2.getTime()
//        val seconds = diff / 1000
//        val minutes = seconds / 60
//        val hours = minutes / 60
//        val days = hours / 24
    }

    fun getQueueChannelLiveData(serviceChannel: String, userId: Int): MutableLiveData<ArrayList<QueueResponse>>{
        println("User Id is $userId")
        val call = QueueApi().getQueueFromService(QueueFromServiceRequest("get_queue_from_service",serviceChannel,userId))
        call.enqueue(object : retrofit2.Callback<List<QueueResponse>> {
            override fun onResponse(
                call: Call<List<QueueResponse>>,
                response: Response<List<QueueResponse>>
            ) {
                val result = response.body()
                queueList.clear()
                println("Result is $result")
                result?.let { queueList.addAll(it) }
                mutableLiveData.value = queueList
            }

            override fun onFailure(call: Call<List<QueueResponse>>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })

        return mutableLiveData
    }

    fun getAccount(): MutableLiveData<ArrayList<AccountResponse>>{
        val call = AccountApi().getAccount(NormalRequest("get_accounts"))
        call.enqueue(object : retrofit2.Callback<List<AccountResponse>>{
            override fun onResponse(
                call: Call<List<AccountResponse>>,
                response: Response<List<AccountResponse>>
            ) {
                val result = response.body()
                accountList.clear()
                result?.let { accountList.addAll(it) }
                accountLiveData.value = accountList
            }

            override fun onFailure(call: Call<List<AccountResponse>>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })

        return accountLiveData
    }

    fun getReport(): MutableLiveData<ArrayList<ReportResponse>>{
        val call = ReportApi().getReport(NormalRequest("get_report"))
        call.enqueue(object : retrofit2.Callback<List<ReportResponse>>{
            override fun onResponse(
                call: Call<List<ReportResponse>>,
                response: Response<List<ReportResponse>>
            ) {
                val result = response.body()
                reportList.clear()
                result?.let { reportList.addAll(it) }
                reportLiveData.value = reportList
            }

            override fun onFailure(call: Call<List<ReportResponse>>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })

        return reportLiveData
    }

    fun getReportFromDate(year: Int, month: Int, day: Int): MutableLiveData<ArrayList<ReportResponse>>{
        val list = ArrayList<ReportResponse>()
        val call = ReportApi().getReportFromDate(ReportDateRequest("get_report_from_date",year, month, day))
        call.enqueue(object : retrofit2.Callback<List<ReportResponse>>{
            override fun onResponse(
                call: Call<List<ReportResponse>>,
                response: Response<List<ReportResponse>>
            ) {
                val result = response.body()
                reportList.clear()
                result?.let { reportList.addAll(it) }
                reportLiveData.value = reportList

            }

            override fun onFailure(call: Call<List<ReportResponse>>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })
        println("List size is ${list.size}")

        return reportLiveData
    }

    fun getRate(): MutableLiveData<ArrayList<RateResponse>>{
        val call = RateApi().getRate(NormalRequest("get_rate"))
        call.enqueue(object : retrofit2.Callback<List<RateResponse>>{
            override fun onResponse(
                call: Call<List<RateResponse>>,
                response: Response<List<RateResponse>>
            ) {
                val result = response.body()
                rateList.clear()
                result?.let { rateList.addAll(it) }
                rateLiveData.value = rateList
            }

            override fun onFailure(call: Call<List<RateResponse>>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })

        return rateLiveData
    }

    fun getRateFromDate(year: Int, month: Int, day: Int): MutableLiveData<ArrayList<RateResponse>>{
        val call = RateApi().getRateFromDate(RateFromYearMonthRequest("get_rate_from_date",year, month, day))
        call.enqueue(object : retrofit2.Callback<List<RateResponse>>{
            override fun onResponse(
                call: Call<List<RateResponse>>,
                response: Response<List<RateResponse>>
            ) {
                val result = response.body()
                rateList.clear()
                result?.let { rateList.addAll(it) }
                rateLiveData.value = rateList

            }

            override fun onFailure(call: Call<List<RateResponse>>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })


        return rateLiveData
    }

    fun getBelowRateLiveData(context: Context): MutableLiveData<ArrayList<RateResponse>>{
        val call = RateApi().getRate(NormalRequest("get_below_rate"))
        call.enqueue(object : retrofit2.Callback<List<RateResponse>>{
            override fun onResponse(
                call: Call<List<RateResponse>>,
                response: Response<List<RateResponse>>
            ) {
                val result = response.body()
                belowRateList.clear()
                println("Result is $result")
                result?.let {
                    belowRateList.addAll(it)
                    belowRateCheck(context)
                }
            }

            override fun onFailure(call: Call<List<RateResponse>>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })
        return rateLiveData
    }

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

                updateBelowRate(belowRateList[0].rateId,belowRateList[0].rateStatus)

//                if (Notification(context).belowRateNotification(belowRateList[0].rateId,belowRateList[0].rateTitle,belowRateList[0].rateDate)){
//                    repo.updateBelowRate(belowRateList[0].rateId,belowRateList[0].rateStatus)
//                }
            }
        }
    }

    fun updateBelowRate(rateId: Int, rateStatus: String){
        val call = RateApi()
            .updateRate(
                RateUpdateRequest(
                    "update_rate",
                    rateId,
                    rateStatus
                )
            )
        call.enqueue(object : retrofit2.Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val result = response.body()
                println("Result is $result")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })
    }

    fun getRateFromYearMonthLiveData(year: Int, month: Int, day: Int): MutableLiveData<ArrayList<RateResponse>>{
        val call = RateApi().getRateFromYearMonth(RateFromYearMonthRequest("get_rate_from_year_month",year, month, day))
        call.enqueue(object : retrofit2.Callback<List<RateResponse>>{
            override fun onResponse(
                call: Call<List<RateResponse>>,
                response: Response<List<RateResponse>>
            ) {
                val result = response.body()
                rateList.clear()
                result?.let { rateList.addAll(it) }
                rateLiveData.value = rateList
            }

            override fun onFailure(call: Call<List<RateResponse>>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })

        return rateLiveData
    }

    fun getReportBetweenDates(firstDate: String, lastDate: String, typeSelected: String, serviceChannel: String, serviceCounter: String): MutableLiveData<ArrayList<ReportResponse>>{
        var request = ""
        when (typeSelected) {
            "เลือกทั้งหมด" -> {
                request = "get_report_between_dates"
            }
            "การให้บริการ" -> {
                request = "get_report_between_dates_by_service"
            }
            "ช่องบริการ" -> {
                request = "get_report_between_dates_by_counter"
            }
        }

        println("FirstDate before query is $firstDate")
        println("LastDate before query is $lastDate")
        val call = ReportApi().getReportBetweenDates(ReportBetweenDatesRequest(request,firstDate,lastDate,serviceChannel,serviceCounter.toInt()))
        call.enqueue(object : retrofit2.Callback<List<ReportResponse>>{
            override fun onResponse(
                call: Call<List<ReportResponse>>,
                response: Response<List<ReportResponse>>
            ) {
                val result = response.body()
                reportList.clear()
                result?.let { reportList.addAll(it) }
                reportLiveData.value = reportList
            }

            override fun onFailure(call: Call<List<ReportResponse>>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })

        return reportLiveData
    }

    fun getRateBetweenDates(firstDate: String, lastDate: String, rateAccountId: Int): MutableLiveData<ArrayList<RateResponse>>{
        var request = ""
        request = if (rateAccountId == 0){
            "get_rate_between_dates"
        } else {
            "get_rate_between_dates_employee"
        }
        val call = RateApi().getRateBetweenDates(RateBetweenDateRequest(request,firstDate,lastDate,rateAccountId))
        call.enqueue(object : retrofit2.Callback<List<RateResponse>>{
            override fun onResponse(
                call: Call<List<RateResponse>>,
                response: Response<List<RateResponse>>
            ) {
                val result = response.body()
                rateList.clear()
                result?.let { rateList.addAll(it) }
                rateLiveData.value = rateList
            }

            override fun onFailure(call: Call<List<RateResponse>>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })

        return rateLiveData
    }

    fun clearQueue(viewModel: PRViewModel){
        val call = QueueApi().clearQueue(NormalRequest("clear_queue"))
        call.enqueue(object : retrofit2.Callback<String>{
            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                val result = response.body()
                println(result)
                viewModel.getQueue()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })
    }

    fun clearQueue2(viewModel: PRViewModel2){
        val call = QueueApi().clearQueue(NormalRequest("clear_queue"))
        call.enqueue(object : retrofit2.Callback<String>{
            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                val result = response.body()
                println(result)
                viewModel.getQueue()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })
    }

    fun resetQueueOperation(){
        val call = QueueApi().clearQueue(NormalRequest("clear_queue"))
        call.enqueue(object : retrofit2.Callback<String>{
            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                val result = response.body()
                println(result)
                Log.d("Dashboard Activity","Receiver: Reset Queue Completed" + Date().toString())

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })
    }

//    fun clearQueueFromService(viewModel: PRViewModel2, serviceChannel: String, userId: Int){
//        val call = QueueApi().clearQueueFromService(QueueFromServiceRequest("clear_queue_from_service",serviceChannel,userId))
//        call.enqueue(object : retrofit2.Callback<String>{
//            override fun onResponse(
//                call: Call<String>,
//                response: Response<String>
//            ) {
//                val result = response.body()
//                println(result)
//                viewModel.getQueue()
//            }
//
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                println("Error is : ${t.message}")
//            }
//        })
//    }

}