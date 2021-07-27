package com.example.landmarkapp.ui.Fragment.Report

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.FragmentReportBinding
import com.example.landmarkapp.ui.Activity.Report.DashboardActivity
import com.example.landmarkapp.ui.Fragment.BaseFragment
import com.example.landmarkapp.viewmodel.DashBoardViewModel
import com.example.landmarkapp.viewmodel.ReportViewModel

class ReportFragment(private val activity: DashboardActivity, private val viewModel: ReportViewModel): BaseFragment() {
    private lateinit var binding: FragmentReportBinding
    private lateinit var myContext: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_report,container,false)
        initInstance()
        return binding.root
    }

    private fun initInstance(){
        onClickActivate()
    }

    private fun onClickActivate(){
        binding.userTime.setOnClickListener {
            activity.supportFragmentManager.beginTransaction().apply {
                val descFrag = TimeQueryFragment(activity, viewModel)
                replace(R.id.dbfragment,descFrag)
                addToBackStack(null)
                commit()
            }
        }
        binding.userSatisfaction.setOnClickListener {
            activity.supportFragmentManager.beginTransaction().apply {
                val descFrag = SatisfactionQueryFragment(activity, viewModel)
                replace(R.id.dbfragment,descFrag)
                addToBackStack(null)
                commit()
            }
        }
        binding.userCount.setOnClickListener {
            activity.supportFragmentManager.beginTransaction().apply {
                val descFrag = CountQueryFragment(activity, viewModel)
                replace(R.id.dbfragment,descFrag)
                addToBackStack(null)
                commit()
            }
        }
        binding.userLengthTime.setOnClickListener {
            activity.supportFragmentManager.beginTransaction().apply {
                val descFrag = LengthTimeQueryFragment(activity, viewModel)
                replace(R.id.dbfragment,descFrag)
                addToBackStack(null)
                commit()
            }
        }
        binding.queTransfer.setOnClickListener {
            activity.supportFragmentManager.beginTransaction().apply {
                val descFrag = TransferQueryFragment(activity, viewModel)
                replace(R.id.dbfragment,descFrag)
                addToBackStack(null)
                commit()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
    }
}