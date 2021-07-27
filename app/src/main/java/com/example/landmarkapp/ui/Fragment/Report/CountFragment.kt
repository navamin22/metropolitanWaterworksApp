package com.example.landmarkapp.ui.Fragment.Report

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.FragmentCountBinding
import com.example.landmarkapp.model.ReportTotalQueue
import com.example.landmarkapp.model.Retrofit.response.ReportResponse
import com.example.landmarkapp.ui.Activity.Report.DashboardActivity
import com.example.landmarkapp.ui.Adapters.Report.CountAdapter
import com.example.landmarkapp.ui.Fragment.BaseFragment
import com.example.landmarkapp.utils.StaticData
import com.example.landmarkapp.viewmodel.ReportViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CountFragment(private val activity: DashboardActivity, private val viewModel: ReportViewModel,
                    private val firstDate: String, private val lastDate: String, private val typeSelected: String, private val serviceChannel: String, private val serviceCounter: String, private val showEachTime: Boolean) : BaseFragment() {
    private lateinit var binding: FragmentCountBinding
    private lateinit var myContext: Context
    private lateinit var countAdapter: CountAdapter
    private val arrTotalQueue = ArrayList<ReportTotalQueue>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_count,container,false)
        initInstance()

        return binding.root
    }

    private fun initInstance(){
        observeData()
        setData()
        onClickActivate()
    }

    private fun onClickActivate(){
        binding.download.setOnClickListener {

        }
        binding.graph.setOnClickListener {
//            activity.supportFragmentManager.beginTransaction().apply {
//                replace(R.id.dbfragment,SatisfactionGraphFragment(activity,year,monthStr,countExcellent,countVeryGood,countGood,countFair,countPoor))
//                addToBackStack(null)
//                commit()
//            }
        }
    }

    private fun setData(){
        if(!showEachTime){
            binding.eachTime.visibility = GONE
        } else {
            binding.eachTime.visibility = VISIBLE
        }
        binding.firstday.text = firstDate
        binding.lastday.text = lastDate
    }

    private fun setAdapter(){
        countAdapter = CountAdapter(activity,arrTotalQueue,showEachTime)
        binding.recyclerCount.let {
            it.layoutManager = LinearLayoutManager(myContext)
            it.adapter = countAdapter
        }
    }

    private fun observeData(){
        viewModel.getReportBetweenDates(firstDate, lastDate, typeSelected, serviceChannel, serviceCounter)
        viewModel.betweenReportLiveDate.observe(activity, {
            viewModel.setReportList(it)
            calculateDate(it)
        })
    }

    private fun calculateDate(it: ArrayList<ReportResponse>){
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        val formatShowDate = SimpleDateFormat("dd-MM-yyyy", Locale.US)
        val formatPeriod = SimpleDateFormat("HH:mm:ss", Locale.US)
        val listCalendar = ArrayList<Calendar>()
        val listDate = ArrayList<String>()

        arrTotalQueue.clear()
        listCalendar.clear()
        listDate.clear()

        for (i in it.indices) {
            val dateReport = format.parse(it[i].queueFinishDate)
            val dayReport = formatShowDate.format(dateReport)
            val cal = Calendar.getInstance()
            cal.time = dateReport

            if (listCalendar.isNotEmpty()) {
                val year1 = cal.get(Calendar.YEAR)
                val day1 = cal.get(Calendar.DAY_OF_MONTH)
                val month1 = cal.get(Calendar.MONTH)
                var isHave = false

                for (j in listCalendar.indices) {
                    val year2 = listCalendar[j].get(Calendar.YEAR)
                    val day2 = listCalendar[j].get(Calendar.DAY_OF_MONTH)
                    val month2 = listCalendar[j].get(Calendar.MONTH)
//                        println("year1 is $year1")
//                        println("year2 is $year2")
//                        println("month1 is $month1")
//                        println("month2 is $month2")
//                        println("day1 is $day1")
//                        println("day2 is $day2")

                    if (year1 == year2 && month1 == month2 && day1 == day2) {
                        isHave = true
                        break
                    } else {
                        isHave = false
                    }
                }

                if (!isHave) {
                    listCalendar.add(cal)
                    listDate.add(dayReport)
                }

            } else {
                listCalendar.add(cal)
                listDate.add(dayReport)
            }
        }

        println("List Calendar is ${listCalendar.size}")

        for (i in listCalendar.indices) {
            //println("Date${i + 1} ${listDate[i].time}")
            val year2 = listCalendar[i].get(Calendar.YEAR)
            val month2 = listCalendar[i].get(Calendar.MONTH)
            val day2 = listCalendar[i].get(Calendar.DAY_OF_MONTH)
            val totalCount = ArrayList<Int>()

            for (j in StaticData.firstPeriod.indices){
                var countReport = 0
                for (k in it.indices){
                    val dateBeforeConvert = format.parse(it[k].queueFinishDate)
                    val cal1 = Calendar.getInstance()
                    cal1.time = dateBeforeConvert
                    val year1 = cal1.get(Calendar.YEAR)
                    val month1 = cal1.get(Calendar.MONTH)
                    val day1 = cal1.get(Calendar.DAY_OF_MONTH)

                    println("year1 is $year1")
                    println("year2 is $year2")
                    println("month1 is $month1")
                    println("month2 is $month2")
                    println("day1 is $day1")
                    println("day2 is $day2")

                    if (year1 == year2 && month1 == month2 && day1 == day2) {
                        println("Yes")
                        val periodDateConvert: String = formatPeriod.format(dateBeforeConvert)
                        val firstTime = formatPeriod.format(StaticData.firstPeriod[j])
                        val lastTime = formatPeriod.format(StaticData.lastPeriod[j])

                        //val dateTest = format.parse(StaticData.firstPeriod[j])

                        println("PeriodDateConvert is $periodDateConvert")
                        println("firstTime is $firstTime")
                        println("lastTime is $lastTime")
                        //println()

                        val dateReport = formatPeriod.parse(periodDateConvert)
                        val firstTimeCal = formatPeriod.parse(firstTime)
                        val lastTimeCal = formatPeriod.parse(lastTime)

                        //val dateReport = formatPeriod.parse(it[k].queueFinishDate)
                        //val cal = Calendar.getInstance()

                        if ((dateReport.after(firstTimeCal) || dateReport.equals(firstTimeCal))
                            && (dateReport.before(lastTimeCal) || dateReport.equals(lastTimeCal))){
                            countReport += 1
                            println("Check is True")
                        } else {
                            println("Check is false")
                        }

                    } else {
                        println("No")
                    }
                }
                totalCount.add(countReport)
                //listTotalCount.add(totalCount)
                println("j value is $j")
                println("Add count = $countReport")
                println("totalCount is $totalCount")
            }
            arrTotalQueue.add(ReportTotalQueue(listDate[i],totalCount))
        }

        println("ArrTotalQueue is $arrTotalQueue")
        setAdapter()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
    }
}