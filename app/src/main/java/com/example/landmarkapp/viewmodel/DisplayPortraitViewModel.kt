package com.example.landmarkapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.landmarkapp.Network.Repository
import com.example.landmarkapp.model.Retrofit.response.QueueResponse
import com.example.landmarkapp.ui.Activity.Display2Activity
import com.example.landmarkapp.ui.Activity.DisplayPortraitActivity
import kotlinx.coroutines.*

class DisplayPortraitViewModel : ViewModel()  {
    private val repo: Repository = Repository()
    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)
    private var queueList = ArrayList<QueueResponse>()
    var mutableLiveData: MutableLiveData<ArrayList<QueueResponse>> = repo.getQueueWithChannel()

    fun showAllQueueSequence(activity: DisplayPortraitActivity){
        if (queueList.isNotEmpty() && queueList.size != 0){
            activity.binding.queue1Txt.text = "-"
            activity.binding.counter1Txt.text = "-"
            activity.binding.queue2Txt.text = "-"
            activity.binding.counter2Txt.text = "-"
            activity.binding.queue3Txt.text = "-"
            activity.binding.counter3Txt.text = "-"
            activity.binding.queue4Txt.text = "-"
            activity.binding.counter4Txt.text = "-"
            activity.binding.queue5Txt.text = "-"
            activity.binding.counter5Txt.text = "-"

            for (i in queueList.indices){
                println("Queue Size is ${queueList.size}")
                if (queueList[i].serviceCounter == null || queueList[i].serviceCounter == 0){
                    println("Continue queue")
                    continue
                } else {
                    if (activity.binding.queue1Txt.text == "-" && activity.binding.counter1Txt.text == "-"){
                        activity.binding.queue1Txt.text = queueList[i].queueNum
                        activity.binding.counter1Txt.text = queueList[i].serviceCounter.toString()
                    } else if (activity.binding.queue2Txt.text == "-" && activity.binding.counter2Txt.text == "-"){
                        activity.binding.queue2Txt.text = queueList[i].queueNum
                        activity.binding.counter2Txt.text = queueList[i].serviceCounter.toString()
                    } else if (activity.binding.queue3Txt.text == "-" && activity.binding.counter3Txt.text == "-"){
                        activity.binding.queue3Txt.text = queueList[i].queueNum
                        activity.binding.counter3Txt.text = queueList[i].serviceCounter.toString()
                    } else if (activity.binding.queue4Txt.text == "-" && activity.binding.counter4Txt.text == "-"){
                        activity.binding.queue4Txt.text = queueList[i].queueNum
                        activity.binding.counter4Txt.text = queueList[i].serviceCounter.toString()
                    } else if (activity.binding.queue5Txt.text == "-" && activity.binding.counter5Txt.text == "-"){
                        activity.binding.queue5Txt.text = queueList[i].queueNum
                        activity.binding.counter5Txt.text = queueList[i].serviceCounter.toString()
                        break
                    }
                }
            }
        } else {
            activity.launch(Dispatchers.Main) {
                activity.binding.queue1Txt.text = "-"
                activity.binding.counter1Txt.text = "-"
                activity.binding.queue2Txt.text = "-"
                activity.binding.counter2Txt.text = "-"
                activity.binding.queue3Txt.text = "-"
                activity.binding.counter3Txt.text = "-"
                activity.binding.queue4Txt.text = "-"
                activity.binding.counter4Txt.text = "-"
                activity.binding.queue5Txt.text = "-"
                activity.binding.counter5Txt.text = "-"
            }
        }
    }

    fun setQueueList(list: ArrayList<QueueResponse>){
        this.queueList = list
        //println("Queue size is ${this.queueList.size}")
    }

    fun getQueue(){
        mutableLiveData = repo.getQueueWithChannel()
    }

}