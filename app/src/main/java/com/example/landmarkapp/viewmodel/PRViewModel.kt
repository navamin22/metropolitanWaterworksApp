package com.example.landmarkapp.viewmodel

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.landmarkapp.Network.Repository
import com.example.landmarkapp.R
import com.example.landmarkapp.model.Retrofit.response.QueueResponse
import com.example.landmarkapp.utils.StaticData
import com.example.landmarkapp.utils.toast
import kotlinx.android.synthetic.main.dialog_submit_password.view.*
import kotlinx.coroutines.*
import java.math.BigInteger
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PRViewModel(app: Application) : AndroidViewModel(app) {
    private val repo: Repository = Repository()
    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)
    private var queueList = ArrayList<QueueResponse>()
    var mutableLiveData: MutableLiveData<ArrayList<QueueResponse>> = repo.getQueueLiveData()
//    var mutableLiveData: MutableLiveData<ArrayList<QueueResponse>> = repo.getQueueChannelLiveData(
//        StaticData.currentChannelType)
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

    fun getCurrentQueueShow(): LiveData<CharSequence>{
        return currentQueueLiveData
    }

    fun getCurrentNextQueueShow(): LiveData<CharSequence>{
        return nextQueueLiveData
    }

    private fun setSkipQueue(){
        if (queueList.isNotEmpty() && queueList.size > 0){
            if (skipQueueLiveData.value != null){
                updateQueueData(skipQueueLiveData.value!!.queueId.toString(),"Completed",StaticData.currentChannelNo,StaticData.currentUserId)
                skipQueueLiveData.value = queueList[0]
            } else {
                skipQueueLiveData.value = queueList[0]
            }
        }
    }

    fun getQueue(){
        mutableLiveData = repo.getQueueLiveData()
        //mutableLiveData = repo.getQueueChannelLiveData(StaticData.currentChannelType)
    }

    fun callQueue(){
        if (queueList.isNotEmpty()){
            if (queueList.size != 1){
                updateQueueData(queueList[0].queueId.toString(),"Completed",StaticData.currentChannelNo,StaticData.currentUserId)
//                updateQueueData(queueList[1].queueId.toString(),"Prepare")
//                insertSoundQueue(queueList[1].queueId, queueList[1].queueNum,"เชิญหมายเลข ${queueList[1].queueNum} ที่ช่องบริการ ${StaticData.currentChannelNo} ค่ะ","Ready")
                insertSoundQueue(queueList[1].queueId, queueList[1].queueNum,"เชิญหมายเลข ${queueList[1].queueNum} ค่ะ","Ready")
                queueList.remove(queueList[0])
            } else{
//                updateQueueData(queueList[0].queueId.toString(),"Prepare")
//                insertSoundQueue(queueList[0].queueId, queueList[0].queueNum,"เชิญหมายเลข ${queueList[0].queueNum} ที่ช่องบริการ ${StaticData.currentChannelNo} ค่ะ","Ready")
                insertSoundQueue(queueList[0].queueId, queueList[0].queueNum,"เชิญหมายเลข ${queueList[0].queueNum} ค่ะ","Ready")
            }
        } else {
            uiScope.launch {
                getQueue()
                delay(2000)
                if (queueList.isNotEmpty()){
                    callRepeat()
                }
            }
        }
    }

    fun callRepeat(){
        if (queueList.isNotEmpty()){
//            updateQueueData(queueList[0].queueId.toString(),"Prepare")
//            insertSoundQueue(queueList[0].queueId, queueList[0].queueNum,"เชิญหมายเลข ${queueList[0].queueNum} ที่ช่องบริการ ${StaticData.currentChannelNo} ค่ะ","Ready")
            insertSoundQueue(queueList[0].queueId, queueList[0].queueNum,"เชิญหมายเลข ${queueList[0].queueNum} ค่ะ","Ready")
        } else {
            println("QueueList Empty")
        }
    }

    fun skipQueue(){
        if (queueList.isNotEmpty()){
            if (queueList.size != 1){
                updateQueueData(queueList[0].queueId.toString(),"Skip",StaticData.currentChannelNo,StaticData.currentUserId)
                insertSoundQueue(queueList[1].queueId, queueList[1].queueNum,"เชิญหมายเลข ${queueList[1].queueNum} ค่ะ","Ready")
//                insertSoundQueue(queueList[1].queueId, queueList[1].queueNum,"เชิญหมายเลข ${queueList[1].queueNum} ที่ช่องบริการ ${StaticData.currentChannelNo} ค่ะ","Ready")
                setSkipQueue()
                queueList.remove(queueList[0])
            }
        }
    }

    fun callPreviousQueue(){
        if (skipQueueLiveData.value != null){
//            updateQueueData(skipQueueLiveData.value!!.queueId.toString(),"Prepare")
//            insertSoundQueue(skipQueueLiveData.value!!.queueId, skipQueueLiveData.value!!.queueNum,"เชิญหมายเลข ${skipQueueLiveData.value!!.queueNum} ที่ช่องบริการ ${StaticData.currentChannelNo} ค่ะ","Ready")
            updateQueueData(skipQueueLiveData.value!!.queueId.toString(),"Prepare",StaticData.currentChannelNo,StaticData.currentUserId)
            insertSoundQueue(skipQueueLiveData.value!!.queueId, skipQueueLiveData.value!!.queueNum,"เชิญหมายเลข ${skipQueueLiveData.value!!.queueNum} ค่ะ","Ready")
        }
    }

    fun clearQueue(){
        repo.clearQueue(this)
        //repo.clearQueueFromService(this,StaticData.currentChannelType)
    }

    fun clearPresentQueue(){
        if (queueList.isNotEmpty()){
            updateQueueData(queueList[0].queueId.toString(),"Completed",StaticData.currentChannelNo,StaticData.currentUserId)
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

    private fun updateQueueData(queueId: String, queueStatus: String, serviceCounter: Int, userId: Int){
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val currentTime = Calendar.getInstance().time
        val currentDateTime: String = sdf.format(currentTime)

        repo.updateQueueData(queueId,queueStatus,serviceCounter,userId,currentDateTime)
    }

    private fun insertSoundQueue(queueId: Int, queueNum: String, message: String, active: String){
        repo.insertSoundQueue(queueId, queueNum,message, active, StaticData.currentChannelType, StaticData.currentChannelNo)
    }

}