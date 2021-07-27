package com.example.landmarkapp.ui.Activity

import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.ActivityDisplayQueuePortraitBinding
import com.example.landmarkapp.viewmodel.Display2ViewModel
import com.example.landmarkapp.viewmodel.DisplayPortraitViewModel

class DisplayPortraitActivity: BaseActivity() {
    lateinit var binding: ActivityDisplayQueuePortraitBinding
    private lateinit var displayViewModel: DisplayPortraitViewModel
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_display_queue_portrait)
        initInstance()
    }

    private fun initInstance(){
        setViewModel()
        observeData()
        timing()
    }

    private fun observeData(){
        displayViewModel.mutableLiveData.observe(this,
            Observer {
                displayViewModel.setQueueList(it)
                displayViewModel.showAllQueueSequence(this)
            })
    }

    private fun setViewModel(){
        displayViewModel = ViewModelProviders.of(this).get(DisplayPortraitViewModel::class.java)
        binding.lifecycleOwner = this
    }

    private fun timing(){
        handler = Handler()
        handler.postDelayed(object : Runnable{
            override fun run() {
                displayViewModel.getQueue()
                handler.postDelayed(this,3000)
            }
        },3000)
    }
}