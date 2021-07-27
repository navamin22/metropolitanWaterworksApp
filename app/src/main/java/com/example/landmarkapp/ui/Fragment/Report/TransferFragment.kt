package com.example.landmarkapp.ui.Fragment.Report

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.FragmentQueryTransferBinding
import com.example.landmarkapp.databinding.FragmentTransferBinding
import com.example.landmarkapp.ui.Activity.Report.DashboardActivity
import com.example.landmarkapp.ui.Fragment.BaseFragment
import com.example.landmarkapp.viewmodel.ReportViewModel

class TransferFragment(private val activity: DashboardActivity, private val viewModel: ReportViewModel, private val firstDay: String, private val lastDay: String): BaseFragment() {
    private lateinit var binding: FragmentTransferBinding
    private lateinit var myContext: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_transfer, container, false)
        initInstance()

        return binding.root
    }

    private fun initInstance(){
        setData()
        onClickActivate()
    }

    private fun onClickActivate(){

    }

    private fun setData(){
        binding.firstday.text = firstDay
        binding.lastday.text = lastDay
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
    }

}