package com.example.landmarkapp.ui.Activity.Report

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.landmarkapp.Network.Services.RateCheckService
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.ActivityDashboardBinding
import com.example.landmarkapp.model.Retrofit.Interface.RateService
import com.example.landmarkapp.model.Retrofit.response.RateResponse
import com.example.landmarkapp.model.Retrofit.response.ReportResponse
import com.example.landmarkapp.ui.Activity.BaseActivity
import com.example.landmarkapp.ui.DialogFragment.LogoutDialog
import com.example.landmarkapp.ui.Fragment.Report.HomeFragment
import com.example.landmarkapp.ui.Fragment.Report.ReportFragment
import com.example.landmarkapp.utils.StaticData
import com.example.landmarkapp.utils.toast
import com.example.landmarkapp.viewmodel.DashBoardViewModel
import com.example.landmarkapp.viewmodel.ReportViewModel

class DashboardActivity: BaseActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private var doubleBackExit = false
    private val dashBoardViewModel by lazy {
        ViewModelProviders.of(this).get(DashBoardViewModel::class.java)
    }
    private val reportViewModel by lazy {
        ViewModelProviders.of(this).get(ReportViewModel::class.java)
    }
    companion object {
        var reportList = mutableListOf<ReportResponse>()
        var rateList = mutableListOf<RateResponse>()
    }

    override fun onCreate(savedInstantState: Bundle?) {
        super.onCreate(savedInstantState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        initInstance()
    }

    private fun initInstance(){
        setViewModel()
        observeData()
        onClickActivate()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.dbfragment, HomeFragment(this@DashboardActivity, dashBoardViewModel))
            commit()
        }
    }

    private fun onClickActivate(){
        binding.homeBtn.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.dbfragment, HomeFragment(this@DashboardActivity, dashBoardViewModel))
                addToBackStack(null)
                commit()
            }
        }
        binding.reportBtn.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.dbfragment, ReportFragment(this@DashboardActivity, reportViewModel))
                addToBackStack(null)
                commit()
            }
        }
        binding.logoutBtn.setOnClickListener {
            val fm = supportFragmentManager
            val myFragment = LogoutDialog(this)
            myFragment.show(fm, "Logout Fragment")
        }
    }

    private fun setViewModel(){
        binding.lifecycleOwner = this
    }

    private fun observeData(){
        dashBoardViewModel.reportHomeLiveData.observe(this, {
            dashBoardViewModel.setReportFromDateList(it)
            setReportData(it)
        })
        dashBoardViewModel.rateHomeLiveData.observe(this, {
            dashBoardViewModel.setRateFromDateList(it)
            setRateData(it)
        })
        reportViewModel.accountLiveData.observe(this,{
            reportViewModel.setAccountList(it)
            reportViewModel.setAccountSpinnerData()
        })
    }

    private fun setRateData(data: MutableList<RateResponse>){
        rateList = data
    }

    private fun setReportData(data: MutableList<ReportResponse>){
        reportList = data
    }

    override fun onBackPressed() {
        if (doubleBackExit){
            super.onBackPressed()
            return
        }
        this.doubleBackExit = true
        toast("กด Back อีกครั้งเพื่อออกจากเเอพ")

        Handler().postDelayed({ doubleBackExit = false }, 2000)
    }

    override fun onStart() {
        super.onStart()
        startService(Intent(this, RateCheckService::class.java))
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        StaticData.screen_width = binding.root.measuredWidth
        StaticData.screen_height = binding.root.measuredHeight
        println("Width of screen = ${StaticData.screen_width}")
        println("Height of screen = ${StaticData.screen_height}")
    }

}