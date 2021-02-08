package com.example.landmarkapp.viewmodel

import android.app.Application
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.landmarkapp.Network.Repository
import com.example.landmarkapp.model.Retrofit.response.QueueResponse
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

class PRViewModel(app: Application) : AndroidViewModel(app) {
    private val repo: Repository = Repository()
    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)
    private var queueList = ArrayList<QueueResponse>()
    var mutableLiveData: MutableLiveData<ArrayList<QueueResponse>> = repo.getQueueLiveData()
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

    fun getCurrentQueueShow(): LiveData<CharSequence>{
        return currentQueueLiveData
    }

    fun getCurrentNextQueueShow(): LiveData<CharSequence>{
        return nextQueueLiveData
    }

    private fun setSkipQueue(){
        if (queueList.isNotEmpty() && queueList.size > 0){
            if (skipQueueLiveData.value != null){
                updateQueueData(skipQueueLiveData.value!!.queueId.toString(),"Completed")
                skipQueueLiveData.value = queueList[0]
            } else {
                skipQueueLiveData.value = queueList[0]
            }
        }
    }

    fun getQueue(){
        mutableLiveData = repo.getQueueLiveData()
    }

    fun callQueue(){
        if (queueList.isNotEmpty()){
            if (queueList.size != 1){
                updateQueueData(queueList[0].queueId.toString(),"Completed")
                insertSoundQueue(queueList[1].queueId, queueList[1].queueNum,"เชิญหมายเลข ${queueList[1].queueNum} ค่ะ","Ready")
                queueList.remove(queueList[0])
            } else{
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
        insertSoundQueue(queueList[0].queueId, queueList[0].queueNum,"เชิญหมายเลข ${queueList[0].queueNum} ค่ะ","Ready")
    }

    fun skipQueue(){
        if (queueList.isNotEmpty()){
            if (queueList.size != 1){
                updateQueueData(queueList[0].queueId.toString(),"Skip")
                insertSoundQueue(queueList[1].queueId, queueList[1].queueNum,"เชิญหมายเลข ${queueList[1].queueNum} ค่ะ","Ready")
                setSkipQueue()
                queueList.remove(queueList[0])
            }
        }
    }

    fun callPreviousQueue(){
        if (skipQueueLiveData.value != null){
            insertSoundQueue(skipQueueLiveData.value!!.queueId, skipQueueLiveData.value!!.queueNum,"เชิญหมายเลข ${skipQueueLiveData.value!!.queueNum} ค่ะ","Ready")
        }
    }

    fun clearQueue(){
        repo.clearQueue(this)
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

    private fun updateQueueData(queueId: String, queueStatus: String){
        repo.updateQueueData(queueId,queueStatus)
    }

    private fun insertSoundQueue(queueId: Int, queueNum: String, message: String, active: String){
        repo.insertSoundQueue(queueId, queueNum,message, active)
    }

}