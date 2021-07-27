package com.example.landmarkapp.viewmodel

import android.speech.tts.TextToSpeech
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DisplayFactory(val tts: TextToSpeech): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass == DisplayViewModel::class.java)
            return DisplayViewModel(tts) as T

        if (modelClass == Display2ViewModel::class.java)
            return Display2ViewModel(tts) as T

        throw IllegalArgumentException("Unknown ViewModel class : ${modelClass.name}")
    }
}