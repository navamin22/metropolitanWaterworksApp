package com.example.landmarkapp.ui.Fragment.Report

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.FragmentQueryCountBinding
import com.example.landmarkapp.ui.Activity.Report.DashboardActivity
import com.example.landmarkapp.ui.DialogFragment.SelectDate
import com.example.landmarkapp.utils.StaticData
import com.example.landmarkapp.utils.snackbar
import com.example.landmarkapp.utils.toast
import com.example.landmarkapp.viewmodel.ReportViewModel
import java.util.*

class CountQueryFragment(private val activity: DashboardActivity, private val viewModel: ReportViewModel) : Fragment() {
    private lateinit var binding: FragmentQueryCountBinding
    private lateinit var myContext: Context
    private lateinit var arrAdapter: ArrayAdapter<String>
    private lateinit var arrAdapter2: ArrayAdapter<String>
    private lateinit var arrAdapter3: ArrayAdapter<String>
    lateinit var calendarList: List<Calendar>
    var year = 0
    var showEachTime = false
    var selectAnother = false
    var typeSelected = ""
    var serviceChannel = ""
    var serviceCounter = "0"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_query_count,container,false)
        initInstance()

        return binding.root
    }

    private fun initInstance(){
        observeData()
        setSpinner()
        onClickActivate()
    }

    private fun onClickActivate(){
        binding.day.setOnClickListener {
            val fm = activity.supportFragmentManager
            val dialog = SelectDate(activity,viewModel)
            dialog.show(fm,"SelectDate")
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
        val selectedId = binding.radioGroup.checkedRadioButtonId
        val firstDay = binding.firstday.text.toString()
        val lastDay = binding.lastday.text.toString()
        if (firstDay.isEmpty() || lastDay.isEmpty()){
            binding.root.snackbar("กรุณาใส่วันที่ให้ครบค่ะ")
        } else {
            if (selectedId == -1) {
                myContext.toast("กรุณาเลือกตัวเลือกช่วงเวลาด้วยค่ะ")
            } else {
                showEachTime = binding.lengthTimeRb.isChecked

                if (selectAnother){
                    when (binding.spinnerCategory.selectedItemPosition) {
                        0 -> {
                            typeSelected = binding.spinnerCategory.selectedItem.toString()
                        }
                        1 -> {
                            serviceChannel = binding.spinnerType.selectedItem.toString()
                            typeSelected = binding.spinnerCategory.selectedItem.toString()
                        }
                        2 -> {
                            serviceCounter = binding.spinnerType.selectedItem.toString()
                            typeSelected = binding.spinnerCategory.selectedItem.toString()
                        }
                    }
                }

                activity.supportFragmentManager.beginTransaction().apply {
                    val descFrag = CountFragment(activity,viewModel,binding.firstday.text.toString(),
                        binding.lastday.text.toString(),binding.spinnerCategory.selectedItem.toString(),
                        serviceChannel, serviceCounter, showEachTime)
                    replace(R.id.dbfragment,descFrag)
                    addToBackStack(null)
                    commit()
                }
            }
        }
    }

    private fun setSpinner(){
        arrAdapter = ArrayAdapter(
            activity,R.layout.style_spinner, StaticData.counterChoiceList
        )
        binding.spinnerCategory.adapter = arrAdapter

        binding.spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when {
                    binding.spinnerCategory.selectedItem.toString() == "เลือกทั้งหมด" -> {
                        arrAdapter = ArrayAdapter(
                            activity, R.layout.style_spinner
                        )
                        binding.spinnerType.visibility = View.GONE
                        binding.spinnerType.adapter = arrAdapter
                        selectAnother = false
                        serviceChannel = ""
                        serviceCounter = "0"
                    }
                    binding.spinnerCategory.selectedItem.toString() == "การให้บริการ" -> {
                        arrAdapter2 = ArrayAdapter(
                            activity, R.layout.style_spinner, StaticData.typeOfList
                        )
                        binding.spinnerType.visibility = View.VISIBLE
                        binding.spinnerType.adapter = arrAdapter2
                        selectAnother = true
                    }
                    binding.spinnerCategory.selectedItem.toString() == "ช่องบริการ" -> {
                        arrAdapter3 = ArrayAdapter(
                            activity, R.layout.style_spinner, StaticData.counterOfList
                        )
                        binding.spinnerType.visibility = View.VISIBLE
                        binding.spinnerType.adapter = arrAdapter3
                        selectAnother = true
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