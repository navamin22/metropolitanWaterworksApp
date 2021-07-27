package com.example.landmarkapp.ui.DialogFragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.DialogDayBinding
import com.example.landmarkapp.ui.Activity.Report.DashboardActivity
import com.example.landmarkapp.utils.StaticData
import com.example.landmarkapp.utils.snackbar
import com.example.landmarkapp.viewmodel.ReportViewModel
import java.text.SimpleDateFormat
import java.util.*

class SelectDate(private val activity: DashboardActivity, private val viewModel: ReportViewModel): DialogFragment() {
    private lateinit var binding: DialogDayBinding
    private lateinit var myContext: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_day, container, false
        )
        initInstance()

        return binding.root
    }

    private fun initInstance(){
        observeData()
        onClickActivate()
    }

    private fun onClickActivate(){
        binding.selectYear.setOnClickListener {
            val fm = activity.supportFragmentManager
            val yearPicker = YearPicker(activity, viewModel)
            yearPicker.show(fm,"YearPicker")
        }

        binding.submitDate.setOnClickListener {
            checkCalendars()
        }
    }

    private fun checkCalendars(){
        val calendarList = binding.dayView.selectedDates

        //viewModel.setCalendarList(binding.dayView.selectedDates)
        Log.v("Select Date", calendarList.toString())
        Log.v("Select Date", "Calendar size = ${calendarList.size}")

        if (calendarList.isNotEmpty()){
            if (calendarList.size >= 1){
                val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.US)
                val year_start = calendarList[0].get(Calendar.YEAR)
                val month_start = calendarList[0].get(Calendar.MONTH)
                val day_start = calendarList[0].get(Calendar.DAY_OF_MONTH)

                val year_expire = calendarList[calendarList.size - 1].get(Calendar.YEAR)
                val month_expire = calendarList[calendarList.size - 1].get(Calendar.MONTH)
                val day_expire = calendarList[calendarList.size - 1].get(Calendar.DAY_OF_MONTH)

                calendarList[0].set(year_start, month_start, day_start)
                calendarList[calendarList.size - 1].set(
                    year_expire,
                    month_expire,
                    day_expire
                )

                Log.v("Start Date", sdf.format(calendarList[0].time))
                Log.v(
                    "Expire Date",
                    sdf.format(calendarList[calendarList.size - 1].time)
                )

                viewModel.setCoupleDates(day_start.toString(),month_start.toString(),year_start.toString(),day_expire.toString(),month_expire.toString(),year_expire.toString())
                viewModel.setFirstDate(sdf.format(calendarList[0].time))
                viewModel.setLastDate(sdf.format(calendarList[calendarList.size - 1].time))
                viewModel.setCalendarList(calendarList)
                println(sdf.format(calendarList[0].time))

                dismiss()
            } else {
                binding.root.snackbar("Require Start date and Expire date")
            }
        } else {
            binding.root.snackbar("กรุณาใส่วันที่ให้ครบค่ะ")
        }
    }

    private fun observeData(){
        viewModel.getCalendar().observe(activity,{
            calendar -> run{
                binding.dayView.setDate(calendar)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStyle(STYLE_NO_INPUT, android.R.style.Theme)
        dialog?.window?.decorView?.systemUiVisibility
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog?.window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawableResource(R.color.transparent)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)

        return super.onCreateDialog(savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        val width = StaticData.screen_width - (StaticData.screen_width * 10) /100
        val height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog?.window?.setLayout(width, height)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
    }
}