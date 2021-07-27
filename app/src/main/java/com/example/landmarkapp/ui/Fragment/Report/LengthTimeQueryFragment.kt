package com.example.landmarkapp.ui.Fragment.Report

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.FragmentQueryLengthTimeBinding
import com.example.landmarkapp.ui.Activity.Report.DashboardActivity
import com.example.landmarkapp.ui.DialogFragment.SelectDate
import com.example.landmarkapp.ui.Fragment.BaseFragment
import com.example.landmarkapp.utils.StaticData
import com.example.landmarkapp.viewmodel.ReportViewModel
import java.util.*

class LengthTimeQueryFragment(private val activity: DashboardActivity, private val viewModel: ReportViewModel): BaseFragment() {
    private lateinit var binding: FragmentQueryLengthTimeBinding
    private lateinit var myContext: Context
    private lateinit var arrAdapter: ArrayAdapter<String>
    private lateinit var arrAdapter2: ArrayAdapter<String>
    private lateinit var arrAdapter3: ArrayAdapter<String>
    private lateinit var arrAdapter4: ArrayAdapter<String>
    lateinit var calendarList: List<Calendar>

    var year = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_query_length_time,container,false)
        initInstance()

        return binding.root
    }

    private fun initInstance(){
        observeData()
        setSpinner()
        onClickActivate()
    }

    private fun onClickActivate(){
        binding.constraintLayout2.setOnClickListener {

        }
        binding.day.setOnClickListener {
            val fm = activity.supportFragmentManager
            val dialog = SelectDate(activity,viewModel)
            dialog.show(fm,"SelectDate")
        }
    }

    private fun observeData(){
        viewModel.getFirstDate().observe(activity,{
                firstDay -> run{
            binding.firstday.text = firstDay
        }
        })
        viewModel.getLastDate().observe(activity,{
                lastDay -> run{
            binding.lastday.text = lastDay
        }
        })
        viewModel.getCalendarList().observe(activity,{
                calendarLiveData -> run{
            calendarList = calendarLiveData
        }
        })
    }

    private fun setSpinner(){
        arrAdapter = ArrayAdapter(
            activity,R.layout.style_spinner, StaticData.reportTypeOf
        )
        binding.spinnerCategory.adapter = arrAdapter
        binding.spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                println("Selected is = ${binding.spinnerCategory.selectedItem}")
                when {
                    binding.spinnerCategory.selectedItem.toString() == "เลือกทั้งหมด" -> {
                        arrAdapter = ArrayAdapter(
                            activity, R.layout.style_spinner
                        )
                        binding.spinnerType.visibility = View.GONE
                        binding.spinnerType.adapter = arrAdapter
                    }
                    binding.spinnerCategory.selectedItem.toString() == "การให้บริการ" -> {
                        arrAdapter2 = ArrayAdapter(
                            activity, R.layout.style_spinner, StaticData.typeService
                        )
                        binding.spinnerType.visibility = View.VISIBLE
                        binding.spinnerType.adapter = arrAdapter2
                    }
                    binding.spinnerCategory.selectedItem.toString() == "ช่องบริการ" -> {
                        arrAdapter3 = ArrayAdapter(
                            activity,R.layout.style_spinner, StaticData.counterOfList
                        )
                        binding.spinnerType.visibility = View.VISIBLE
                        binding.spinnerType.adapter = arrAdapter3
                    }
                    binding.spinnerCategory.selectedItem.toString() == "พนักงาน" -> {
                        arrAdapter4 = ArrayAdapter(
                            activity,R.layout.style_spinner, viewModel.spinnerAccount
                        )
                        binding.spinnerType.visibility = View.VISIBLE
                        binding.spinnerType.adapter = arrAdapter4
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
    }

}