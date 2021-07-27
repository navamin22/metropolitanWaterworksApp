package com.example.landmarkapp.ui.Activity

import android.app.AlertDialog
import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.ActivityQueueBinding
import com.example.landmarkapp.model.ImageSlider
import com.example.landmarkapp.ui.Adapters.ImageSliderAdapter
import com.example.landmarkapp.ui.DialogFragment.MoreBillsQueue
import com.example.landmarkapp.ui.DialogFragment.QrcodeDialog
import com.example.landmarkapp.ui.DialogFragment.RateDialog2
import com.example.landmarkapp.utils.BixolonPrinter.BixolonPrinter
import com.example.landmarkapp.utils.CheckInternet
import com.example.landmarkapp.utils.StaticData.Companion.bxlPrinter
import com.example.landmarkapp.utils.StaticData.Companion.ipAddress
import com.example.landmarkapp.utils.StaticData.Companion.logicalName
import com.example.landmarkapp.utils.StaticData.Companion.portType
import com.example.landmarkapp.utils.StaticData.Companion.screen_height
import com.example.landmarkapp.utils.StaticData.Companion.screen_width
import com.example.landmarkapp.utils.TPS780_Utils.Device.Connect
import com.example.landmarkapp.utils.TPS780_Utils.Device.StatusDescribe
import com.example.landmarkapp.utils.toast
import com.example.landmarkapp.viewmodel.QueueUserViewModel
import com.szsicod.print.escpos.PrinterAPI
import com.szsicod.print.utils.BitmapUtils.convertGreyImgByFloyd
import kotlinx.android.synthetic.main.dialog_thanks.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class QueueUserActivity : BaseActivity() {
    lateinit var binding: ActivityQueueBinding
    lateinit var timer: CountDownTimer
    private val queueUserViewModel by lazy {
        ViewModelProviders.of(this).get(QueueUserViewModel::class.java)
    }
    private lateinit var adapters: ImageSliderAdapter
    private lateinit var handler: Handler
    private val imgSlider = ArrayList<ImageSlider>()
    private var doubleBackToExitPressedOnce = false
    private var printerErrorStatus = ""
    private val mHandler = Handler(Handler.Callback {
        when (it.what) {
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_queue)
        initInstance()
        timing()
    }

    private fun initInstance(){
        binding.lifecycleOwner = this
        binding.viewModel = queueUserViewModel
        Connect().Connect_Devices(this, this)
        //openPrinter()
        setViewpager()
        observeData()
        onClickActivate()
    }

    private fun onClickActivate(){
        binding.getQueue.setOnClickListener {
            if(Connect().mPrinter.isConnect){
                timer.cancel()

                if (cheakStatesPrinter() == 0){
                    queueUserViewModel.getQueueSlip(this)
                    timing()
                } else {
                    val dialog = LayoutInflater.from(this).inflate(R.layout.dialog_alert, null)
                    val builder = AlertDialog.Builder(this).setView(dialog)
                    val alertDialog = builder.show()
                    dialog.optional_txt.text = printerErrorStatus
                    dialog.back_btn.setOnClickListener {
                        alertDialog.dismiss()
                        timing()
                    }
                }
            } else {
                //queueUserViewModel.getQueueSlip(this)
                timer.cancel()
                timing()
            }
        }

        binding.recommendBtn.setOnClickListener {
            if(Connect().mPrinter.isConnect){
                timer.cancel()
                if (cheakStatesPrinter() == 0){
                    queueUserViewModel.newPlumbingQueue(this)
                    timing()
                } else {
                    val dialog = LayoutInflater.from(this).inflate(R.layout.dialog_alert, null)
                    val builder = AlertDialog.Builder(this).setView(dialog)
                    val alertDialog = builder.show()
                    dialog.optional_txt.text = printerErrorStatus
                    dialog.back_btn.setOnClickListener {
                        alertDialog.dismiss()
                        timing()
                    }
                }
            } else {
                timer.cancel()
                timing()
            }

            //Connect().Connect_Devices(this, this)
        }

        binding.noticeBtn.setOnClickListener {
            if(Connect().mPrinter.isConnect){
                timer.cancel()
                if (cheakStatesPrinter() == 0){
                    val fm = supportFragmentManager
                    val dialog = MoreBillsQueue(this,queueUserViewModel)
                    dialog.show(fm, "MoreBillsQueue")
                    //queueUserViewModel.payBillQueue(this)
                    //timing()
                } else {
                    val dialog = LayoutInflater.from(this).inflate(R.layout.dialog_alert, null)
                    val builder = AlertDialog.Builder(this).setView(dialog)
                    val alertDialog = builder.show()
                    dialog.optional_txt.text = printerErrorStatus
                    dialog.back_btn.setOnClickListener {
                        alertDialog.dismiss()
                        timing()
                    }
                }
            } else {
                timer.cancel()
                timing()
            }
            //printImageQueue()
        }

        binding.btn6.setOnClickListener{
            val fm = supportFragmentManager
            val dialog = QrcodeDialog(this, queueUserViewModel)
            dialog.show(fm, "qrcode")
            timer.cancel()
            //timing()
        }

        binding.btn7.setOnClickListener {
            val fm = supportFragmentManager
            val dialog = RateDialog2(this,queueUserViewModel)
            dialog.show(fm, "Rating")
            timer.cancel()
            //timing()
        }
    }

    private fun setViewpager(){
        imgSlider.add(ImageSlider(R.drawable.imagewater1))
        imgSlider.add(ImageSlider(R.drawable.imagewater2))
//        imgSlider.add(ImageSlider(R.drawable.infog4))
        adapters = ImageSliderAdapter(this, imgSlider, binding.viewpager)
        binding.viewpager.adapter = adapters
        binding.viewpager.isUserInputEnabled = false
        imageSliderTimer()
    }

    fun observeData(){
        queueUserViewModel.getInitialCount()
        queueUserViewModel.getInitialCount2()
        queueUserViewModel.getInitialCount3()
        queueUserViewModel.getInitialCount4()
        queueUserViewModel.getInitialCount5()
        queueUserViewModel.mutableLiveData.observe(this,
            Observer {
                queueUserViewModel.setQueueList(it)
            })
    }

    fun timing(){
        timer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                startActivity(Intent(this@QueueUserActivity, CommercialActivity::class.java))
            }
        }.start()
    }

    fun printImageQueue(){
        if (PrinterAPI.SUCCESS == Connect().mPrinter.printRasterBitmap(
                toGrayscale(
                    loadBitmapFromView(
                        binding.constraintQueue.rootView
                    )
                )
            )){
            //Connect().mPrinter.printAndFeedLine(2)
            Connect().mPrinter.fullCut()
            binding.linearMenu.visibility = View.VISIBLE
            binding.constraintQueue.visibility = View.VISIBLE
        } else {
            toast("ทำการเปิด Printer ไม่สำเร็จ")
        }

//        if (PrinterAPI.SUCCESS == Connect().mPrinter.printRasterBitmap(toGrayscale(icon))){
//            Connect().mPrinter.printAndFeedLine(3)
//            Connect().mPrinter.fullCut()
//        }

    }

    private fun loadBitmapFromView(view: View): Bitmap{
        binding.linearMenu.visibility = View.GONE
        //binding.paymentToolbar.visibility = View.GONE
        view.measure(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT
        )
        val b: Bitmap = Bitmap.createBitmap(
            view.measuredWidth,
            view.measuredHeight,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(b)
        val a = view.measuredWidth
        val b1 = view.measuredHeight
        view.layout(0, 0, a, b1)
        view.draw(canvas)
        binding.constraintQueue.visibility = View.GONE

        return b

    }

    fun printStringTest(){
        Connect().mPrinter.printString("Hello World", "GBK", true)
        Connect().mPrinter.printAndFeedLine(5)
        Connect().mPrinter.fullCut()
    }

    fun toGrayscale(bmpOriginal: Bitmap): Bitmap? {
        val width: Int
        val height: Int
        height = bmpOriginal.height
        width = bmpOriginal.width
        val bmpGrayscale = Bitmap.createBitmap(
            width, height,
            Bitmap.Config.RGB_565
        )
        val c = Canvas(bmpGrayscale)
        val paint = Paint()
        val cm = ColorMatrix()
        cm.setSaturation(0f)
        val f = ColorMatrixColorFilter(cm)
        paint.colorFilter = f
        c.drawBitmap(bmpOriginal, 0f, 0f, paint)
        return convertGreyImgByFloyd(bmpGrayscale)
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

    fun cheakStatesPrinter(): Int {
        StatusDescribe.getStatusDescribe(Connect().mPrinter.status)
//        if (Connect().mPrinter.statusOverhead) {
//            printerErrorStatus = "ปรินท์เตอร์ร้อนเกินไปค่ะ"
//            toast(printerErrorStatus)
//            return 1
//        }
//        if (StatusDescribe.Headlift()) {
//            printerErrorStatus = "Cover open"
//            toast("Cover open")
//            return 2
//        }
        if (StatusDescribe.isOutOfPaper()) {
            printerErrorStatus = "กระดาษหมด \nกรุณาติดต่อเจ้าหน้าที่"
            toast("กระดาษหมด!! กรุณาเติมกระดาษเพิ่มด้วยค่ะ")
            return 3
        }
//        if (StatusDescribe.isPaperDone()) {
////            val dialog =
////                synDialog(this@MainActivity, "The paper will run out, whether to continue print")
////            dialog.setTitle("Hint")
//            printerErrorStatus = "กระดาษจะหมด \nกรุณาติดต่อเจ้าหน้าที่"
//            toast("กระดาษจะหมดเเล้ว!! กรุณาเติมกระดาษด้วยค่ะ")
//            return 4
//        }
        return 0
    }

    override fun onDestroy() {
        super.onDestroy()
        //bxlPrinter.printerClose()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        screen_width = binding.root.measuredWidth
        screen_height = binding.root.measuredHeight
        println("Width of screen = $screen_width")
        println("Height of screen = $screen_height")
    }

    companion object {
        private const val STATUS = 3
        private const val openfileDialogId = 0
        private const val NO_CONNECT = 10
        fun GetTime(): String {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss") // HH:mm:ss
            val date = Date(System.currentTimeMillis())
            return simpleDateFormat.format(date)
        }
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
}