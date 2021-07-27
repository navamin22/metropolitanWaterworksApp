package com.example.landmarkapp.ui.DialogFragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.DialogYearBinding
import com.example.landmarkapp.ui.Activity.Report.DashboardActivity
import com.example.landmarkapp.utils.StaticData
import com.example.landmarkapp.viewmodel.ReportViewModel

class YearPicker(private val activity: DashboardActivity, private val viewModel: ReportViewModel): DialogFragment() {
    private lateinit var binding: DialogYearBinding
    private lateinit var myContext: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_year, container, false
        )
        initInstance()

        return binding.root
    }

    private fun initInstance(){
        setYear()
    }

    private fun setYear() {
        binding.numberPicker.maxValue = 2050
        binding.numberPicker.minValue = 1950
        binding.numberPicker.value = 2021
        binding.numberPickerMonth.maxValue = 12
        binding.numberPickerMonth.minValue = 1
        binding.numberPickerMonth.value = 1
        binding.SubmitYear.setOnClickListener {

            StaticData.year_sel = binding.numberPicker.value
            StaticData.month_sel = binding.numberPickerMonth.value
            viewModel.setCalendarFromYear()
//            fragment.setCalendarFromYear(binding.numberPicker.value,selectDateSatisfaction)

            dismiss()
        }
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