package com.example.landmarkapp.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.Rating
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import android.util.Log
import android.view.Display
import android.view.WindowManager
import android.widget.Toast
import com.example.landmarkapp.ui.Activity.*
import com.example.landmarkapp.ui.Activity.Report.AccountActivity
import com.example.landmarkapp.utils.StaticData
import com.example.landmarkapp.utils.StaticData.Companion.DisplayApp
import com.example.landmarkapp.utils.StaticData.Companion.DisplayPortraitApp
import com.example.landmarkapp.utils.StaticData.Companion.PRApp
import com.example.landmarkapp.utils.StaticData.Companion.QueueUserApp
import com.example.landmarkapp.utils.StaticData.Companion.RatingApp
import com.example.landmarkapp.utils.StaticData.Companion.ReportApp
import com.example.landmarkapp.utils.StaticData.Companion.tts
import kotlinx.coroutines.Dispatchers
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
        if (ReportApp != null){
            when (ReportApp) {
                "QueueUser" -> {
                    launch {
                        delay(2000)
                        startActivity(Intent(this@SplashScreen, CommercialActivity::class.java))
                        finishAffinity()
                    }
                }
                "Personal Relation" -> {
                    launch {
                        delay(2000)
                        startActivity(Intent(this@SplashScreen, LoginCounterActivity::class.java))
                        finishAffinity()
                    }
                }
                "Display" -> {
                    launch {
                        delay(2000)
//                        startActivity(Intent(this@SplashScreen, DisplayActivity::class.java))
                        startActivity(Intent(this@SplashScreen, Display2Activity::class.java))
                        finishAffinity()
                    }
                }
                "DisplayPortrait" -> {
                    launch {
                        delay(2000)
                        startActivity(Intent(this@SplashScreen, DisplayPortraitActivity::class.java))
                        finishAffinity()
                    }
                }
                "Rating" -> {
                    launch {
                        delay(2000)
                        startActivity(Intent(this@SplashScreen, RateActivity::class.java))
                        finishAffinity()
                    }
                }
                "Report" -> {
                    launch {
                        delay(2000)
                        startActivity(Intent(this@SplashScreen, AccountActivity::class.java))
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

    //Auto Login Don't Delete
    private fun isLoggedIn(){
        val sp: SharedPreferences = getSharedPreferences("LOGIN_EMPLOYEE", Context.MODE_PRIVATE)
        val sp2: SharedPreferences = getSharedPreferences("SERVICE_QUEUE_SETTING", Context.MODE_PRIVATE)
        StaticData.accountId = sp.getInt("accountId",0)
        StaticData.username = sp.getString("username","").toString()
        StaticData.name = sp.getString("name","").toString()
        StaticData.surname = sp.getString("surname","").toString()
        StaticData.currentChannelType = sp2.getString("serviceChannel","").toString()
        StaticData.currentChannelNo = sp2.getInt("serviceCounter",0)

        launch(Dispatchers.Default) {
            if (StaticData.accountId != 0 && StaticData.username.isNotEmpty() && StaticData.name.isNotEmpty() && StaticData.surname.isNotEmpty()){
                StaticData.password = sp.getString("password","").toString()
                StaticData.tel = sp.getString("tel","").toString()
                StaticData.email = sp.getString("email","").toString()
                StaticData.userType = sp.getString("userType","").toString()
                StaticData.status = sp.getString("status","").toString()
                StaticData.active = sp.getString("active","").toString()
                println("accountId = ${StaticData.accountId}")
                println("username = ${StaticData.username}")
                println("password = ${StaticData.password}")
                println("name = ${StaticData.name}")
                println("surname = ${StaticData.surname}")
                println("tel = ${StaticData.tel}")
                println("email = ${StaticData.email}")
                println("status = ${StaticData.status}")
                println("active = ${StaticData.active}")
                delay(2000)

                startActivity(Intent(this@SplashScreen, PRActivity2::class.java))
                finishAffinity()
            } else {
                delay(2000)
                startActivity(Intent(this@SplashScreen, LoginCounterActivity::class.java))
                finishAffinity()
            }
        }
    }
}