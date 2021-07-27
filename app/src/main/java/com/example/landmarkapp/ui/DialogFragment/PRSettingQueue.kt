package com.example.landmarkapp.ui.DialogFragment

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.landmarkapp.Network.Repository
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.DialogPrSettingQueueBinding
import com.example.landmarkapp.databinding.DialogRate2Binding
import com.example.landmarkapp.ui.Activity.PRActivity2
import com.example.landmarkapp.utils.StaticData
import com.example.landmarkapp.utils.toast

class PRSettingQueue(private val activity: PRActivity2): DialogFragment() {
    private lateinit var myContext: Context
    private lateinit var binding: DialogPrSettingQueueBinding
    private val repo = Repository()
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
            R.layout.dialog_pr_setting_queue, container, false
        )
        initInstance()

        return binding.root
    }

    private fun initInstance(){
        setServiceQueue()
        onClickActivate()
        setSpinner()
    }

    private fun setServiceQueue(){
        if (StaticData.currentChannelType.isEmpty()){
            binding.previousCounterType.text = "-"
        } else {
            binding.previousCounterType.text = StaticData.currentChannelType
        }
        if (StaticData.currentChannelNo == 0){
            binding.previousChannel.text = "-"
        } else {
            binding.previousChannel.text = StaticData.currentChannelNo.toString()
        }
    }

    private fun onClickActivate(){
        binding.cancelBtn.setOnClickListener {
            dismiss()
        }

        binding.logoutBtn.setOnClickListener {
            repo.logoutEmployee(activity,myContext, StaticData.accountId, "Offline", StaticData.currentChannelType, StaticData.currentChannelNo)
        }

        binding.submitBtn.setOnClickListener {
            val sp: SharedPreferences = myContext.getSharedPreferences("SERVICE_QUEUE_SETTING", Context.MODE_PRIVATE)
            when {
                typeSelected == 0 -> {
                    myContext.toast("กรุณาเลือกประเภทบริการก่อนค่ะ")
                }
                counterSelected == 0 -> {
                    myContext.toast("กรุณาเลือกช่องบริการก่อนค่ะ")
                }
                else -> {
                    val serviceChannel = binding.typeSelSpinner.selectedItem.toString()
                    val counterNumber = binding.counterSelSpinner.selectedItem.toString().toInt()
                    val editor = sp.edit()
                    editor.putString("serviceChannel",serviceChannel)
                    editor.putInt("serviceCounter",counterNumber)

                    StaticData.currentChannelType = serviceChannel
                    StaticData.currentChannelNo = counterNumber

                    editor.apply()
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
}