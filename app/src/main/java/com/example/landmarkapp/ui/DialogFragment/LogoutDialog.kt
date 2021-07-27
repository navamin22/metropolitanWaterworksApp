package com.example.landmarkapp.ui.DialogFragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.DialogLogoutBinding
import com.example.landmarkapp.ui.Activity.Report.AccountActivity
import com.example.landmarkapp.ui.Activity.Report.DashboardActivity
import com.example.landmarkapp.utils.StaticData.Companion.screen_width

class LogoutDialog(private val activity: DashboardActivity): DialogFragment() {
    private lateinit var myContext: Context
    private lateinit var binding: DialogLogoutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_logout,container,false)
        initInstance()
        return binding.root
    }

    private fun initInstance(){
        onClickActivate()
    }

    private fun onClickActivate(){
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
        binding.noBtn.setOnClickListener {
            dismiss()
        }
        binding.yesBtn.setOnClickListener {
            val intent = Intent(activity, AccountActivity::class.java)
            startActivity(intent)
            activity.finish()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStyle(STYLE_NO_INPUT, android.R.style.Theme)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.decorView?.systemUiVisibility
//        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog?.window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawableResource(R.color.transparent)
    }

    override fun onStart() {
        super.onStart()
        val width = screen_width - (screen_width * 10) /100
        val height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog?.window?.setLayout(width, height)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
    }
}