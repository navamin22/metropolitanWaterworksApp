package com.example.landmarkapp.ui.Activity

import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bxl.config.editor.BXLConfigLoader
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.ActivityQueueBinding
import com.example.landmarkapp.model.ImageSlider
import com.example.landmarkapp.ui.Adapters.ImageSliderAdapter
import com.example.landmarkapp.ui.DialogFragment.QrcodeDialog
import com.example.landmarkapp.ui.DialogFragment.RateDialog
import com.example.landmarkapp.ui.DialogFragment.RateDialog2
import com.example.landmarkapp.utils.BixolonPrinter.BixolonPrinter
import com.example.landmarkapp.utils.CheckInternet
import com.example.landmarkapp.utils.StaticData.Companion.bxlPrinter
import com.example.landmarkapp.utils.StaticData.Companion.ipAddress
import com.example.landmarkapp.utils.StaticData.Companion.logicalName
import com.example.landmarkapp.utils.StaticData.Companion.portType
import com.example.landmarkapp.utils.StaticData.Companion.screen_height
import com.example.landmarkapp.utils.StaticData.Companion.screen_width
import com.example.landmarkapp.utils.toast
import com.example.landmarkapp.viewmodel.QueueUserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QueueUserActivity : BaseActivity() {
    private lateinit var binding: ActivityQueueBinding
    private val queueUserViewModel by lazy {
        ViewModelProviders.of(this).get(QueueUserViewModel::class.java)
    }
    private lateinit var adapters: ImageSliderAdapter
    private val imgSlider = ArrayList<ImageSlider>()
    private val mHandler = Handler(Handler.Callback {
        when(it.what){
            0 -> {
                println("OK Printer Enable")
                window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }
            1 -> {
                val data = it.obj as String
                if (data != null && data.isNotEmpty()) {
                    Toast.makeText(applicationContext, data, Toast.LENGTH_LONG).show()
                }
                println("Printer cannot use")
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }
        }
        false
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_queue)
        initInstance()
    }

    private fun initInstance(){
        binding.lifecycleOwner = this
        binding.viewModel = queueUserViewModel
        openPrinter()
        setViewpager()
        observeData()
        onClickActivate()
    }

    private fun onClickActivate(){
        binding.btn7.setOnClickListener {
            val fm = supportFragmentManager
            val dialog = RateDialog2(this,queueUserViewModel)
            dialog.show(fm,"Rating")
        }

        binding.btn6.setOnClickListener{
            val fm = supportFragmentManager
            val dialog = QrcodeDialog()
            dialog.show(fm, "qrcode")
            toast("ชำระเงินด้วย QR Code")
        }

        binding.getQueue.setOnClickListener{
            toast("ชำระค่าประปา/ค่าไฟฟ้า")
        }

        binding.recommendBtn.setOnClickListener {
            toast("ติดตั้งประปาใหม่/บริการอื่นๆ")
        }

        binding.noticeBtn.setOnClickListener {
            toast("ชำระค่าติดตั้งประปาใหม่")
        }

    }


    private fun setViewpager(){
        imgSlider.add(ImageSlider(R.drawable.imagewater1))
        imgSlider.add(ImageSlider(R.drawable.imagewater2))
//        imgSlider.add(ImageSlider(R.drawable.infog4))
        adapters = ImageSliderAdapter(this,imgSlider,binding.viewpager)
        binding.viewpager.adapter = adapters
        binding.viewpager.isUserInputEnabled = false
        imageSliderTimer()
    }

    private fun observeData(){
        queueUserViewModel.getInitialCount()
        queueUserViewModel.mutableLiveData.observe(this,
            Observer {
                queueUserViewModel.setQueueList(it)
            })
    }

    private fun openPrinter(){
        bxlPrinter = BixolonPrinter(this)
        if (CheckInternet().isNetworkAvailable(this)){
            launch(Dispatchers.Default){
                if (bxlPrinter.printerOpen(
                        portType,
                        logicalName,
                        ipAddress,
                        false
                    )
                ) {
                    println("Printer1 connected")
                } else {
                    mHandler.obtainMessage(1, 0, 0, "Fail to printer1 open!!").sendToTarget()
                }
            }
        }
//        if (CheckInternet().isNetworkAvailable(this)){
//            launch(Dispatchers.Default){
//                if (bxlPrinter.printerOpen(
//                        BXLConfigLoader.DEVICE_BUS_ETHERNET,
//                        logicalName,
//                        "192.168.1.37",
//                        false
//                    )
//                ) {
//                    bxlPrinter.printText("A001",BixolonPrinter.ALIGNMENT_CENTER,BixolonPrinter.ATTRIBUTE_FONT_B,6)
//                    bxlPrinter.cutPaper()
//                    bxlPrinter.printerClose()
//
//                    println("Printer1 connected")
//                } else {
//                    mHandler.obtainMessage(1, 0, 0, "Fail to printer1 open!!").sendToTarget()
//                }
//            }
//        }

//        if (CheckInternet().isNetworkAvailable(this)){
//            launch(Dispatchers.Default){
//                if (bxlPrinter.printerOpen(
//                        BXLConfigLoader.DEVICE_BUS_ETHERNET,
//                        logicalName,
//                        "192.168.1.37",
//                        false
//                    )
//                ) {
//                    println("Printer1 connected")
//                } else {
//                    mHandler.obtainMessage(1, 0, 0, "Fail to printer1 open!!").sendToTarget()
//                }
//            }
//        }

    }

    private fun imageSliderTimer(){
        launch(Dispatchers.Main) {
            while (true){
                delay(5000)
                if (binding.viewpager.currentItem +1 < adapters.itemCount){
                    binding.viewpager.currentItem += 1
                } else {
                    binding.viewpager.currentItem = 0
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bxlPrinter.printerClose()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        screen_width = binding.root.measuredWidth
        screen_height = binding.root.measuredHeight
        println("Width of screen = $screen_width")
        println("Height of screen = $screen_height")
    }

}