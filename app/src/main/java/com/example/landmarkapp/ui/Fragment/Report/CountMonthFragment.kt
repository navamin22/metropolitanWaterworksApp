package com.example.landmarkapp.ui.Fragment.Report

import android.app.DatePickerDialog
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.FragmentCountMonthBinding
import com.example.landmarkapp.ui.Activity.Report.DashboardActivity
import java.text.SimpleDateFormat
import java.util.*

class CountMonthFragment(private val activity: DashboardActivity) : Fragment()  {
    private lateinit var binding: FragmentCountMonthBinding
    private lateinit var myContext: Context
    private var selectyear = SimpleDateFormat("yyyy").format(Date()).toInt()
    private var specialFrebDay = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_count_month,container,false)
        initInstance()
        return binding.root
    }

    private fun initInstance(){
        binding.appCompatTextView2.text = "< $selectyear >"
        isLeapYear()
        onClickActivate()
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

    private fun onClickActivate() {
        binding.appCompatTextView2.setOnClickListener {
            yearPicker()
        }
        binding.jan.setOnClickListener {
            openCountFragment(1,"มกราคม",31)
        }
        binding.feb.setOnClickListener {
            openCountFragment(2, "กุมภาพันธ์",specialFrebDay)
        }
        binding.mar.setOnClickListener {
            openCountFragment(3,"มีนาคม",31)
        }
        binding.apr.setOnClickListener {
            openCountFragment(4,"เมษายน",30)
        }
        binding.may.setOnClickListener {
            openCountFragment(5,"พฤษภาคม",31)
        }
        binding.jun.setOnClickListener {
            openCountFragment(6,"มิถุนายน",30)
        }
        binding.jul.setOnClickListener {
            openCountFragment(7,"กรกฎาคม",31)
        }
        binding.aug.setOnClickListener {
            openCountFragment(8,"สิงหาคม",30)
        }
        binding.sep.setOnClickListener {
            openCountFragment(9,"กันยายน",30)
        }
        binding.oct.setOnClickListener {
            openCountFragment(10,"ตุลาคม",31)
        }
        binding.nov.setOnClickListener {
            openCountFragment(11,"พฤศจิกายน",30)
        }
        binding.dec.setOnClickListener {
            openCountFragment(12,"ธันวาคม",31)
        }
    }

    private fun openCountFragment(month: Int, monthStr: String, lastDay: Int){
//        activity.supportFragmentManager.beginTransaction().apply {
//            val descFrag = CountFragment(activity,selectyear,month,monthStr,lastDay)
//            replace(R.id.dbfragment,descFrag)
//            addToBackStack(null)
//            commit()
//        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
    }
}