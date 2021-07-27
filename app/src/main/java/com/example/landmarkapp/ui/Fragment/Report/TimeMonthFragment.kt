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
import com.example.landmarkapp.databinding.FragmentTimeMonthBinding
import com.example.landmarkapp.ui.Activity.Report.DashboardActivity
import java.text.SimpleDateFormat
import java.util.*

class TimeMonthFragment(private val activity: DashboardActivity, private val counter: Int) : Fragment() {
    private lateinit var binding: FragmentTimeMonthBinding
    private lateinit var myContext: Context
    private var selectyear = SimpleDateFormat("yyyy").format(Date()).toInt()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_time_month,container,false)
        binding.appCompatTextView2.setText("< $selectyear >")
        isLeapYear()
        yearPicker()
        return binding.root
    }

    private fun yearPicker() {
        binding.appCompatTextView2.setOnClickListener {
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
        clickmonth(yy)
    }

    private fun clickmonth(ly: Int) {

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
    }
}