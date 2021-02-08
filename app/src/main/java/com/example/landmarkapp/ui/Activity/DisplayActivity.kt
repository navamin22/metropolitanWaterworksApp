package com.example.landmarkapp.ui.Activity

import android.R.attr.x
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.landmarkapp.Network.Repository
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.ActivityDisplayBinding
import com.example.landmarkapp.utils.StaticData.Companion.tts
import com.example.landmarkapp.viewmodel.DisplayFactory
import com.example.landmarkapp.viewmodel.DisplayViewModel
import kotlinx.coroutines.Runnable
import java.util.*


class DisplayActivity : BaseActivity() {
    private val repo: Repository = Repository()
    private lateinit var binding: ActivityDisplayBinding
    private lateinit var displayViewModel: DisplayViewModel
    private lateinit var viewModelFactory: DisplayFactory
    private lateinit var handler: Handler
    private var countDown: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_display)
        initInstance()
    }

    private fun initInstance(){
        setViewModel()
        observeData()
        timing()
        countDownLoop()
    }

    private fun setViewModel(){
        viewModelFactory = DisplayFactory(tts)
        displayViewModel = ViewModelProviders.of(this, viewModelFactory).get(DisplayViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = displayViewModel
    }

    private fun timing(){
        handler = Handler()
        handler.postDelayed(object : Runnable{
            override fun run() {
                displayViewModel.getQueue()
                displayViewModel.getSpeech()
                handler.postDelayed(this,3000)
            }
        },3000)
    }

    private fun observeData(){
        displayViewModel.currentQueueLiveData.observe(this,
            Observer {
                binding.currentQueue.text = it.toString()
            })

        displayViewModel.waitingQueueLiveData.observe(this,
            Observer {
                binding.waitQueue.text = "$it คิว"
            })

        displayViewModel.mutableLiveData.observe(this,
            Observer {
                displayViewModel.setQueueList(it)
                displayViewModel.showCurrentQueue()
                displayViewModel.showWaitingQueueCount()
                displayViewModel.getQueueFromTimer()
            })

        displayViewModel.mutableSoundLiveData.observe(this,
            Observer {
                displayViewModel.setSpeechList(it)
            })

        binding.waitTitle.setOnClickListener {
            displayViewModel.speech()
        }
    }

    private fun countDownLoop(){
        countDown = object : CountDownTimer(4000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                displayViewModel.speech()
                start()
            }
        }
        countDown?.start()
    }

    private fun speakTTS(str: String){
        tts = TextToSpeech(application, TextToSpeech.OnInitListener {
            if (it == TextToSpeech.SUCCESS){
                tts.language = Locale.forLanguageTag("th")
                tts.setSpeechRate(0.7f)
                tts.speak(str, TextToSpeech.QUEUE_FLUSH,null)

                //val speakTxt = "เชิญหมายเลข $str ค่ะ"
                //tts.stop()
                //tts.shutdown()

            } else {
                Log.e("TTS", "Initilization Failed!")
            }
        })
    }
}