package com.example.landmarkapp.ui.Fragment.Report

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.FragmentTimeBinding
import com.example.landmarkapp.model.Retrofit.response.ReportResponse
import com.example.landmarkapp.ui.Activity.Report.DashboardActivity
import com.example.landmarkapp.ui.Adapters.Report.TimeAdapter
import com.example.landmarkapp.ui.Fragment.BaseFragment
import com.example.landmarkapp.viewmodel.ReportViewModel

class TimeFragment(private val activity: DashboardActivity, private val viewModel: ReportViewModel,
                   private val firstDate: String, private val lastDate: String, private val typeSelected: String,
                   private val serviceChannel: String, private val serviceCounter: String, private val userId: Int): BaseFragment() {
    private lateinit var binding: FragmentTimeBinding
    private lateinit var adapters: TimeAdapter
    private lateinit var myContext: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_time, container, false)
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
    }

    private fun observeData(){
        viewModel.getReportBetweenDates(firstDate,lastDate,typeSelected,serviceChannel,serviceCounter,userId)
        viewModel.timeReportLiveData.observe(activity, {
            viewModel.setReportList(it)
            setAdapter(it)
        })
    }

    private fun setData(){
        binding.firstday.text = firstDate
        binding.lastday.text = lastDate
    }

    private fun setAdapter(items: ArrayList<ReportResponse>){
        adapters = TimeAdapter(activity, items)
        binding.recyclerTime.let {
            it.layoutManager = LinearLayoutManager(myContext)
            it.adapter = adapters
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
    }
}