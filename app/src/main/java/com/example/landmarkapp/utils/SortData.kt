package com.example.landmarkapp.utils

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SortData {
    fun sortPeriodTime(arr: Array<String>): ArrayList<Date>{
        val formatPeriod = SimpleDateFormat("HH:mm:ss", Locale.US)
        val periodTime = ArrayList<Date>()

        for (i in arr.indices){
            val date: Date = formatPeriod.parse(arr[i])
            //val cal = Calendar.getInstance()
            //cal.time = date
            println("Time is $date")
            periodTime.add(date)
        }

        return periodTime
    }
}