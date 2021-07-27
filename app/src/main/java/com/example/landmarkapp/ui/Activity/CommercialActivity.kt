package com.example.landmarkapp.ui.Activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.MediaController
import androidx.databinding.DataBindingUtil
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.ActivityCommercialBinding
import com.example.landmarkapp.model.ImageSlider
import com.example.landmarkapp.ui.Adapters.ImageSliderAdapter
import com.example.landmarkapp.utils.StaticData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CommercialActivity: BaseActivity() {
    private lateinit var binding: ActivityCommercialBinding
    private lateinit var adapters: ImageSliderAdapter
    private val imgSlider = ArrayList<ImageSlider>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_commercial)
        initInstance()
    }

    private fun initInstance(){
        //setVideo()
        setViewPager()
        onClickActivate()
    }

    private fun onClickActivate(){
        binding.constraintCommercial.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        toMainPage()
                    }
                }

                return v?.onTouchEvent(event) ?: true
            }
        })
    }

    private fun toMainPage(){
        startActivity(Intent(this@CommercialActivity, QueueUserActivity::class.java))
        finish()
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

    private fun setVideo(){
        val videoPath = "android.resource://"+ packageName + "/" + R.raw.hidden_beach_hawaii_4k
        val uri = Uri.parse(videoPath)
        binding.videoView.setVideoURI(uri)

        val mediaController = MediaController(this)
        binding.videoView.setMediaController(mediaController)
        mediaController.setAnchorView(binding.videoView)
        binding.videoView.start()
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
}