package com.example.landmarkapp.ui.Activity

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.ActivityPr2Binding
import com.example.landmarkapp.ui.DialogFragment.PRSettingQueue
import com.example.landmarkapp.utils.StaticData
import com.example.landmarkapp.utils.toast
import com.example.landmarkapp.viewmodel.PRViewModel2

class PRActivity2: BaseActivity() {
    private lateinit var binding: ActivityPr2Binding
    private val prViewModel by lazy {
        ViewModelProviders.of(this).get(PRViewModel2::class.java)
    }
    private lateinit var handler: Handler
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pr2)
        initInstance()
    }

    private fun initInstance(){
        setViewModel()
        observeData()
        onClickActivate()
        timing()
    }

    private fun onClickActivate(){
        binding.callBtn.setOnClickListener {
            if (StaticData.currentChannelType.isEmpty() && StaticData.currentChannelNo == 0) {
                toast("กรุณาเลือกประเภทเเละช่องการให้บริการก่อนใข้งานระบบคิวค่ะ")
            } else {
                prViewModel.callQueue()
            }
        }

        binding.repeatBtn.setOnClickListener {
            if (StaticData.currentChannelType.isEmpty() && StaticData.currentChannelNo == 0) {
                toast("กรุณาเลือกประเภทเเละช่องการให้บริการก่อนใข้งานระบบคิวค่ะ")
            } else {
                prViewModel.callRepeat()
            }
        }

        binding.skipBtn.setOnClickListener {
            if (StaticData.currentChannelType.isEmpty() && StaticData.currentChannelNo == 0) {
                toast("กรุณาเลือกประเภทเเละช่องการให้บริการก่อนใข้งานระบบคิวค่ะ")
            } else {
                prViewModel.skipQueue()
            }
        }

        binding.previousBtn.setOnClickListener {
            if (StaticData.currentChannelType.isEmpty() && StaticData.currentChannelNo == 0) {
                toast("กรุณาเลือกประเภทเเละช่องการให้บริการก่อนใข้งานระบบคิวค่ะ")
            } else {
                prViewModel.callPreviousQueue()
            }
        }

        binding.transferBtn.setOnClickListener {
            if (StaticData.currentChannelType.isEmpty() && StaticData.currentChannelNo == 0) {
                toast("กรุณาเลือกประเภทเเละช่องการให้บริการก่อนใข้งานระบบคิวค่ะ")
            } else {
                prViewModel.transferQueue(this)
            }
        }

        binding.clearPresentQueueBtn.setOnClickListener {
            if (StaticData.currentChannelType.isEmpty() && StaticData.currentChannelNo == 0) {
                toast("กรุณาเลือกประเภทเเละช่องการให้บริการก่อนใข้งานระบบคิวค่ะ")
            } else {
                prViewModel.clearPresentQueue()
            }
        }

        binding.clearQueueBtn.setOnClickListener {
            if (StaticData.currentChannelType.isEmpty() && StaticData.currentChannelNo == 0) {
                toast("กรุณาเลือกประเภทเเละช่องการให้บริการก่อนใข้งานระบบคิวค่ะ")
            } else {
                prViewModel.submitPasswordDialog(this)
            }
        }

        binding.settingImg.setOnClickListener {
            val fm = supportFragmentManager
            val dialog = PRSettingQueue(this)
            dialog.show(fm,"PRSettingQueue")
        }
    }

    private fun setViewModel(){
        binding.lifecycleOwner = this
        binding.viewModel = prViewModel
    }

    fun observeData(){
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
        handler.postDelayed(object : Runnable {
            override fun run() {
                prViewModel.getQueue()
                handler.postDelayed(this, 3000)
            }
        }, 3000)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        StaticData.screen_width = binding.root.measuredWidth
        StaticData.screen_height = binding.root.measuredHeight
        println("Width of screen = ${StaticData.screen_width}")
        println("Height of screen = ${StaticData.screen_height}")
    }

    override fun onStart() {
        super.onStart()
        val flags = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        window.decorView.systemUiVisibility = flags
        //window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun onBackPressed() {
        if(doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true;
        toast("กด Back อีกครั้งเพื่อออกจากโปรเเกรม")
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }
}