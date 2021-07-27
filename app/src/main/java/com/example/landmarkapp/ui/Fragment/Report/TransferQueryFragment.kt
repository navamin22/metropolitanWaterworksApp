package com.example.landmarkapp.ui.Fragment.Report

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.FragmentQueryTransferBinding
import com.example.landmarkapp.ui.Activity.Report.DashboardActivity
import com.example.landmarkapp.ui.DialogFragment.SelectDate
import com.example.landmarkapp.ui.Fragment.BaseFragment
import com.example.landmarkapp.viewmodel.ReportViewModel
import java.util.*

class TransferQueryFragment(private val activity: DashboardActivity, private val viewModel: ReportViewModel): BaseFragment() {
    private lateinit var binding: FragmentQueryTransferBinding
    private lateinit var myContext: Context
    lateinit var calendarList: List<Calendar>

    var year = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_query_transfer,container,false)
        initInstance()

        return binding.root
    }

    private fun initInstance(){
        observeData()
        onClickActivate()
    }

    private fun onClickActivate(){
        binding.day.setOnClickListener {
            val fm = activity.supportFragmentManager
            val dialog = SelectDate(activity,viewModel)
            dialog.show(fm,"SelectDate")
        }
        binding.constraintLayout2.setOnClickListener {
            activity.supportFragmentManager.beginTransaction().apply {
                val descFrag = TransferFragment(activity,viewModel,binding.firstday.text.toString(),binding.lastday.text.toString())
                replace(R.id.dbfragment,descFrag)
                addToBackStack(null)
                commit()
            }
        }
    }

    private fun observeData(){
        viewModel.getFirstDate().observe(activity,{
                firstDay -> run{
            binding.firstday.text = firstDay
        }
        })
        viewModel.getLastDate().observe(activity,{
                lastDay -> run{
            binding.lastday.text = lastDay
        }
        })
        viewModel.getCalendarList().observe(activity,{
                calendarLiveData -> run{
            calendarList = calendarLiveData
        }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
    }

}