package com.example.landmarkapp.viewmodel

import android.app.Application
import android.graphics.BitmapFactory
import android.text.format.DateFormat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.landmarkapp.R
import com.example.landmarkapp.Network.Repository
import com.example.landmarkapp.model.Retrofit.response.QueueResponse
import com.example.landmarkapp.utils.BixolonPrinter.BixolonPrinter
import com.example.landmarkapp.utils.StaticData.Companion.bxlPrinter
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

class QueueUserViewModel(app : Application) : AndroidViewModel(app){
    private val repo: Repository = Repository()
    private var queueCount = 0
    private var queueTime = ""
    private var countLiveData = MutableLiveData<Int>()
    private var timeLiveData = MutableLiveData<String>()
    private var queueList = ArrayList<QueueResponse>()
    var mutableLiveData: MutableLiveData<ArrayList<QueueResponse>> = repo.getQueueLiveData()
    var waitingQueueLiveData = MutableLiveData<Int>()
    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Default + viewModelJob)

    fun getQueueSlip(){
        getCurrentQueue()
        getCurrentDate()
        runBlocking {
            uiScope.launch(Dispatchers.IO) {
                insertQueue("A${String.format("%03d",countLiveData.value)}",timeLiveData.value.toString())
                insertReport(0, "A${String.format("%03d",countLiveData.value)}","A","","Enable")
            }
            uiScope.launch(Dispatchers.Default) {
                printQueue()
            }
        }
    }

    fun getNotice(){
        println("Get Notices")
    }

    private fun getCurrentDate(): MutableLiveData<String>{
        queueTime = DateFormat.format("yyyy-MM-dd hh:mm:ss", Date()).toString()
        timeLiveData.value = queueTime
        println("Date time now is = ${timeLiveData.value}")
        return timeLiveData
    }

    fun getInitialCount(): MutableLiveData<Int>{
        countLiveData.value = queueCount
        return countLiveData
    }

    private fun getCurrentQueue(){
        queueCount +=1
        countLiveData.value = queueCount
        println("Count is ${countLiveData.value}")
    }

    private fun printQueue(){
        val bitmap = BitmapFactory.decodeResource(
            getApplication<Application>().applicationContext.resources,
            R.drawable.qrcode
        )

//        bxlPrinter.characterSet = BixolonPrinter.CS_THAI11
//        bxlPrinter.printText("Pathumthani Provincial Land Office\n\n",BixolonPrinter.ALIGNMENT_CENTER,BixolonPrinter.ATTRIBUTE_FONT_C,2)
//        bxlPrinter.printText("Welcome\n\n",BixolonPrinter.ALIGNMENT_CENTER,BixolonPrinter.ATTRIBUTE_FONT_D,0)
//        bxlPrinter.printText("Check documents\n",BixolonPrinter.ALIGNMENT_CENTER,BixolonPrinter.ATTRIBUTE_FONT_D,2)
//        bxlPrinter.printText("\nA${String.format("%03d",countLiveData.value)}\n\n",BixolonPrinter.ALIGNMENT_CENTER,BixolonPrinter.ATTRIBUTE_FONT_A,4)
//        bxlPrinter.printText("Waiting Queue ${checkWaitingQueue()} Queue\n\n",BixolonPrinter.ALIGNMENT_CENTER,BixolonPrinter.ATTRIBUTE_FONT_A,0)
//        bxlPrinter.printText("Date: ${timeLiveData.value}\n\n",BixolonPrinter.ALIGNMENT_CENTER,BixolonPrinter.ATTRIBUTE_FONT_A,0)
//        bxlPrinter.printImage(bitmap,150, BixolonPrinter.ALIGNMENT_CENTER,50,0)
//        bxlPrinter.printText("\n",BixolonPrinter.ALIGNMENT_CENTER,BixolonPrinter.ATTRIBUTE_FONT_A,1)

        var size = BixolonPrinter.ATTRIBUTE_FONT_A
        size = size or BixolonPrinter.ATTRIBUTE_BOLD
        var size2 = BixolonPrinter.ATTRIBUTE_FONT_B
        size2 = size2 or BixolonPrinter.ATTRIBUTE_BOLD
        bxlPrinter.characterSet = BixolonPrinter.CS_THAI11
        bxlPrinter.printText("Pathumthani Provincial Land Office\n\n",BixolonPrinter.ALIGNMENT_CENTER,size,0)
        bxlPrinter.printText("Welcome\n",BixolonPrinter.ALIGNMENT_CENTER,BixolonPrinter.ATTRIBUTE_FONT_A,0)
        //bxlPrinter.printText("Check documents\n",BixolonPrinter.ALIGNMENT_CENTER,BixolonPrinter.ATTRIBUTE_FONT_B,3)
        bxlPrinter.printText("\nA${String.format("%03d",countLiveData.value)}\n\n",BixolonPrinter.ALIGNMENT_CENTER,size2,7)
        bxlPrinter.printText("Waiting Queue ${checkWaitingQueue()} Queue\n\n",BixolonPrinter.ALIGNMENT_CENTER,BixolonPrinter.ATTRIBUTE_FONT_A,0)
        bxlPrinter.printText("Date: ${timeLiveData.value}\n\n",BixolonPrinter.ALIGNMENT_CENTER,BixolonPrinter.ATTRIBUTE_FONT_A,0)
        bxlPrinter.printImage(bitmap,150, BixolonPrinter.ALIGNMENT_CENTER,50,0)
        bxlPrinter.printText("\n",BixolonPrinter.ALIGNMENT_CENTER,BixolonPrinter.ATTRIBUTE_FONT_A,1)

//        bxlPrinter.characterSet = BixolonPrinter.CS_THAI11
//        bxlPrinter.printText("Pathumthani Provincial Land Office\n\n",BixolonPrinter.ALIGNMENT_CENTER,BixolonPrinter.ATTRIBUTE_FONT_B,2)
//        bxlPrinter.printText("Welcome\n\n",BixolonPrinter.ALIGNMENT_CENTER,BixolonPrinter.ATTRIBUTE_FONT_B,2)
//        bxlPrinter.printText("Check documents\n",BixolonPrinter.ALIGNMENT_CENTER,BixolonPrinter.ATTRIBUTE_FONT_B,2)
//        bxlPrinter.printText("\nA${String.format("%03d",countLiveData.value)}\n\n",BixolonPrinter.ALIGNMENT_CENTER,BixolonPrinter.ATTRIBUTE_FONT_B,2)
//        bxlPrinter.printText("Waiting Queue ${checkWaitingQueue()} Queue\n\n",BixolonPrinter.ALIGNMENT_CENTER,BixolonPrinter.ATTRIBUTE_FONT_B,2)
//        bxlPrinter.printText("Date: ${timeLiveData.value}\n\n",BixolonPrinter.ALIGNMENT_CENTER,BixolonPrinter.ATTRIBUTE_FONT_B,2)
//        bxlPrinter.printImage(bitmap,150, BixolonPrinter.ALIGNMENT_CENTER,50,0)
//        bxlPrinter.printText("\n",BixolonPrinter.ALIGNMENT_CENTER,BixolonPrinter.ATTRIBUTE_FONT_B,2)

        bxlPrinter.cutPaper()

    }

    private fun checkWaitingQueue(): String{
        uiScope.launch {
            getQueue()
            if (queueList.isNotEmpty()){
                println("Queue waiting is ${queueList.size}")
            } else {
                println("There is no queue")
            }
        }

        return queueList.size.toString()
    }

    fun setQueueList(list: ArrayList<QueueResponse>){
        this.queueList = list
    }

    private fun getQueue(){
        mutableLiveData = repo.getQueueLiveData()
    }

    fun showWaitingQueueCount(){
        if (queueList.isNotEmpty() && queueList.size > 0){
            waitingQueueLiveData.value = queueList.size -1
        } else {
            waitingQueueLiveData.value = 0
        }
    }

    private fun insertQueue(queueNum: String, time: String){
        repo.insertQueue(queueNum,time)
    }

    private fun insertReport(queueId: Int, queueNum: String, serviceChannel: String, employee: String, active: String){
        repo.insertReport(queueId, queueNum, serviceChannel, employee, active)
    }

    fun insertRate(rateScore: Int, rateTitle: String, active: String, rateStatus: String) {
        repo.insertRate(rateScore, rateTitle, active, rateStatus)
    }

//    suspend fun insertRate(rateScore: Int, rateTitle: String, active: String) = withContext(Dispatchers.IO){
//        repo.insertRate(rateScore, rateTitle, active)
//    }

}