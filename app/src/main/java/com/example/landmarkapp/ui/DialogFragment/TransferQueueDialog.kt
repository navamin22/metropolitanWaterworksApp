package com.example.landmarkapp.ui.DialogFragment

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.DialogTransferQueueBinding
import com.example.landmarkapp.ui.Activity.PRActivity2
import com.example.landmarkapp.utils.StaticData
import com.example.landmarkapp.utils.toast
import com.example.landmarkapp.viewmodel.PRViewModel2

class TransferQueueDialog(
    private val activity: PRActivity2,
    private val viewModel: PRViewModel2
): DialogFragment() {
    private lateinit var myContext: Context
    private lateinit var binding: DialogTransferQueueBinding
    var typeList = arrayOf("-","A","B","C","D")
    var counterList = arrayOf("-","1","2","3","4","5","6","7","8","9","10")
    var typeSelected = 0
    var counterSelected = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_transfer_queue, container, false
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
        }

        binding.submitBtn.setOnClickListener {
            when {
                typeSelected == 0 -> {
                    myContext.toast("กรุณาเลือกประเภทบริการก่อนค่ะ")
                }
                counterSelected == 0 -> {
                    myContext.toast("กรุณาเลือกช่องบริการก่อนค่ะ")
                }
                else -> {
                    val counterType = binding.typeSelSpinner.selectedItem.toString()
                    val counterNumber = binding.counterSelSpinner.selectedItem.toString().toInt()
                    viewModel.transferQueueData(counterType,counterNumber)
                    dismiss()
                }
            }
        }
    }

    private fun setSpinner(){
        val typeAdapter = ArrayAdapter(myContext, R.layout.spinner_counter_item, typeList)
        val adapter = ArrayAdapter(myContext, R.layout.spinner_counter_item, counterList)
        binding.typeSelSpinner.adapter = typeAdapter
        binding.counterSelSpinner.adapter = adapter

        binding.typeSelSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                typeSelected = p2
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                typeSelected = 0
            }
        }

        binding.counterSelSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                counterSelected = p2
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                counterSelected = 0
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStyle(STYLE_NO_INPUT, android.R.style.Theme)
        val flags = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.decorView?.systemUiVisibility = flags
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

}