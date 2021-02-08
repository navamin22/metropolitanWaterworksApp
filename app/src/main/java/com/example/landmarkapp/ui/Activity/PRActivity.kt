package com.example.landmarkapp.ui.Activity

import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.ActivityPrBinding
import com.example.landmarkapp.viewmodel.PRViewModel

class PRActivity : BaseActivity(){
    private lateinit var binding: ActivityPrBinding
    private val prViewModel by lazy {
        ViewModelProviders.of(this).get(PRViewModel::class.java)
    }
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pr)
        initInstance()
    }

    private fun initInstance(){
        setViewModel()
        observeData()
        timing()
    }

    private fun setViewModel(){
        binding.lifecycleOwner = this
        binding.viewModel = prViewModel
    }

    private fun observeData(){
        prViewModel.currentQueueLiveData.observe(this,
            Observer {
                binding.currentQueue.text = it.toString()
            })

        prViewModel.nextQueueLiveData.observe(this,
            Observer {
                binding.nextQueue.text = it.toString()
            })

        prViewModel.waitingQueueLiveData.observe(this,
            Observer {
                binding.waitQueue.text = "$it คิว"
            })

        prViewModel.mutableLiveData.observe(this,
            Observer {
                prViewModel.setQueueList(it)
                prViewModel.showCurrentQueue()
                prViewModel.showNextQueue()
                prViewModel.showWaitingQueueCount()
                //prViewModel.countDownLoop()
                //prViewModel.getQueueFromTimer()
            })

    }

    private fun timing(){
        handler = Handler()
        handler.postDelayed(object : Runnable{
            override fun run() {
                prViewModel.getQueue()
                handler.postDelayed(this,3000)
            }
        },3000)
    }
}