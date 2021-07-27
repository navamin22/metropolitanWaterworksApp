package com.example.landmarkapp.ui.DialogFragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.landmarkapp.Network.Repository
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.DialogReasonRateLowBinding
import com.example.landmarkapp.databinding.FragmentRateBinding
import com.example.landmarkapp.ui.Activity.RateActivity
import com.example.landmarkapp.ui.Fragment.BaseFragment
import com.example.landmarkapp.utils.StaticData
import kotlinx.android.synthetic.main.dialog_thanks.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class ReasonRateLowCounter(private val activity: RateActivity): DialogFragment() {
    private lateinit var myContext: Context
    private lateinit var binding: DialogReasonRateLowBinding
    private val repo = Repository()
    var reasonSelected = 0
    var rateScore = 0
    var rateStatus = ""
    var rateTitle = ""
    var active = ""
    var telNumber = ""
    var reasonRateLow = ""
    var serviceCounter = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.dialog_reason_rate_low,container,false)
        initInstance()

        return binding.root
    }

    private fun initInstance(){
        onClickActivate()
    }

    private fun onClickActivate(){
        binding.submitBtn.setOnClickListener {
            if (reasonSelected == 0){
                val dialog = LayoutInflater.from(myContext).inflate(R.layout.dialog_alert, null)
                val builder = AlertDialog.Builder(myContext).setView(dialog)
                val alertDialog = builder.show()
                dialog.optional_txt.text = "กรุณาเลือกเหตุผลค่ะ"
                dialog.back_btn.setOnClickListener {
                    alertDialog.dismiss()
                }
            } else {
                telNumber = binding.telEdt.text.toString()
                reasonRateLow = binding.reasonSpinner.selectedItem.toString()

                val dialog = LayoutInflater.from(myContext).inflate(R.layout.dialog_thanks, null)
                val builder = AlertDialog.Builder(myContext).setView(dialog)
                val alertDialog = builder.show()
                dialog.back_btn.setOnClickListener {
                    alertDialog.dismiss()
                    dismiss()
                }
                GlobalScope.launch {
                    repo.insertRate(rateScore, rateTitle, active, rateStatus, telNumber, reasonRateLow, serviceCounter,0)
                    delay(3000)
                    alertDialog.dismiss()
                    dismiss()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf {
            it.containsKey("rateScore")
            it.containsKey("rateTitle")
            it.containsKey("active")
            it.containsKey("rateStatus")
            it.containsKey("serviceCounter")
        }?.apply {
            rateScore = getInt("rateScore")
            rateTitle = getString("rateTitle").toString()
            active = getString("active").toString()
            rateStatus = getString("rateStatus").toString()
            serviceCounter = getInt("serviceCounter")

            println("RateScore is $rateScore")
            println("RateTitle is $rateTitle")
            println("Active is $active")
            println("RateStatus is $rateStatus")
            println("ServiceCounter is $serviceCounter")
        }

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

    override fun onDestroy() {
        super.onDestroy()
//        activity.timing()
    }
}