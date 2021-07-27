package com.example.landmarkapp.utils

import android.speech.tts.TextToSpeech
import com.bxl.config.editor.BXLConfigLoader
import com.example.landmarkapp.utils.BixolonPrinter.BixolonPrinter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class StaticData {
    companion object{
        //จำนวนเคาเตอร์ที่ใช้บริการทั้งหมดของสาขา
        val counterList = arrayOf(1,2,3,4,5,6,7,8,9,10)
        val counterOfList = arrayOf("1","2","3","4","5","6","7","8","9","10")
        val firstPeriodList = arrayOf("8.30","9.30","10.30","11.30","12.30","13.30","14.30","15.30")
        val lastPeriodList = arrayOf("9.30","10.30","11.30","12.30","13.30","14.30","15.30","16.30")

        private val firstPeriodTime = arrayOf("08:30:00","09:31:00","10:31:00","11:31:00","12:31:00","13:31:00","14:31:00","15:31:00")
        private val lastPeriodTime = arrayOf("09:30:59","10:30:59","11:30:59","12:30:59","13:30:59","14:30:59","15:30:59","16:30:59")
        private val firstPeriodTime2 = arrayOf("10:31:00","14:31:00")
        private val lastPeriodTime2 = arrayOf("11:30:59","15:30:59")

        val firstPeriod: ArrayList<Date> = SortData().sortPeriodTime(firstPeriodTime)
        val lastPeriod: ArrayList<Date> = SortData().sortPeriodTime(lastPeriodTime)

        val rateOfList = arrayOf("เลือกทั้งหมด","พนักงาน")
        val typeOfList = arrayOf("รับชำระเงิน","รับคำร้อง","สำรวจเส้นท่อ")
        val typeOfListChar = arrayOf("A","B","C")
        val reasonList = arrayOf("โปรดระบุเหตุผล","การบริการของพนักงานบริการไม่ดี", "สถานที่", "อื่นๆ")
        val counterChoiceList = arrayOf("เลือกทั้งหมด","การให้บริการ","ช่องบริการ")
        val reportTypeOf = arrayOf("เลือกทั้งหมด","การให้บริการ","ช่องบริการ","พนักงาน")
        val typeService = arrayOf("รับชำระเงิน","รับคำร้อง","สำรวจเส้นท่อ")
        //QueueUserApp = คิวผู้ใช้งาน
        //PRApp = ประชาสัมพันธ์
        //DisplayApp = แสดงผลหน้าจอ
        const val QueueUserApp: String = "QueueUser"
        const val PRApp: String = "Personal Relation"
        const val DisplayApp: String = "Display"
        const val DisplayPortraitApp: String = "DisplayPortrait"
        const val RatingApp: String = "Rating"
        const val ReportApp: String = "Report"

        const val url = "http://192.168.1.117/"
        //const val url = "http://192.168.1.103/"
        //const val url = "http://192.168.1.38/"
        const val url5 = "http://192.168.43.20/"
        const val url2 = "http://10.0.2.2/"
        const val folder = "Landmark/"
        const val php = "PHP/"
        const val queue_php = "Queue.php/"
        const val sound_queue_php = "SoundQueue.php/"
        const val rate_php = "Rate.php/"
        const val report_php = "Report.php/"
        const val account_php = "Account.php/"
        const val t_queue_php = "t_queue.php/"

        //Bixolon Thermal Printer
        lateinit var bxlPrinter: BixolonPrinter
        const val portType = BXLConfigLoader.DEVICE_BUS_USB
        const val logicalName = "SRP-330II"
        const val ipAddress = ""
        const val paperLine = 41
        const val paperLineTPS780 = 45

        //Screen
        var screen_width = 0
        var screen_height = 0

        var queueCount = 0
        var queueCount2 = 0
        var queueCount3 = 0
        var queueCount4 = 0
        var queueCount5 = 0

        val userId_A = 1112
        val userId_B = 1234
        val userId_C = 4321
        val userId_D = 9919

        val channelType1 = "A"
        val channelType2 = "B"
        val channelType3 = "C"
        val channelType4 = "D"
        val channelNo1 = 1
        val channelNo2 = 2
        val channelNo3 = 3
        val channelNo4 = 4

        var currentUserId = userId_B
        var currentChannelType = ""
        var currentChannelNo = 1

        //Account
        var accountId = 0
        var username = ""
        var password = ""
        var name = ""
        var surname = ""
        var email = ""
        var tel = ""
        var userType = ""
        var status = ""
        var active = ""

        //SelectDate
        var year_sel = 0
        var month_sel = 0

        lateinit var tts: TextToSpeech

    }
}