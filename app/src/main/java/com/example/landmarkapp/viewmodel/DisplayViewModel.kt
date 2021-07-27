package com.example.landmarkapp.viewmodel

import android.speech.tts.TextToSpeech
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.landmarkapp.Network.Repository
import com.example.landmarkapp.model.Retrofit.response.QueueResponse
import com.example.landmarkapp.model.Retrofit.response.SoundQueueResponse
import kotlinx.coroutines.*
import java.util.logging.Handler
import kotlin.collections.ArrayList

class DisplayViewModel(tts : TextToSpeech) : ViewModel() {
    private val repo: Repository = Repository()
    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)
    private var queueList = ArrayList<QueueResponse>()
    private var soundList = ArrayList<SoundQueueResponse>()
    private lateinit var textToSpeech: TextToSpeech
    var mutableLiveData: MutableLiveData<ArrayList<QueueResponse>> = repo.getQueueLiveData()
    var mutableSoundLiveData: MutableLiveData<ArrayList<SoundQueueResponse>> = repo.getSoundQueueLiveData()
    var currentQueueLiveData = MutableLiveData<CharSequence>()
    var waitingQueueLiveData = MutableLiveData<Int>()
    private lateinit var handler: Handler
    val tts : TextToSpeech = tts

    fun getQueueFromTimer(){
//        uiScope.launch {
//            while (true){
//                getQueue()
//                getSpeech()
//                delay(10000)
//            }
//        }
    }

    fun showCurrentQueue(){
        if (queueList.isNotEmpty() && queueList.size != 0){
            currentQueueLiveData.value = queueList[0].queueNum
        } else {
            currentQueueLiveData.value = "-"
        }
    }

    fun showWaitingQueueCount(){
        if (queueList.isNotEmpty() && queueList.size > 0){
            waitingQueueLiveData.value = queueList.size -1
        } else {
            waitingQueueLiveData.value = 0
        }
    }

    fun setQueueList(list: ArrayList<QueueResponse>){
        this.queueList = list
        //println("Queue size is ${this.queueList.size}")
    }

    fun setSpeechList(list: ArrayList<SoundQueueResponse>){
        this.soundList = list
    }

    fun getQueue(){
        mutableLiveData = repo.getQueueLiveData()
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

    fun setSpeech(){

//        textToSpeech = TextToSpeech(getApplication(), TextToSpeech.OnInitListener {
//            if (it == TextToSpeech.SUCCESS){
//                textToSpeech.language = Locale.forLanguageTag("th")
//                textToSpeech.setSpeechRate(0.7f)
//            } else {
//                Log.e("TTS", "Initilization Failed!");
//            }
//        })
    }


}
