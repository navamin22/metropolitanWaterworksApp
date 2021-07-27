package com.example.landmarkapp.viewmodel

import android.speech.tts.TextToSpeech
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.landmarkapp.Network.Repository
import com.example.landmarkapp.model.Retrofit.response.QueueResponse
import com.example.landmarkapp.model.Retrofit.response.SoundQueueResponse
import com.example.landmarkapp.ui.Activity.Display2Activity
import kotlinx.coroutines.*
import java.util.logging.Handler

class Display2ViewModel(tts : TextToSpeech) : ViewModel() {
    private val repo: Repository = Repository()
    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)
    private var queueList = ArrayList<QueueResponse>()
    private var waitingQueueList = ArrayList<QueueResponse>()
    private var soundList = ArrayList<SoundQueueResponse>()
    private lateinit var textToSpeech: TextToSpeech
    var mutableLiveData: MutableLiveData<ArrayList<QueueResponse>> = repo.getQueueWithChannel()
    var mutableLiveData2: MutableLiveData<ArrayList<QueueResponse>> = repo.getWaitingQueueLiveData()
    var mutableSoundLiveData: MutableLiveData<ArrayList<SoundQueueResponse>> = repo.getSoundQueueLiveData()
    var currentQueueLiveData = MutableLiveData<CharSequence>()
    var waitingQueueLiveData = MutableLiveData<Int>()
    var nextQueueLiveData = MutableLiveData<CharSequence>()
    private lateinit var handler: Handler
    val tts : TextToSpeech = tts

    fun showQueueDisplay(activity: Display2Activity){
        showQueueEachType(activity)
        showAllQueueSequence(activity)
    }

    fun showQueueEachType(activity: Display2Activity){
        if (waitingQueueList.isNotEmpty() && waitingQueueList.size != 0){
            var aCount = 0
            var bCount = 0
            var cCount = 0
            var dCount = 0

            activity.launch(Dispatchers.Default) {
                for (i in waitingQueueList.indices){
                    if (waitingQueueList[i].serviceChannel == "A"){
                        aCount += 1
                    } else if (waitingQueueList[i].serviceChannel == "B"){
                        bCount += 1
                    } else if (waitingQueueList[i].serviceChannel == "C"){
                        cCount += 1
                    } else if (waitingQueueList[i].serviceChannel == "D"){
                        dCount += 1
                    }
                }
                withContext(Dispatchers.Main){
                    activity.binding.totalQueueA.text = "$aCount"
                    activity.binding.totalQueueB.text = "$bCount"
                    activity.binding.totalQueueC.text = "$cCount"
                    activity.binding.totalQueueD.text = "$dCount"
                }
            }
        } else {
            uiScope.launch(Dispatchers.Main) {
                activity.binding.totalQueueA.text = "0"
                activity.binding.totalQueueB.text = "0"
                activity.binding.totalQueueC.text = "0"
                activity.binding.totalQueueD.text = "0"
            }
        }
    }

    fun showAllQueueSequence(activity: Display2Activity){
        if (queueList.isNotEmpty() && queueList.size != 0){
            activity.binding.queue1Txt.text = "-"
            activity.binding.counter1Txt.text = "-"
            activity.binding.queue2Txt.text = "-"
            activity.binding.counter2Txt.text = "-"
            activity.binding.queue3Txt.text = "-"
            activity.binding.counter3Txt.text = "-"
            activity.binding.queue4Txt.text = "-"
            activity.binding.counter4Txt.text = "-"

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
            }
        }
    }

    fun showAllQueueSequenceFix(activity: Display2Activity){
        activity.binding.queue1Txt.text = "-"
        activity.binding.counter1Txt.text = "-"
        activity.binding.queue2Txt.text = "-"
        activity.binding.counter2Txt.text = "-"
        activity.binding.queue3Txt.text = "-"
        activity.binding.counter3Txt.text = "-"
        activity.binding.queue4Txt.text = "-"
        activity.binding.counter4Txt.text = "-"

        if (queueList.isNotEmpty() && queueList.size != 0){
            //var queueCount = 1
            for (i in queueList.indices){
                if (queueList[i].serviceCounter == null || queueList[i].serviceCounter == 0){
                    println("Continue queue")
                    continue
                } else {
                    if (queueList[i].serviceChannel == "A"){
                        if (activity.binding.queue1Txt.text == "-" && activity.binding.counter1Txt.text == "-"){
                            activity.binding.queue1Txt.text = queueList[i].queueNum
                            activity.binding.counter1Txt.text = queueList[i].serviceCounter.toString()
                        }
                    } else if (queueList[i].serviceChannel == "B"){
                        if (activity.binding.queue2Txt.text == "-" && activity.binding.counter2Txt.text == "-"){
                            activity.binding.queue2Txt.text = queueList[i].queueNum
                            activity.binding.counter2Txt.text = queueList[i].serviceCounter.toString()
                        }
                    } else if (queueList[i].serviceChannel == "C"){
                        if (activity.binding.queue3Txt.text == "-" && activity.binding.counter3Txt.text == "-"){
                            activity.binding.queue3Txt.text = queueList[i].queueNum
                            activity.binding.counter3Txt.text = queueList[i].serviceCounter.toString()
                        }
                    } else if (queueList[i].serviceChannel == "D"){
                        if (activity.binding.queue4Txt.text == "-" && activity.binding.counter4Txt.text == "-"){
                            activity.binding.queue4Txt.text = queueList[i].queueNum
                            activity.binding.counter4Txt.text = queueList[i].serviceCounter.toString()
                        }
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

    fun showWaitingQueueCount(){
        if (waitingQueueList.isNotEmpty() && waitingQueueList.size > 0){
            waitingQueueLiveData.value = waitingQueueList.size
        } else {
            waitingQueueLiveData.value = 0
        }
    }

    fun showWaitingQueueCount2(){
        if (queueList.isNotEmpty() && queueList.size > 0){
            waitingQueueLiveData.value = queueList.size
        } else {
            waitingQueueLiveData.value = 0
        }
    }

    fun showNextQueue(){
        if (queueList.isNotEmpty() && queueList.size >= 2) {
            nextQueueLiveData.value = queueList[1].queueNum
        } else {
            nextQueueLiveData.value = "-"
        }
    }

    fun setQueueList(list: ArrayList<QueueResponse>){
        this.queueList = list
        //println("Queue size is ${this.queueList.size}")
    }

    fun setWaitingQueueList(list: ArrayList<QueueResponse>){
        this.waitingQueueList = list
    }

    fun setSpeechList(list: ArrayList<SoundQueueResponse>){
        this.soundList = list
    }

    fun getQueue(){
        mutableLiveData = repo.getQueueWithChannel()
    }

    fun getWaitingQueue(){
        mutableLiveData2 = repo.getWaitingQueueLiveData()
    }

    fun getSpeech(){
        mutableSoundLiveData = repo.getSoundQueueLiveData()
    }

    fun speech(){
        //tts.speak("เชิญหมายเลข ${soundList[0].queueNum} ค่ะ", TextToSpeech.QUEUE_FLUSH,null)
        //tts.speak("สวัสดีค่ะ", TextToSpeech.QUEUE_FLUSH,null)
        if(soundList.isNotEmpty() && soundList.size != 0){
            if (soundList[0].active == "Ready"){
//                println("Not null sound ${soundList[0].queueNum}")
//                //textToSpeech.speak("เชิญหมายเลข ${soundList[0].queueNum} ค่ะ"
//                //    , TextToSpeech.QUEUE_FLUSH,null)
                println("Condition true")

                tts.speak(mutableSoundLiveData.value!![0].msg, TextToSpeech.QUEUE_FLUSH,null)
                repo.updateSoundQueueData(mutableSoundLiveData.value!![0].sound_queueId,
                    mutableSoundLiveData.value!![0].queueId,"Completed")


            } else {
                println("Condition false")
            }
        }
    }

}