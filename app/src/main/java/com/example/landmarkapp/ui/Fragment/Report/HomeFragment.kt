package com.example.landmarkapp.ui.Fragment.Report

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.landmarkapp.Network.Repository
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.FragmentHomeBinding
import com.example.landmarkapp.model.HomeReport
import com.example.landmarkapp.model.Retrofit.response.RateResponse
import com.example.landmarkapp.model.Retrofit.response.ReportResponse
import com.example.landmarkapp.ui.Activity.Report.DashboardActivity
import com.example.landmarkapp.ui.Activity.Report.DashboardActivity.Companion.rateList
import com.example.landmarkapp.ui.Activity.Report.DashboardActivity.Companion.reportList
import com.example.landmarkapp.ui.Adapters.Report.HomeAdapters
import com.example.landmarkapp.ui.Fragment.BaseFragment
import com.example.landmarkapp.utils.StaticData
import com.example.landmarkapp.viewmodel.DashBoardViewModel
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment(private val activity: DashboardActivity, private val viewModel: DashBoardViewModel): BaseFragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var myContext: Context
    private lateinit var adapters: HomeAdapters
    private val repo = Repository()
    private val homeReportList = ArrayList<HomeReport>()
    val sdf = SimpleDateFormat("dd/MM/yyyy")
    val currentDate = sdf.format(Date())
    val day = SimpleDateFormat("d").format(Date()).toInt()
    val month = SimpleDateFormat("MM").format(Date()).toInt()
    val year = SimpleDateFormat("yyyy").format(Date()).toInt()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false)
        initInstance()

        return binding.root
    }

    private fun initInstance(){
        binding.materialTextView.text = "ข้อมูลผู้ใช้บริการ: วันที่ $currentDate "
        setAdapter()
        launch(Dispatchers.Main) { getData() }
    }

    private fun setAdapter(){
        adapters = HomeAdapters(activity,myContext,homeReportList)
        binding.recyclerCounterno.let {
            it.layoutManager = LinearLayoutManager(myContext)
            it.adapter = adapters
        }
    }

    suspend fun getData(){
        withContext(Dispatchers.Default){
            sortCounterNumber()
        }
        setAdapter()
    }

    private fun sortCounterNumber(){
        for (i in StaticData.counterList.indices){
            var count = 0
            var count2 = 0
            for (j in reportList.indices){
                if (reportList[j].serviceCounter == StaticData.counterList[i]){
                    count += 1
                }
            }
            for (k in rateList.indices){
                if (rateList[k].serviceCounter.toInt() == StaticData.counterList[i] && rateList[k].rateScore <= 2){
                    count2 += 1
                }
            }
            homeReportList.add(HomeReport(StaticData.counterList[i],count,count2))
        }
        println("HomeReportList is $homeReportList")
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
    }

}