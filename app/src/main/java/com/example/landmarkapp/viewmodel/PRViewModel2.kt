package com.example.landmarkapp.viewmodel

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.landmarkapp.Network.Repository
import com.example.landmarkapp.R
import com.example.landmarkapp.model.Retrofit.response.QueueResponse
import com.example.landmarkapp.ui.Activity.PRActivity2
import com.example.landmarkapp.ui.DialogFragment.LoginCounter
import com.example.landmarkapp.ui.DialogFragment.TransferQueueDialog
import com.example.landmarkapp.utils.StaticData
import com.example.landmarkapp.utils.toast
import kotlinx.android.synthetic.main.dialog_submit_password.view.*
import kotlinx.coroutines.*
import java.math.BigInteger
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PRViewModel2(app: Application) : AndroidViewModel(app) {
    private val repo: Repository = Repository()
    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)
    private var queueList = ArrayList<QueueResponse>()
    var mutableLiveData: MutableLiveData<ArrayList<QueueResponse>> = repo.getQueueChannelLiveData(
        StaticData.currentChannelType,
        StaticData.accountId
    )
    //var mutableLiveData = MutableLiveData<ArrayList<QueueResponse>>()
    var currentQueueLiveData = MutableLiveData<CharSequence>()
    var nextQueueLiveData = MutableLiveData<CharSequence>()
    var waitingQueueLiveData = MutableLiveData<Int>()
    var skipQueueLiveData = MutableLiveData<QueueResponse>()

    fun getQueueFromTimer(){
        uiScope.launch(Dispatchers.IO) {
            while (true){
                autoQueueTiming()
            }
        }
    }

    fun showCurrentQueue(){
        if (queueList.isNotEmpty() && queueList.size != 0){
            currentQueueLiveData.value = queueList[0].queueNum
        } else {
            currentQueueLiveData.value = "-"
        }
    }

    fun showNextQueue(){
        if (queueList.isNotEmpty() && queueList.size >= 2){
            nextQueueLiveData.value = queueList[1].queueNum
        } else {
            nextQueueLiveData.value = "-"
        }
    }

    fun showWaitingQueueCount(){
        if (queueList.isNotEmpty() && queueList.size > 0){
            waitingQueueLiveData.value = queueList.size -1
        } else {
            waitingQueueLiveData.value = 0
        }
    }

    fun getCurrentQueueShow(): LiveData<CharSequence> {
        return currentQueueLiveData
    }

    fun getCurrentNextQueueShow(): LiveData<CharSequence> {
        return nextQueueLiveData
    }

    private fun setSkipQueue(){
        if (queueList.isNotEmpty() && queueList.size > 0){
            if (skipQueueLiveData.value != null){
                finishQueueData(
                    skipQueueLiveData.value!!.queueId.toString(),
                    "Completed",
                    StaticData.currentChannelNo,
                    StaticData.accountId
                )
                skipQueueLiveData.value = queueList[0]
            } else {
                skipQueueLiveData.value = queueList[0]
            }
        }
    }

    fun getQueue(){
        mutableLiveData = repo.getQueueChannelLiveData(
            StaticData.currentChannelType,
            StaticData.accountId
        )
    }

    fun callQueue(){
        if (queueList.isNotEmpty()){
            if (queueList.size != 1){
                finishQueueData(
                    queueList[0].queueId.toString(),
                    "Completed",
                    StaticData.currentChannelNo,
                    StaticData.accountId)
                updateQueueData(
                    queueList[1].queueId.toString(),
                    "Prepare",
                    StaticData.currentChannelNo,
                    StaticData.accountId
                )
                insertSoundQueue(
                    queueList[1].queueId,
                    queueList[1].queueNum,
                    "เชิญหมายเลข ${queueList[1].queueNum} ที่ช่องบริการ ${StaticData.currentChannelNo} ค่ะ",
                    "Ready"
                )
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val currentTime = Calendar.getInstance().time
                val currentDateTime: String = sdf.format(currentTime)

                insertTransactionQueue(
                    "insert_t_call_queue",
                    queueList[0].queueId.toString(),
                    queueList[0].queueNum,
                    queueList[0].queueDate,
                    "Finish",
                    StaticData.currentChannelType,
                    StaticData.currentChannelNo,
                    "Call",
                    StaticData.accountId,0,
                    "","",currentDateTime,
                    currentDateTime)

                insertTransactionQueue(
                    "insert_t_repeat_queue",
                    queueList[1].queueId.toString(),
                    queueList[1].queueNum,
                    queueList[1].queueDate,
                    "Prepare",
                    StaticData.currentChannelType,
                    StaticData.currentChannelNo,
                    "Call",
                    StaticData.accountId,0,
                    currentDateTime,"","",
                    currentDateTime)

                if (queueList[0].queueTransferDate.isNotEmpty() || queueList[0].queueTransferDate != null){
                    insertReportData(queueList[0].queueId,queueList[0].queueNum,queueList[0].queueDate,"Completed",
                        StaticData.currentChannelType,StaticData.currentChannelNo,StaticData.accountId,queueList[0].queueChangeDate,
                        currentDateTime,currentDateTime,"Yes","Enable")
                } else {
                    insertReportData(queueList[0].queueId,queueList[0].queueNum,queueList[0].queueDate,"Completed",
                        StaticData.currentChannelType,StaticData.currentChannelNo,StaticData.accountId,queueList[0].queueChangeDate,
                        currentDateTime,currentDateTime,"No","Enable")
                }

                queueList.remove(queueList[0])

//                if (queueList[0].queueChangeDate.isEmpty()){
//                    uiScope.launch {
//                        getQueue()
//                        delay(2000)
//                        if (queueList.isNotEmpty()){
//                            callRepeat()
//                        }
//                    }
//                } else {
//                    finishQueueData(
//                        queueList[0].queueId.toString(),
//                        "Completed",
//                        StaticData.currentChannelNo,
//                        StaticData.currentUserId)
//                    updateQueueData(
//                        queueList[1].queueId.toString(),
//                        "Prepare",
//                        StaticData.currentChannelNo,
//                        StaticData.currentUserId
//                    )
//                    insertSoundQueue(
//                        queueList[1].queueId,
//                        queueList[1].queueNum,
//                        "เชิญหมายเลข ${queueList[1].queueNum} ที่ช่องบริการ ${StaticData.currentChannelNo} ค่ะ",
//                        "Ready"
//                    )
//                    queueList.remove(queueList[0])
//                }
            } else if (queueList.size == 1){
                uiScope.launch {
                    getQueue()
                    delay(2000)
                    if (queueList.isNotEmpty()){
                        callRepeat()
                    }
                }
            }
        }
    }

    fun callQueue2(){
        if (queueList.isNotEmpty()) {
            if (queueList.size != 1) {
                if (queueList[0].queueChangeDate.isEmpty()) {
                    uiScope.launch {
                        getQueue()
                        delay(2000)
                        if (queueList.isNotEmpty()) {
                            callRepeat()
                        }
                    }
                } else {
                    finishQueueData(
                        queueList[0].queueId.toString(),
                        "Completed",
                        StaticData.currentChannelNo,
                        StaticData.accountId)
                    updateQueueData(
                        queueList[1].queueId.toString(),
                        "Prepare",
                        StaticData.currentChannelNo,
                        StaticData.accountId
                    )
                    insertSoundQueue(
                        queueList[1].queueId,
                        queueList[1].queueNum,
                        "เชิญหมายเลข ${queueList[1].queueNum} ที่ช่องบริการ ${StaticData.currentChannelNo} ค่ะ",
                        "Ready"
                    )
                    queueList.remove(queueList[0])
                }

            } else if(queueList.size == 1){
                uiScope.launch {
                    getQueue()
                    delay(2000)
                    if (queueList.isNotEmpty()){
                        callRepeat()
                    }
                }
            }
        }
    }

    fun callRepeat(){
        if (queueList.isNotEmpty()){
            updateQueueData(
                queueList[0].queueId.toString(),
                "Prepare",
                StaticData.currentChannelNo,
                StaticData.accountId
            )
            insertSoundQueue(
                queueList[0].queueId,
                queueList[0].queueNum,
                "เชิญหมายเลข ${queueList[0].queueNum} ที่ช่องบริการ ${StaticData.currentChannelNo} ค่ะ",
                "Ready"
            )

            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val currentTime = Calendar.getInstance().time
            val currentDateTime: String = sdf.format(currentTime)

            insertTransactionQueue(
                "insert_t_repeat_queue",
                queueList[0].queueId.toString(),
                queueList[0].queueNum,
                queueList[0].queueDate,
                queueList[0].queueStatus,
                StaticData.currentChannelType,
                StaticData.currentChannelNo,
                "Repeat",
                StaticData.accountId,0,
                currentDateTime,"","",
                currentDateTime)

        } else {
            println("QueueList Empty")
        }
    }

    fun transferQueue(activity: PRActivity2){
        val fm = activity.supportFragmentManager
        val dialog = TransferQueueDialog(activity, this)
        dialog.show(fm,"TransferDialog")
    }

    fun transferQueueData(serviceChannel: String, serviceCounter: Int){
        if (queueList.isNotEmpty()){
            transferQueueData(
                queueList[0].queueId.toString(),
                "Prepare",
                serviceChannel,
                serviceCounter,
                StaticData.accountId
            )

            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val currentTime = Calendar.getInstance().time
            val currentDateTime: String = sdf.format(currentTime)

            insertTransactionQueue(
                "insert_t_transfer_queue",
                queueList[0].queueId.toString(),
                queueList[0].queueNum,
                queueList[0].queueDate,
                "Prepare",
                serviceChannel,
                serviceCounter,
                "Transfer",
                StaticData.accountId,StaticData.accountId,
                "",currentDateTime,"",
                currentDateTime)

//            insertSoundQueue(
//                queueList[0].queueId,
//                queueList[0].queueNum,
//                "เชิญหมายเลข ${queueList[0].queueNum} ที่ช่องบริการ $serviceCounter ค่ะ",
//                "Ready"
//            )
        } else {
            println("QueueList Empty")
        }
    }

    fun skipQueue(){
        if (queueList.isNotEmpty()){
            if (queueList.size != 1){
                updateQueueData(
                    queueList[0].queueId.toString(),
                    "Skip",
                    StaticData.currentChannelNo,
                    StaticData.accountId
                )
                updateQueueData(
                    queueList[1].queueId.toString(),
                    "Prepare",
                    StaticData.currentChannelNo,
                    StaticData.accountId
                )
                insertSoundQueue(
                    queueList[1].queueId,
                    queueList[1].queueNum,
                    "เชิญหมายเลข ${queueList[1].queueNum} ที่ช่องบริการ ${StaticData.currentChannelNo} ค่ะ",
                    "Ready"
                )

                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val currentTime = Calendar.getInstance().time
                val currentDateTime: String = sdf.format(currentTime)

                insertTransactionQueue(
                    "insert_t_skip_queue",
                    queueList[0].queueId.toString(),
                    queueList[0].queueNum,
                    queueList[0].queueDate,
                    "Skip",
                    StaticData.currentChannelType,
                    StaticData.currentChannelNo,
                    "Skip",
                    StaticData.accountId,0,
                    currentDateTime,"","",
                    currentDateTime)

                insertTransactionQueue(
                    "insert_t_repeat_queue",
                    queueList[1].queueId.toString(),
                    queueList[1].queueNum,
                    queueList[1].queueDate,
                    "Prepare",
                    StaticData.currentChannelType,
                    StaticData.currentChannelNo,
                    "Skip",
                    StaticData.accountId,0,
                    currentDateTime,"","",
                    currentDateTime)

                setSkipQueue()
                queueList.remove(queueList[0])
            }
        }
    }

    fun callPreviousQueue(){
        if (skipQueueLiveData.value != null){
            updateQueueData(
                skipQueueLiveData.value!!.queueId.toString(),
                "Prepare",
                StaticData.currentChannelNo,
                StaticData.accountId
            )
            insertSoundQueue(
                skipQueueLiveData.value!!.queueId,
                skipQueueLiveData.value!!.queueNum,
                "เชิญหมายเลข ${skipQueueLiveData.value!!.queueNum} ที่ช่องบริการ ${StaticData.currentChannelNo} ค่ะ",
                "Ready"
            )

            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val currentTime = Calendar.getInstance().time
            val currentDateTime: String = sdf.format(currentTime)

            insertTransactionQueue(
                "insert_t_previous_queue",
                skipQueueLiveData.value!!.queueId.toString(),
                skipQueueLiveData.value!!.queueNum,
                skipQueueLiveData.value!!.queueDate,
                "Prepare",
                StaticData.currentChannelType,
                StaticData.currentChannelNo,
                "Previous",
                StaticData.accountId,0,
                currentDateTime,"","",
                currentDateTime)
        }
    }

    fun clearQueue(){
        repo.clearQueue2(this)
        //repo.clearQueueFromService(this,StaticData.currentChannelType)
    }

    fun clearPresentQueue(){
        if (queueList.isNotEmpty()){
            finishQueueData(
                queueList[0].queueId.toString(),
                "Completed",
                StaticData.currentChannelNo,
                StaticData.accountId
            )

            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val currentTime = Calendar.getInstance().time
            val currentDateTime: String = sdf.format(currentTime)

            insertTransactionQueue(
                "insert_t_end_queue",
                queueList[0].queueId.toString(),
                queueList[0].queueNum,
                queueList[0].queueDate,
                "Completed",
                StaticData.currentChannelType,
                StaticData.currentChannelNo,
                "Finish",
                StaticData.accountId,0,
                "","",currentDateTime,
                currentDateTime)

            if (queueList[0].queueTransferDate != null){
                insertReportData(queueList[0].queueId,queueList[0].queueNum,queueList[0].queueDate,"Completed",
                    StaticData.currentChannelType,StaticData.currentChannelNo,StaticData.accountId,queueList[0].queueChangeDate,
                    currentDateTime, currentDateTime,"Yes","Enable")
            } else {
                insertReportData(queueList[0].queueId,queueList[0].queueNum,queueList[0].queueDate,"Completed",
                    StaticData.currentChannelType,StaticData.currentChannelNo,StaticData.accountId,queueList[0].queueChangeDate,
                    currentDateTime, currentDateTime,"No","Enable")
            }
        }
    }

    fun submitPasswordDialog(context: Context){
        val dialog = LayoutInflater.from(context).inflate(R.layout.dialog_submit_password, null)
        val builder = AlertDialog.Builder(context).setView(dialog)
        val alertDialog = builder.show()
        dialog.submit_clear_queue.setOnClickListener {
            if (dialog.password_edt.text.toString() == "123456"){
                clearQueue()
                context.toast("เคลียร์คิวเสร็จสิ้น")
                alertDialog.dismiss()
            } else{
                context.toast("รหัสผ่านไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง")
            }
        }
        dialog.dialog_close.setOnClickListener {
            alertDialog.dismiss()
        }
    }

    fun setQueueList(list: ArrayList<QueueResponse>){
        this.queueList = list
    }

    private suspend fun autoQueueTiming(){
        withContext(Dispatchers.IO){
            getQueue()
            delay(6000)
        }
    }

    private fun updateQueueData(
        queueId: String,
        queueStatus: String,
        serviceCounter: Int,
        userId: Int
    ){
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val currentTime = Calendar.getInstance().time
        val currentDateTime: String = sdf.format(currentTime)

        repo.updateQueueData(queueId, queueStatus, serviceCounter, userId, currentDateTime)
    }

    private fun transferQueueData(
        queueId: String,
        queueStatus: String,
        serviceChannel: String,
        serviceCounter: Int,
        userId: Int
    ){
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val currentTime = Calendar.getInstance().time
        val currentDateTime: String = sdf.format(currentTime)

        repo.transferQueueData(queueId, queueStatus, serviceChannel, serviceCounter, userId, currentDateTime, currentDateTime)
    }

    private fun finishQueueData(
        queueId: String,
        queueStatus: String,
        serviceCounter: Int,
        userId: Int
    ){
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val currentTime = Calendar.getInstance().time
        val currentDateTime: String = sdf.format(currentTime)

        repo.finishQueueData(queueId,queueStatus,serviceCounter,userId,currentDateTime)
    }

//    fun insertAccount(username: String, password: String, name: String, surname: String, tel: String, email: String, userType: String, status: String){
//        repo.insertAccount(username, password, name, surname, tel, email, userType, status)
//    }

    private fun insertSoundQueue(queueId: Int, queueNum: String, message: String, active: String){
        repo.insertSoundQueue(
            queueId,
            queueNum,
            message,
            active,
            StaticData.currentChannelType,
            StaticData.currentChannelNo
        )
    }

    private fun insertTransactionQueue(request: String, queueId: String, queueNum: String, queueDate: String, queueStatus: String, serviceChannel: String, serviceCounter: Int
                                       ,pressQueueType: String, calledUserId: Int, transferUserId: Int, queueChangeDate: String, queueTransferDate: String, queueFinishDate: String, transactionDate: String){
        repo.insertTransactionQueue(request, queueId, queueNum, queueDate, queueStatus, serviceChannel, serviceCounter, pressQueueType, calledUserId, transferUserId, queueChangeDate, queueTransferDate, queueFinishDate, transactionDate)
    }

    private fun insertReportData(queueId: Int, queueNum: String, queueDate: String, queueStatus: String, serviceChannel: String, serviceCounter: Int, endQueueUserId: Int, latestCallDate: String, queueFinishDate: String, reportDate: String, isTransfer: String, active: String){
        repo.insertReportData(queueId, queueNum, queueDate, queueStatus, serviceChannel, serviceCounter, endQueueUserId, latestCallDate, queueFinishDate, reportDate, isTransfer, active)
    }

}