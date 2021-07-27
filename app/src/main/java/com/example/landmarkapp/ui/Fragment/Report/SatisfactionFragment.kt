package com.example.landmarkapp.ui.Fragment.Report

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.FragmentSatisfactionBinding
import com.example.landmarkapp.model.Retrofit.response.RateResponse
import com.example.landmarkapp.ui.Activity.Report.DashboardActivity
import com.example.landmarkapp.viewmodel.DashBoardViewModel
import com.example.landmarkapp.viewmodel.ReportViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SatisfactionFragment(private val activity: DashboardActivity, private val viewModel: ReportViewModel,
                           private val firstDate: String, private val lastDate: String, private val ratedAccountId: Int) : Fragment() {
    private lateinit var binding: FragmentSatisfactionBinding
    private lateinit var myContext: Context
    var countPoor = 0
    var countFair = 0
    var countGood = 0
    var countVeryGood = 0
    var countExcellent = 0
    var allRate = 0
    var rateList = ArrayList<RateResponse>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_satisfaction,container,false)
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
        binding.firstday.text = firstDate
        binding.lastday.text = lastDate
    }

    private fun observeData(){
        viewModel.getRateBetweenDates(firstDate, lastDate, ratedAccountId)
        viewModel.betweenRateLiveData.observe(activity,
            Observer {
                viewModel.setRateList(it)
                activity.launch(Dispatchers.Default) {
                    countPoor = it.count { it1 -> it1.component3() == "Poor"}
                    countFair = it.count { it2 -> it2.component3() == "Fair"}
                    countGood = it.count { it3 -> it3.component3() == "Good"}
                    countVeryGood = it.count { it4 -> it4.component3() == "Very Good"}
                    countExcellent = it.count { it5 -> it5.component3() == "Excellent"}
                    allRate = countPoor + countFair + countGood + countVeryGood + countExcellent
                    withContext(Dispatchers.Main){
                        binding.all.text = allRate.toString()
                        binding.very.text = countPoor.toString()
                        binding.poor.text = countFair.toString()
                        binding.aver.text = countGood.toString()
                        binding.good.text = countVeryGood.toString()
                        binding.exce.text = countExcellent.toString()
                    }
                }
            })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
    }
}