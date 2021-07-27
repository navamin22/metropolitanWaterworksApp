package com.example.landmarkapp.ui.DialogFragment

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.DialogMoreBillsQueueBinding
import com.example.landmarkapp.ui.Activity.QueueUserActivity
import com.example.landmarkapp.utils.StaticData
import com.example.landmarkapp.viewmodel.QueueUserViewModel

class MoreBillsQueue(private val activity: QueueUserActivity,
                     private val viewModel: QueueUserViewModel
): DialogFragment() {
    private lateinit var myContext: Context
    private lateinit var binding: DialogMoreBillsQueueBinding
    var counterList = arrayOf("2","3","4","5","6","7","8","9","10",
        "11","12","13","14","15","16","17","18","19","20")
    var billSelected = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_more_bills_queue, container, false
        )
        initInstance()

        return binding.root
    }

    private fun initInstance(){
        onClickActivate()
        setSpinner()
    }

    private fun onClickActivate(){
        binding.cancelBtn.setOnClickListener {
            dismiss()
            activity.timing()
        }

        binding.submitBtn.setOnClickListener {
            viewModel.getMoreBillsSlip(activity,binding.billCountSpinner.selectedItem.toString().toInt())
            dismiss()
            activity.timing()
        }
    }

    private fun setSpinner(){
        val adapter = ArrayAdapter(myContext, R.layout.spinner_counter_item, counterList)
        binding.billCountSpinner.adapter = adapter

        binding.billCountSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                billSelected = p2
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                billSelected = 0
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStyle(STYLE_NO_INPUT, android.R.style.Theme)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.decorView?.systemUiVisibility
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawableResource(R.color.transparent)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
    }

    override fun onStart() {
        super.onStart()
        val width = StaticData.screen_width - (StaticData.screen_width * 10) /100
        val height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog?.window?.setLayout(width, height)
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        activity.timing()
    }
}