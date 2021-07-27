package com.example.landmarkapp.ui.DialogFragment

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.DialogLoginCounterBinding
import com.example.landmarkapp.ui.Activity.PRActivity2
import com.example.landmarkapp.utils.StaticData
import com.example.landmarkapp.utils.toast
import com.example.landmarkapp.viewmodel.PRViewModel2

class LoginCounter(private val activity: PRActivity2, private val viewModel: PRViewModel2): DialogFragment() {
    private lateinit var myContext: Context
    private lateinit var binding: DialogLoginCounterBinding
    private var doubleBackToExitPressedOnce = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_login_counter, container, false
        )
        initInstance()

        return binding.root
    }

    private fun initInstance(){
        onClickActivate()
    }

    private fun onClickActivate(){
        binding.submitBtn.setOnClickListener {
            when {
                binding.usernameEdt.text.toString().isEmpty() -> {
                    myContext.toast("กรุณาระบุชื่อบัญชี")
                }
                binding.passwordEdt.text.toString().isEmpty() -> {
                    myContext.toast("กรุณาระบุรหัสผ่าน")
                }
                else -> {
                    val username = binding.usernameEdt.text.toString()
                    val password = binding.passwordEdt.text.toString()
                    //viewModel.insertAccount(username, password, "Flookky","Chayanut","0652362352","modernpos@gmail.com","Employee","Normal")
                    //viewModel.loginEmployee(activity, myContext, this, binding.usernameEdt.text.toString(), binding.passwordEdt.text.toString())
                }
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
        val width = StaticData.screen_width - (StaticData.screen_width * 20) /100
        val height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog?.window?.setLayout(width, height)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val d: Dialog = super.onCreateDialog(savedInstanceState)
        d.setCanceledOnTouchOutside(false)
        d.setCancelable(false)
        return d

//        val d: Dialog = super.onCreateDialog(savedInstanceState)
//        d.setCanceledOnTouchOutside(false)
//        d.setCancelable(false)
//        return d
    }

}