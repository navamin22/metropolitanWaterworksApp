package com.example.landmarkapp.ui.DialogFragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.DialogQrcodeBinding
import com.example.landmarkapp.ui.Activity.QueueUserActivity
import com.example.landmarkapp.utils.StaticData
import com.example.landmarkapp.utils.TPS780_Utils.Device.Connect
import com.example.landmarkapp.viewmodel.QueueUserViewModel
import kotlinx.android.synthetic.main.dialog_thanks.view.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QrcodeDialog(private val activity: QueueUserActivity, private val viewModel: QueueUserViewModel) : DialogFragment() {
    private lateinit var myContext: Context
    private lateinit var binding: DialogQrcodeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.dialog_qrcode,container,false)

        initInstance()

        return binding.root
    }

    private fun initInstance(){
        onClickActivate()
    }

    private fun onClickActivate(){
        binding.dialogClose.setOnClickListener {
            dismiss()
        }
        binding.submitPayment.setOnClickListener {
            if(Connect().mPrinter.isConnect){
                activity.cheakStatesPrinter()
                viewModel.getPaymentSlip(activity)
            }

            val dialog = LayoutInflater.from(myContext).inflate(R.layout.dialog_thanks, null)
            val builder = AlertDialog.Builder(myContext).setView(dialog)
            val alertDialog = builder.show()
            dialog.optional_txt.visibility = View.VISIBLE
            dialog.optional_txt.text = "กรุณารับใบกำกับภาษีที่ช่อง 1-3 ค่ะ"

            dialog.back_btn.setOnClickListener {
                alertDialog.dismiss()
                dismiss()
            }
            activity.launch {
                delay(3000)
                alertDialog.dismiss()
                dismiss()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStyle(STYLE_NO_INPUT, android.R.style.Theme)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.decorView?.systemUiVisibility
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog?.window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
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
        activity.timing()
    }
}