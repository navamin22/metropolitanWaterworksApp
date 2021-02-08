package com.example.landmarkapp.utils

import android.speech.tts.TextToSpeech
import com.bxl.config.editor.BXLConfigLoader
import com.example.landmarkapp.utils.BixolonPrinter.BixolonPrinter

class StaticData {
    companion object{
//        QueueUserApp = คิวผู้ใช้งาน
//        PRApp = ประชาสัมพันธ์
//        DisplayApp = แสดงผลหน้าจอ
        const val QueueUserApp: String = "QueueUser"
        const val PRApp: String = "Personal Relation"
        const val DisplayApp: String = "Display"

        const val url = "http://192.168.1.117/"
        const val url5 = "http://192.168.43.20/"
        const val url2 = "http://10.0.2.2/"
        const val folder = "Landmark/"
        const val php = "PHP/"
        const val queue_php = "Queue.php/"
        const val sound_queue_php = "SoundQueue.php/"
        const val rate_php = "Rate.php/"
        const val report_php = "Report.php/"

        //Bixolon Thermal Printer
        lateinit var bxlPrinter: BixolonPrinter
        const val portType = BXLConfigLoader.DEVICE_BUS_USB
        const val logicalName = "SRP-330II"
        const val ipAddress = ""
        const val paperLine = 41

        //Screen
        var screen_width = 0
        var screen_height = 0

        lateinit var tts: TextToSpeech
    }
}