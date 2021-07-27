package com.example.landmarkapp.Network.Services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.webkit.MimeTypeMap
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.landmarkapp.R
import java.io.File

class NotificationsService: Service() {
    companion object {
        val NOTIFICATION_ID = 123
        val CHANNEL_ID_RATE= "Rate Service"
        val CHANNEL_ID_PDF = "PDF Service"
        var rateScore: Int? = 0
        var rateTitle: String = ""
        var date: String = ""
        var serviceCounter: Int? = 0
        var noticeType: String = ""
        var reasonRateLow: String = ""
        var file: File? = null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        rateScore = intent?.getIntExtra("rateScore",0)
        rateTitle = intent?.getStringExtra("rateTitle").toString()
        date = intent?.getStringExtra("date").toString()
        serviceCounter = intent?.getIntExtra("serviceCounter",0)
        noticeType = intent?.getStringExtra("noticeType").toString()
        reasonRateLow = intent?.getStringExtra("reasonRateLow").toString()
        file = intent!!.getSerializableExtra("file") as File?
        start(intent)
        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private fun start(intent: Intent?){
        if (noticeType == "RateNotification"){
            rateNotification()
        } else if(noticeType == "PDFNotification"){
            //pdfNotification(file!!)
        }
    }

    private fun rateNotification(){
        var ratingShow = ""
        if (rateTitle == "Poor"){
            ratingShow = "ปรับปรุง"
        } else if(rateTitle == "Fair"){
            ratingShow = "พอใช้"
        } else if(rateTitle == "Good"){
            ratingShow = "ดี"
        } else if(rateTitle == "Very Good"){
            ratingShow = "ดีมาก"
        } else if(rateTitle == "Excellent"){
            ratingShow = "ดีเยี่ยม"
        }

        if (reasonRateLow.isNotEmpty()){
            val icon = BitmapFactory.decodeResource(resources, R.drawable.logo)
            val channelId =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    createNotificationChannel(CHANNEL_ID_RATE, "Rate Notification Foreground")
                } else {
                    // If earlier version channel ID is not used
                    // https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context)
                    ""
                }

            val notificationBuilder = NotificationCompat.Builder(this, channelId)
            val notification = notificationBuilder
                .setStyle(
                    NotificationCompat.InboxStyle()
                        .addLine("ช่วงเวลา $date")
                        .addLine("ที่ช่องบริการที่ $serviceCounter")
                        .addLine("มีผู้ประเมินเกณฑ์ในระดับ $ratingShow")
                        .addLine("ด้วยเหตุผลคือ $reasonRateLow")
                        .addLine("ฝากพิจารณาด้วยค่ะ ขอบคุณค่ะ")
                )
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setLargeIcon(icon)
                .setContentTitle("ตรวจพบผู้ประเมินความพึงพอใจต่ำกว่าเกณฑ์")
                .setColor(ContextCompat.getColor(applicationContext,R.color.moreBlue))
                .setOngoing(false)
                .setAutoCancel(false)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(longArrayOf(100,200,300,400,500,400,300,200,400))
                .build()

            NotificationManagerCompat.from(applicationContext)
                .notify(NOTIFICATION_ID, notification)

//            startForeground(101, notification)
//            stopSelf()
        } else {
            val icon = BitmapFactory.decodeResource(resources, R.drawable.logo)
            val channelId =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    createNotificationChannel(CHANNEL_ID_RATE, "Rate Notification Foreground")
                } else {
                    // If earlier version channel ID is not used
                    // https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context)
                    ""
                }

            val notificationBuilder = NotificationCompat.Builder(this, channelId)
            val notification = notificationBuilder
                .setStyle(
                    NotificationCompat.InboxStyle()
                        .addLine("ช่วงเวลา $date")
                        .addLine("ที่ช่องบริการที่ $serviceCounter")
                        .addLine("มีผู้ประเมินเกณฑ์ในระดับ $ratingShow")
                        .addLine("ฝากพิจารณาด้วยค่ะ ขอบคุณค่ะ")
                )
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setLargeIcon(icon)
                .setContentTitle("ตรวจพบผู้ประเมินความพึงพอใจต่ำกว่าเกณฑ์")
                .setColor(ContextCompat.getColor(applicationContext,R.color.moreBlue))
                .setOngoing(false)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(longArrayOf(100,200,300,400,500,400,300,200,400))
                .build()

            NotificationManagerCompat.from(applicationContext)
                .notify(NOTIFICATION_ID, notification)

            startForeground(101, notification)
            stopSelf()
        }
    }

    private fun pdfNotification(file: File){
        val icon = BitmapFactory.decodeResource(resources, R.drawable.landmark_logo)
        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel(CHANNEL_ID_PDF, "PDF Notification Foreground")
            } else {
                // If earlier version channel ID is not used
                // https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context)
                ""
            }

        val fileUri = FileProvider.getUriForFile(applicationContext,applicationContext.packageName,file)
        val myMime: MimeTypeMap = MimeTypeMap.getSingleton()
        val mimeType = myMime.getMimeTypeFromExtension(
            MimeTypeMap.getFileExtensionFromUrl(fileUri.toString()))

        val intent = Intent(Intent.ACTION_VIEW)
        intent.let {
            it.setDataAndType(fileUri, mimeType)
            it.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            it.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }

        val pendingIntent = PendingIntent.getActivity(applicationContext,0,intent, PendingIntent.FLAG_CANCEL_CURRENT)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
        val notification = notificationBuilder
            .setStyle(
                NotificationCompat.InboxStyle()
                    .addLine("การประปานครหลวง")
                    .addLine("ไฟล์อัพโหลดเสร็จสิ้น")
            )
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setLargeIcon(icon)
            .setColor(ContextCompat.getColor(applicationContext,R.color.moreBlue))
            .setContentTitle("การประปานครหลวง")
            .setContentText("ไฟล์อัพโหลดเสร็จสิ้น กดเพื่อดูไฟล์ได้ที่นี่ค่ะ")
            .setOngoing(false)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setVibrate(longArrayOf(100,200,300,400,500,400,300,200,400))
            .build()

        NotificationManagerCompat.from(applicationContext)
            .notify(NOTIFICATION_ID, notification)

        startForeground(101, notification)
        stopSelf()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String{
        val chan = NotificationChannel(channelId,
            channelName, NotificationManager.IMPORTANCE_HIGH)
        chan.description = "description"
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = NotificationCompat.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
    }

}