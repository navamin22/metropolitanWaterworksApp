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
import com.example.landmarkapp.databinding.FragmentQuerySatisfactionBinding
import com.example.landmarkapp.ui.Activity.Report.DashboardActivity
import com.example.landmarkapp.ui.DialogFragment.SelectDate
import com.example.landmarkapp.ui.Fragment.BaseFragment
import com.example.landmarkapp.utils.StaticData
import com.example.landmarkapp.utils.snackbar
import com.example.landmarkapp.viewmodel.ReportViewModel
import java.util.*

class SatisfactionQueryFragment(private val activity: DashboardActivity, private val viewModel: ReportViewModel) : BaseFragment() {
    lateinit var binding: FragmentQuerySatisfactionBinding
    private lateinit var arrAdapter: ArrayAdapter<String>
    private lateinit var arrAdapter2: ArrayAdapter<String>
    private lateinit var myContext: Context
    lateinit var calendarList: List<Calendar>
    var year = 0
    var selectEmp = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_query_satisfaction,container,false)
        initInstance()

        return binding.root
    }

    private fun initInstance(){
        setSpinner()
        observeData()
        onClickActivate()
    }

    private fun onClickActivate(){
        binding.day.setOnClickListener {
            val fm = activity.supportFragmentManager
            val dialogSelectDate = SelectDate(activity,viewModel)
            dialogSelectDate.show(fm,"SelectDate")
        }
        binding.constraintLayout2.setOnClickListener {
            checkData()
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

    private fun checkData(){
        val firstDay = binding.firstday.text.toString()
        val lastDay = binding.lastday.text.toString()
        if (firstDay.isEmpty() || lastDay.isEmpty()){
            binding.root.snackbar("กรุณาใส่วันที่ให้ครบค่ะ")
        } else {
            var id = 0
            if(selectEmp){
                if (binding.spinnerType.selectedItem.toString().isNotEmpty()){
                    id = viewModel.accountLiveData.value?.get(binding.spinnerType.selectedItemPosition)!!.accountId
                }
            }

            println("ratedId = $id")

            activity.supportFragmentManager.beginTransaction().apply {
                val descFrag = SatisfactionFragment(activity,viewModel,binding.firstday.text.toString(),binding.lastday.text.toString(),id)
                replace(R.id.dbfragment,descFrag)
                addToBackStack(null)
                commit()
            }
        }
    }

    private fun setSpinner(){
        arrAdapter = ArrayAdapter(
            activity,R.layout.style_spinner, StaticData.rateOfList
        )
        binding.spinnerCategory.adapter = arrAdapter
        binding.spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (binding.spinnerCategory.selectedItem.toString() == "เลือกทั้งหมด") {
                    arrAdapter = ArrayAdapter(
                        activity, R.layout.style_spinner
                    )
                    binding.spinnerType.visibility = View.GONE
                    binding.spinnerType.adapter = arrAdapter
                    selectEmp = false
                } else if (binding.spinnerCategory.selectedItem.toString() == "พนักงาน") {
                    arrAdapter2 = ArrayAdapter(
                        activity,R.layout.style_spinner, viewModel.spinnerAccount
                    )
                    binding.spinnerType.visibility = View.VISIBLE
                    binding.spinnerType.adapter = arrAdapter2
                    selectEmp = true
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
    }

}