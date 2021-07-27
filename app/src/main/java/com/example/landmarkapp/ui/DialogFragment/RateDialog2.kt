package com.example.landmarkapp.ui.DialogFragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.DialogRate2Binding
import com.example.landmarkapp.ui.Activity.QueueUserActivity
import com.example.landmarkapp.utils.StaticData
import com.example.landmarkapp.utils.toast
import com.example.landmarkapp.viewmodel.QueueUserViewModel
import kotlinx.android.synthetic.main.dialog_thanks.view.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RateDialog2(
    private val activity: QueueUserActivity,
    private val viewModel: QueueUserViewModel
): DialogFragment()  {
    private lateinit var myContext: Context
    private lateinit var binding: DialogRate2Binding
    var counterList = arrayOf("โปรดระบุช่องบริการ","ช่องที่ 1", "ช่องที่ 2", "ช่องที่ 3", "ช่องที่ 4", "ช่องที่ 5", "ช่องที่ 6")
    var counterSelected = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_rate2, container, false
        )
        initInstance()

        return binding.root
    }

    private fun initInstance(){
        onClickActivate()
        setSpinner()
    }

    private fun setSpinner(){
        val adapter = ArrayAdapter(myContext, R.layout.spinner_counter_item, counterList)
        binding.counterSpinner.adapter = adapter

        binding.counterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                counterSelected = p2
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                counterSelected = 0
            }
        }
    }

    private fun onClickActivate(){
        binding.imgVerypoor.setOnClickListener {
            if (counterSelected != 0){
                println("Poor")
                val fm = activity.supportFragmentManager
                val dialog_reason = ReasonRateLow()
                val bundle = Bundle()
                bundle.putInt("rateScore",1)
                bundle.putString("rateTitle","Poor")
                bundle.putString("active","Enable")
                bundle.putString("rateStatus","Below")
                bundle.putInt("serviceCounter", counterSelected)
                dialog_reason.arguments = bundle
                dialog_reason.show(fm,"reason")
                dismiss()
            } else {
                val dialog = LayoutInflater.from(myContext).inflate(R.layout.dialog_alert, null)
                val builder = AlertDialog.Builder(myContext).setView(dialog)
                val alertDialog = builder.show()
                dialog.optional_txt.text = "กรุณาเลือกช่องบริการที่ต้องการประเมินค่ะ"
                dialog.back_btn.setOnClickListener {
                    alertDialog.dismiss()
                }
            }
        }

        binding.imgPoor.setOnClickListener {
            if (counterSelected != 0){
                println("Fair")
                val fm = activity.supportFragmentManager
                val dialog_reason = ReasonRateLow()
                val bundle = Bundle()
                bundle.putInt("rateScore",2)
                bundle.putString("rateTitle","Fair")
                bundle.putString("active","Enable")
                bundle.putString("rateStatus","Below")
                bundle.putInt("serviceCounter", counterSelected)
                dialog_reason.arguments = bundle
                dialog_reason.show(fm,"reason")
                dismiss()
            } else {
                val dialog = LayoutInflater.from(myContext).inflate(R.layout.dialog_alert, null)
                val builder = AlertDialog.Builder(myContext).setView(dialog)
                val alertDialog = builder.show()
                dialog.optional_txt.text = "กรุณาเลือกช่องบริการที่ต้องการประเมินค่ะ"
                dialog.back_btn.setOnClickListener {
                    alertDialog.dismiss()
                }
            }
        }

        binding.imgNormal.setOnClickListener {
            if (counterSelected != 0){
                println("Good")
                val dialog = LayoutInflater.from(myContext).inflate(R.layout.dialog_thanks, null)
                val builder = AlertDialog.Builder(myContext).setView(dialog)
                val alertDialog = builder.show()
                dialog.back_btn.setOnClickListener {
                    alertDialog.dismiss()
                    dismiss()
                }
                activity.launch {
                    viewModel.insertRate(3,"Good","Enable","Normal","","",counterSelected)
                    delay(3000)
                    alertDialog.dismiss()
                    dismiss()
                }
            } else {
                val dialog = LayoutInflater.from(myContext).inflate(R.layout.dialog_alert, null)
                val builder = AlertDialog.Builder(myContext).setView(dialog)
                val alertDialog = builder.show()
                dialog.optional_txt.text = "กรุณาเลือกช่องบริการที่ต้องการประเมินค่ะ"
                dialog.back_btn.setOnClickListener {
                    alertDialog.dismiss()
                }
            }
        }

        binding.imgGreat.setOnClickListener {
            if (counterSelected != 0){
                println("Very Good")
                val dialog = LayoutInflater.from(myContext).inflate(R.layout.dialog_thanks, null)
                val builder = AlertDialog.Builder(myContext).setView(dialog)
                val alertDialog = builder.show()
                dialog.back_btn.setOnClickListener {
                    alertDialog.dismiss()
                    dismiss()
                }
                activity.launch {
                    viewModel.insertRate(4,"Very Good","Enable","Normal","","",counterSelected)
                    delay(3000)
                    alertDialog.dismiss()
                    dismiss()
                }
            } else {
                val dialog = LayoutInflater.from(myContext).inflate(R.layout.dialog_alert, null)
                val builder = AlertDialog.Builder(myContext).setView(dialog)
                val alertDialog = builder.show()
                dialog.optional_txt.text = "กรุณาเลือกช่องบริการที่ต้องการประเมินค่ะ"
                dialog.back_btn.setOnClickListener {
                    alertDialog.dismiss()
                }
            }
        }

        binding.imgExcellent.setOnClickListener {
            if (counterSelected != 0){
                println("Excellent")
                val dialog = LayoutInflater.from(myContext).inflate(R.layout.dialog_thanks, null)
                val builder = AlertDialog.Builder(myContext).setView(dialog)
                val alertDialog = builder.show()
                dialog.back_btn.setOnClickListener {
                    alertDialog.dismiss()
                    dismiss()
                }

                activity.launch {
                    viewModel.insertRate(5,"Excellent","Enable","Normal","","",counterSelected)
                    delay(3000)
                    alertDialog.dismiss()
                    dismiss()
                }
            } else {
                val dialog = LayoutInflater.from(myContext).inflate(R.layout.dialog_alert, null)
                val builder = AlertDialog.Builder(myContext).setView(dialog)
                val alertDialog = builder.show()
                dialog.optional_txt.text = "กรุณาเลือกช่องบริการที่ต้องการประเมินค่ะ"
                dialog.back_btn.setOnClickListener {
                    alertDialog.dismiss()
                }
            }
        }

        binding.dialogClose.setOnClickListener {
            dismiss()
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
