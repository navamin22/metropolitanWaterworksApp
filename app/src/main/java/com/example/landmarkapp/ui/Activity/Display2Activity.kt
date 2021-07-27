package com.example.landmarkapp.ui.Activity

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.text.TextUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.ActivityDisplayUpgradeBinding
import com.example.landmarkapp.model.ImageSlider
import com.example.landmarkapp.ui.Adapters.ImageSliderAdapter
import com.example.landmarkapp.utils.StaticData
import com.example.landmarkapp.viewmodel.Display2ViewModel
import com.example.landmarkapp.viewmodel.DisplayFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Display2Activity: BaseActivity() {
    lateinit var binding: ActivityDisplayUpgradeBinding
    private lateinit var adapters: ImageSliderAdapter
    private lateinit var displayViewModel: Display2ViewModel
    private lateinit var viewModelFactory: DisplayFactory
    private lateinit var handler: Handler
    private val imgSlider = ArrayList<ImageSlider>()
    private var countDown: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_display_upgrade)
        initInstance()
    }

    private fun initInstance(){
        binding.marqueeTxt.ellipsize = TextUtils.TruncateAt.MARQUEE
        binding.marqueeTxt.isSelected = true
        setViewModel()
        setViewPager()
        observeData()
        timing()
        countDownLoop()
    }

    private fun setViewModel(){
        viewModelFactory = DisplayFactory(StaticData.tts)
        displayViewModel = ViewModelProviders.of(this, viewModelFactory).get(Display2ViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = displayViewModel
    }

    private fun setViewPager(){
        imgSlider.add(ImageSlider(R.drawable.water1))
        imgSlider.add(ImageSlider(R.drawable.water2))
        imgSlider.add(ImageSlider(R.drawable.water3))
        imgSlider.add(ImageSlider(R.drawable.water4))
        adapters = ImageSliderAdapter(this, imgSlider, binding.viewPager2)
        binding.viewPager2.adapter = adapters
        binding.viewPager2.isUserInputEnabled = false
        imageSliderTimer()
    }

    private fun imageSliderTimer(){
        launch(Dispatchers.Main) {
            while (true){
                delay(5000)
                if (binding.viewPager2.currentItem +1 < adapters.itemCount){
                    binding.viewPager2.currentItem += 1
                } else {
                    binding.viewPager2.currentItem = 0
                }
            }
        }
    }

    private fun observeData(){
        displayViewModel.waitingQueueLiveData.observe(this,
            Observer {
                binding.waitingQueue.text = "$it คิว"
            })

        displayViewModel.mutableLiveData.observe(this,
            Observer {
                displayViewModel.setQueueList(it)
                displayViewModel.showAllQueueSequence(this)
                displayViewModel.showCurrentQueue()
                displayViewModel.showNextQueue()
            })

        displayViewModel.mutableLiveData2.observe(this,
            {
                displayViewModel.setWaitingQueueList(it)
                displayViewModel.showQueueEachType(this)
                displayViewModel.showWaitingQueueCount()
            })

        displayViewModel.mutableSoundLiveData.observe(this,
            Observer {
                displayViewModel.setSpeechList(it)
            })

    }

    private fun timing(){
        handler = Handler()
        handler.postDelayed(object : Runnable{
            override fun run() {
                displayViewModel.getQueue()
                displayViewModel.getWaitingQueue()
                displayViewModel.getSpeech()
                handler.postDelayed(this,3000)
            }
        },3000)
    }

    private fun countDownLoop(){
        countDown = object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                displayViewModel.speech()
                start()
            }
        }
        countDown?.start()
    }

}