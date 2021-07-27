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
import com.example.landmarkapp.databinding.FragmentJuristicMonthBinding
import com.example.landmarkapp.ui.Activity.Report.DashboardActivity
import com.example.landmarkapp.ui.Fragment.BaseFragment
import java.text.SimpleDateFormat
import java.util.*

class JuristicMonthFragment(private val activity: DashboardActivity): BaseFragment() {
    private lateinit var binding: FragmentJuristicMonthBinding
    private lateinit var myContext: Context
    private var selectyear = SimpleDateFormat("yyyy").format(Date()).toInt()
    private var specialFrebDay = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_juristic_month,container,false)
        initInstance()

        return binding.root
    }

    private fun initInstance(){
        binding.appCompatTextView2.text = "< $selectyear >"
        onClickActivate()
    }

    private fun onClickActivate(){
        binding.appCompatTextView2.setOnClickListener {
            yearPicker()
        }
        binding.jan.setOnClickListener {
            openJuristicFragment(1,31)
        }
        binding.feb.setOnClickListener {
            openJuristicFragment(2,specialFrebDay)
        }
        binding.mar.setOnClickListener {
            openJuristicFragment(3,31)
        }
        binding.apr.setOnClickListener {
            openJuristicFragment(4,30)
        }
        binding.may.setOnClickListener {
            openJuristicFragment(5,31)
        }
        binding.jun.setOnClickListener {
            openJuristicFragment(6,30)
        }
        binding.jul.setOnClickListener {
            openJuristicFragment(7,31)
        }
        binding.aug.setOnClickListener {
            openJuristicFragment(8,31)
        }
        binding.sep.setOnClickListener {
            openJuristicFragment(9,30)
        }
        binding.oct.setOnClickListener {
            openJuristicFragment(10,31)
        }
        binding.nov.setOnClickListener {
            openJuristicFragment(11,30)
        }
        binding.dec.setOnClickListener {
            openJuristicFragment(12,31)
        }
    }

    private fun openJuristicFragment(month: Int, lastDay: Int){
        activity.supportFragmentManager.beginTransaction().apply {
            val descFrag = JuristicFragment(activity,selectyear,month,lastDay)
            replace(R.id.dbfragment,descFrag)
            addToBackStack(null)
            commit()
        }
    }

    private fun yearPicker(){
        val now = Calendar.getInstance()
        val month = now.get(Calendar.MONTH)
        val day = now.get(Calendar.DAY_OF_MONTH)

        val yp = DatePickerDialog(activity, android.R.style.Theme_Holo_Dialog, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            selectyear = year
            binding.appCompatTextView2.setText("< $year >")
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
}