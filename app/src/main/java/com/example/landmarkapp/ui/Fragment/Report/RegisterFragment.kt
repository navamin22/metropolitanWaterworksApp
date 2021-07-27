package com.example.landmarkapp.ui.Fragment.Report

import android.content.Context
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.FragmentRegisterBinding
import com.example.landmarkapp.ui.Activity.Report.AccountActivity
import com.example.landmarkapp.ui.Fragment.BaseFragment

class RegisterFragment(private val activity: AccountActivity): BaseFragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var myContext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_register, container, false)
        initInstance()

        return binding.root
    }

    private fun initInstance(){
        binding.regisPass.transformationMethod = PasswordTransformationMethod.getInstance()
        binding.regisConfirm.transformationMethod = PasswordTransformationMethod.getInstance()

    }

    private fun onClickActivate(){
        binding.constraintLayout2.setOnClickListener {

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
    }
}