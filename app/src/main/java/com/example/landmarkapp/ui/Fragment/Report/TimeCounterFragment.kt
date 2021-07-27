package com.example.landmarkapp.ui.Fragment.Report

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.FragmentTimeCounterBinding
import com.example.landmarkapp.ui.Activity.Report.DashboardActivity
import com.example.landmarkapp.ui.Fragment.BaseFragment

class TimeCounterFragment(private val activity: DashboardActivity): BaseFragment() {
    private lateinit var binding: FragmentTimeCounterBinding
    private lateinit var myContext: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_time_counter,container,false)
        initInstance()

        return binding.root
    }

    private fun initInstance(){
        onClickActivate()
    }

    private fun onClickActivate(){
        binding.countno1.setOnClickListener {
            callTimeMonthFragment(1)
        }
        binding.countno2.setOnClickListener {
            callTimeMonthFragment(2)
        }
        binding.countno3.setOnClickListener {
            callTimeMonthFragment(3)
        }
        binding.countno4.setOnClickListener {
            callTimeMonthFragment(4)
        }
        binding.countno5.setOnClickListener {
            callTimeMonthFragment(5)
        }
        binding.countno6.setOnClickListener {
            callTimeMonthFragment(6)
        }
        binding.countno7.setOnClickListener {
            callTimeMonthFragment(7)
        }
        binding.countno8.setOnClickListener {
            callTimeMonthFragment(8)
        }
        binding.countno9.setOnClickListener {
            callTimeMonthFragment(9)
        }
        binding.countno10.setOnClickListener {
            callTimeMonthFragment(10)
        }
        binding.countno11.setOnClickListener {
            callTimeMonthFragment(11)
        }
        binding.countno12.setOnClickListener {
            callTimeMonthFragment(12)
        }
    }

    private fun callTimeMonthFragment(month: Int){
        activity.supportFragmentManager.beginTransaction().apply {
            val descFrag = TimeMonthFragment(activity,month)
            replace(R.id.dbfragment,descFrag)
            addToBackStack(null)
            commit()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
    }
}