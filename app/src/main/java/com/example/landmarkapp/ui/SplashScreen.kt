package com.example.landmarkapp.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import android.util.Log
import android.view.Display
import android.view.WindowManager
import android.widget.Toast
import com.example.landmarkapp.ui.Activity.BaseActivity
import com.example.landmarkapp.ui.Activity.PRActivity
import com.example.landmarkapp.ui.Activity.DisplayActivity
import com.example.landmarkapp.ui.Activity.QueueUserActivity
import com.example.landmarkapp.utils.StaticData.Companion.DisplayApp
import com.example.landmarkapp.utils.StaticData.Companion.PRApp
import com.example.landmarkapp.utils.StaticData.Companion.QueueUserApp
import com.example.landmarkapp.utils.StaticData.Companion.tts
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class SplashScreen : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkTypeApp()
        openTTS()
    }

    private fun checkTypeApp(){
        if (QueueUserApp != null){
            when (QueueUserApp) {
                "QueueUser" -> {
                    launch {
                        delay(2000)
                        startActivity(Intent(this@SplashScreen, QueueUserActivity::class.java))
                        finishAffinity()
                    }
                }
                "Personal Relation" -> {
                    launch {
                        delay(2000)
                        startActivity(Intent(this@SplashScreen, PRActivity::class.java))
                        finishAffinity()
                    }
                }
                "Display" -> {
                    launch {
                        delay(2000)
                        startActivity(Intent(this@SplashScreen, DisplayActivity::class.java))
                        finishAffinity()
                    }
                }
            }
        } else {
            println("TypeApp value is null")
        }
    }

    private fun openTTS(){
        tts = TextToSpeech(application, TextToSpeech.OnInitListener {
            if (it == TextToSpeech.SUCCESS){
//                val voice = Voice("it-it-x-kda#male_2-local",Locale.getDefault(),1,1,false,null)
//                tts.voice = voice
//                tts.setPitch(100.0f)
                tts.language = Locale.forLanguageTag("th")
                tts.setSpeechRate(0.7f)
            } else {
                Log.e("TTS", "Initilization Failed!")
            }
        })
    }
}