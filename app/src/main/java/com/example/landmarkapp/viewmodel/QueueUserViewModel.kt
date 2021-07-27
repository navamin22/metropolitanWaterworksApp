package com.example.landmarkapp.viewmodel

import android.app.Application
import android.graphics.BitmapFactory
import android.text.format.DateFormat
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.landmarkapp.R
import com.example.landmarkapp.Network.Repository
import com.example.landmarkapp.model.Retrofit.response.QueueResponse
import com.example.landmarkapp.ui.Activity.QueueUserActivity
import com.example.landmarkapp.utils.BixolonPrinter.BixolonPrinter
import com.example.landmarkapp.utils.StaticData
import com.example.landmarkapp.utils.StaticData.Companion.bxlPrinter
import com.example.landmarkapp.utils.StaticData.Companion.queueCount
import com.example.landmarkapp.utils.StaticData.Companion.queueCount2
import com.example.landmarkapp.utils.StaticData.Companion.queueCount3
import com.example.landmarkapp.utils.StaticData.Companion.queueCount4
import com.example.landmarkapp.utils.StaticData.Companion.queueCount5
import kotlinx.coroutines.*
import java.math.BigInteger
import java.util.*
import kotlin.collections.ArrayList

class QueueUserViewModel(app : Application) : AndroidViewModel(app){
    private val repo: Repository = Repository()
    private var queueTime = ""
    private var countLiveData = MutableLiveData<Int>()
    private var countLiveData2 = MutableLiveData<Int>()
    private var countLiveData3 = MutableLiveData<Int>()
    private var countLiveData4 = MutableLiveData<Int>()
    private var countLiveData5 = MutableLiveData<Int>()
    private var timeLiveData = MutableLiveData<String>()
    private var queueList = ArrayList<QueueResponse>()
    var mutableLiveData: MutableLiveData<ArrayList<QueueResponse>> = repo.getQueueLiveData()
    var waitingQueueLiveData = MutableLiveData<Int>()
    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Default + viewModelJob)
    private var waitQueue = 0

    fun getQueueSlip(activity: QueueUserActivity){
        showWaitingQueueCount(activity)
        getCurrentQueue()
        getCurrentDate()
        uiScope.launch(Dispatchers.Main) {
            delay(1000)
            activity.binding.mwaBills.visibility = View.GONE
            activity.binding.mwaTitle.text = "ชำระค่าประปา/ค่าไฟฟ้า"
            activity.binding.mwaQueue.text = "A${String.format("%03d",countLiveData.value)}"
            activity.binding.mwaWaitQueue.text = "คิวที่รอ $waitQueue คิว"
            activity.binding.mwaDateQueue.text = timeLiveData.value.toString()
            activity.printImageQueue()

            withContext(Dispatchers.IO) {
                insertQueue("A${String.format("%03d",countLiveData.value)}",timeLiveData.value.toString(),StaticData.channelType1)
                //insertReport(0, "A${String.format("%03d",countLiveData.value)}",StaticData.channelType1,"","Enable")
                getQueue()
            }

//            uiScope.launch(Dispatchers.Default) {
//                printQueue()
//            }
        }
    }

    fun newPlumbingQueue(activity: QueueUserActivity){
        showWaitingQueueCount(activity)
        getCurrentQueue2()
        getCurrentDate()
        uiScope.launch(Dispatchers.Main) {
            delay(1000)
            activity.binding.mwaBills.visibility = View.GONE
            activity.binding.mwaTitle.text = "ติดตั้งประปาใหม่/บริการอื่นๆ"
            activity.binding.mwaQueue.text = "B${String.format("%03d",countLiveData2.value)}"
            activity.binding.mwaWaitQueue.text = "คิวที่รอ $waitQueue คิว"
            activity.binding.mwaDateQueue.text = timeLiveData.value.toString()
            activity.printImageQueue()

            withContext(Dispatchers.IO){
                insertQueue("B${String.format("%03d",countLiveData2.value)}",timeLiveData.value.toString(),StaticData.channelType2)
                //insertReport(0, "B${String.format("%03d",countLiveData2.value)}",StaticData.channelType2,"","Enable")
                getQueue()
            }

//            uiScope.launch(Dispatchers.IO) {
//                insertQueue("B${String.format("%03d",countLiveData2.value)}",timeLiveData.value.toString())
//                insertReport(0, "B${String.format("%03d",countLiveData2.value)}","B","","Enable")
//            }
//            uiScope.launch(Dispatchers.Default) {
//                printQueue()
//            }
        }
    }

    fun payBillQueue(activity: QueueUserActivity){
        showWaitingQueueCount(activity)
        getCurrentQueue3()
        getCurrentDate()
        uiScope.launch(Dispatchers.Main) {
            delay(1000)
            activity.binding.mwaBills.visibility = View.GONE
            activity.binding.mwaTitle.text = "ชำระค่าติดตั้งประปาใหม่"
            activity.binding.mwaQueue.text = "C${String.format("%03d",countLiveData3.value)}"
            activity.binding.mwaWaitQueue.text = "คิวที่รอ $waitQueue คิว"
            activity.binding.mwaDateQueue.text = timeLiveData.value.toString()
            activity.printImageQueue()

            withContext(Dispatchers.IO) {
                insertQueue("C${String.format("%03d",countLiveData3.value)}",timeLiveData.value.toString(),StaticData.channelType3)
                //insertReport(0, "C${String.format("%03d",countLiveData3.value)}",StaticData.channelType3,"","Enable")
                getQueue()
            }
        }
    }

    fun getMoreBillsSlip(activity: QueueUserActivity, bills: Int){
        showWaitingQueueCount(activity)
        getCurrentQueue5()
        getCurrentDate()
        uiScope.launch(Dispatchers.Main) {
            delay(1000)
            activity.binding.mwaBills.visibility = View.VISIBLE
            activity.binding.mwaTitle.text = "ชำระมากกว่า 1 รายการ"
            activity.binding.mwaQueue.text = "C${String.format("%03d",countLiveData5.value)}"
            activity.binding.mwaBills.text = "รายการชำระทั้งหมด $bills รายการ"
            activity.binding.mwaWaitQueue.text = "คิวที่รอ $waitQueue คิว"
            activity.binding.mwaDateQueue.text = timeLiveData.value.toString()
            activity.printImageQueue()

            withContext(Dispatchers.IO) {
                insertQueue("D${String.format("%03d",countLiveData5.value)}",timeLiveData.value.toString(),StaticData.channelType3)
                //insertReport(0, "D${String.format("%03d",countLiveData5.value)}",StaticData.channelType3,"","Enable")
            }
        }
    }

    fun getPaymentSlip(activity: QueueUserActivity){
        showWaitingQueueCount(activity)
        getCurrentQueue4()
        getCurrentDate()
        uiScope.launch(Dispatchers.Main) {
            activity.binding.mwaTitle.text = "ชำระเงินผ่าน QRCode"
            activity.binding.mwaQueue.text = "D${String.format("%03d",countLiveData4.value)}"
            activity.binding.mwaWaitQueue.text = "คิวที่รอ $waitQueue คิว"
            activity.binding.mwaDateQueue.text = timeLiveData.value.toString()
            activity.printImageQueue()

            withContext(Dispatchers.IO) {
                insertQueue("D${String.format("%03d",countLiveData4.value)}",timeLiveData.value.toString(),StaticData.channelType4)
                //insertReport(0, "D${String.format("%03d",countLiveData4.value)}",StaticData.channelType4,"","Enable")
            }
        }
    }

    private fun checkWaitingQueue(): String{
        getQueue()
        if (queueList.isNotEmpty()){
            println("Queue waiting is ${queueList.size}")
        } else {
            println("There is no queue")
        }

        return queueList.size.toString()
    }

    fun setQueueList(list: ArrayList<QueueResponse>){
        this.queueList = list
    }

    fun showWaitingQueueCount(activity: QueueUserActivity){
        uiScope.launch(Dispatchers.Main) {
            setQueueList(mutableLiveData.value!!)
            if (queueList.isNotEmpty() && queueList.size > 0){
                waitQueue = queueList.size
            } else {
                waitQueue = 0
            }
            println("Waiting Queue is $waitQueue")
            println("Queue list is ${queueList.size-1}")
        }
    }

    private fun getQueue(){
        mutableLiveData = repo.getQueueLiveData()
    }

    private fun getCurrentDate(): MutableLiveData<String>{
        queueTime = DateFormat.format("dd-MM-yyyy HH:mm:ss", Date()).toString()
        timeLiveData.value = queueTime
        println("Date time now is = ${timeLiveData.value}")
        return timeLiveData
    }

    fun getInitialCount(): MutableLiveData<Int>{
        countLiveData.value = queueCount
        return countLiveData
    }

    fun getInitialCount2(): MutableLiveData<Int>{
        countLiveData2.value = queueCount2
        return countLiveData2
    }

    fun getInitialCount3(): MutableLiveData<Int>{
        countLiveData3.value = queueCount3
        return countLiveData3
    }

    fun getInitialCount4(): MutableLiveData<Int>{
        countLiveData4.value = queueCount4
        return countLiveData4
    }

    fun getInitialCount5(): MutableLiveData<Int>{
        countLiveData5.value = queueCount5
        return countLiveData5
    }

    private fun getCurrentQueue(){
        queueCount +=1
        countLiveData.value = queueCount
        println("Count is ${countLiveData.value}")
    }

    private fun getCurrentQueue2(){
        queueCount2 +=1
        countLiveData2.value = queueCount2
        println("Count is ${countLiveData2.value}")
    }

    private fun getCurrentQueue3(){
        queueCount3 +=1
        countLiveData3.value = queueCount3
        println("Count is ${countLiveData3.value}")
    }

    private fun getCurrentQueue4(){
        queueCount4 +=1
        countLiveData4.value = queueCount4
        println("Count is ${countLiveData4.value}")
    }

    private fun getCurrentQueue5(){
        queueCount5 +=1
        countLiveData5.value = queueCount5
        println("Count is ${countLiveData5.value}")
    }

    private fun insertQueue(queueNum: String, time: String, serviceChannel: String){
        repo.insertQueue(queueNum,time,serviceChannel)
    }

    private fun insertReport(queueId: Int, queueNum: String, serviceChannel: String, employee: String, active: String){
        repo.insertReport(queueId, queueNum, serviceChannel, employee, active)
    }

    fun insertRate(rateScore: Int, rateTitle: String, active: String, rateStatus: String, telNumber: String, reasonRateLow: String, serviceCounter: Int) {
        repo.insertRate(rateScore, rateTitle, active, rateStatus, telNumber, reasonRateLow, serviceCounter, 0)
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
        bxlPrinter.printText("Metropolitan Waterworks Authority\n\n",BixolonPrinter.ALIGNMENT_CENTER,size,0)
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
}