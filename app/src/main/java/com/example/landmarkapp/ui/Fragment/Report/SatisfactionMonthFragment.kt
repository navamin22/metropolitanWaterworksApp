package com.example.landmarkapp.ui.Fragment.Report

import android.app.DatePickerDialog
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.FragmentSatisfactionMonthBinding
import com.example.landmarkapp.ui.Activity.Report.DashboardActivity
import com.example.landmarkapp.ui.Fragment.BaseFragment
import com.example.landmarkapp.viewmodel.DashBoardViewModel
import com.example.landmarkapp.viewmodel.ReportViewModel
import java.text.SimpleDateFormat
import java.util.*

class SatisfactionMonthFragment(private val activity: DashboardActivity, private val viewModel: ReportViewModel): BaseFragment() {
    private lateinit var binding: FragmentSatisfactionMonthBinding
    private lateinit var myContext: Context
    private var specialFrebDay = 0
    private var selectyear = SimpleDateFormat("yyyy").format(Date()).toInt()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_satisfaction_month,container,false)
        initInstance()

        return binding.root
    }

    private fun initInstance(){
        binding.appCompatTextView2.text = "< $selectyear >"
        isLeapYear()
        yearPicker()
        onClickActivate()
    }

    private fun onClickActivate(){
        binding.appCompatTextView2.setOnClickListener {
            yearPicker()
        }
        binding.jan.setOnClickListener {
            openSatisfactionFragment(1,"มกราคม",31)
        }
        binding.feb.setOnClickListener {
            openSatisfactionFragment(2,"กุมภาพันธ์", specialFrebDay)
        }
        binding.mar.setOnClickListener {
            openSatisfactionFragment(3,"มีนาคม",31)
        }
        binding.apr.setOnClickListener {
            openSatisfactionFragment(4,"เมษายน",30)
        }
        binding.may.setOnClickListener {
            openSatisfactionFragment(5,"พฤษภาคม",31)
        }
        binding.jun.setOnClickListener {
            openSatisfactionFragment(6,"มิถุนายม",30)
        }
        binding.jul.setOnClickListener {
            openSatisfactionFragment(7,"กรกฎาคม",31)
        }
        binding.aug.setOnClickListener {
            openSatisfactionFragment(8,"สิงหาคม",30)
        }
        binding.sep.setOnClickListener {
            openSatisfactionFragment(9,"กันยายน",30)
        }
        binding.oct.setOnClickListener {
            openSatisfactionFragment(10,"ตุลาคม",31)
        }
        binding.nov.setOnClickListener {
            openSatisfactionFragment(11,"พฤศจิกายน",30)
        }
        binding.dec.setOnClickListener {
            openSatisfactionFragment(12,"ธันวาคม",31)
        }
    }

    private fun openSatisfactionFragment(month: Int, monthStr: String, lastDay: Int){
//        activity.supportFragmentManager.beginTransaction().apply {
//            val descFrag = SatisfactionFragment(activity,viewModel,selectyear,month,monthStr,lastDay)
//            replace(R.id.dbfragment,descFrag)
//            addToBackStack(null)
//            commit()
//        }
    }

    private fun yearPicker() {
        val now = Calendar.getInstance()
        val month = now.get(Calendar.MONTH)
        val day = now.get(Calendar.DAY_OF_MONTH)

        val yp = DatePickerDialog(activity, android.R.style.Theme_Holo_Dialog, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            selectyear = year
            binding.appCompatTextView2.text = "< $year >"
            isLeapYear()
        }, selectyear, month, day)

        yp.show()

        val vmonth = yp.findViewById<View>(Resources.getSystem().getIdentifier("android:id/month", null, null))
        val vday = yp.findViewById<View>(Resources.getSystem().getIdentifier("android:id/day", null, null))
        if (vmonth != null) {
            vmonth.visibility = View.GONE
        }
        if (vday != null) {
            vday.visibility = View.GONE
        }
    }

    private fun isLeapYear() {
        var yy = 28
        if (selectyear % 400 == 0) {
            yy = 29
        } else if (selectyear % 100 == 0) {
            yy = 28
        } else if (selectyear % 4 == 0) {
            yy = 29
        } else {
            yy = 28
        }
        specialFrebDay = yy
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
    }
}