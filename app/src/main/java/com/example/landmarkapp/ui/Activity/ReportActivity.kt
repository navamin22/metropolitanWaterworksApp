package com.example.landmarkapp.ui.Activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.ActivityReportBinding

class ReportActivity: BaseActivity() {
    private lateinit var binding: ActivityReportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report)
        initInstance()
    }

    private fun initInstance(){

    }
}