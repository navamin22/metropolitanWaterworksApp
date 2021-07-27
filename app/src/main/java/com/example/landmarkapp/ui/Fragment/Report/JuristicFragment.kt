package com.example.landmarkapp.ui.Fragment.Report

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.FragmentJuristicBinding
import com.example.landmarkapp.ui.Activity.Report.DashboardActivity
import com.example.landmarkapp.ui.Fragment.BaseFragment

class JuristicFragment(private val activity: DashboardActivity, private val year: Int,
                       private val month: Int, private val lastday: Int): BaseFragment() {
    private lateinit var binding: FragmentJuristicBinding
    private lateinit var myContext: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_juristic,container,false)

        return binding.root
    }

    private fun initInstance(){
        setAbove()
    }

    private fun setAbove() {
        if (month < 10) {
            binding.firstday.text = "01/"+"0"+month.toString()+"/"+year.toString()
            binding.lastday.text = lastday.toString()+"/"+"0"+month.toString()+"/"+year.toString()
        } else {
            binding.firstday.text = "01/"+month.toString()+"/"+year.toString()
            binding.lastday.text = lastday.toString()+"/"+month.toString()+"/"+year.toString()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
    }
}