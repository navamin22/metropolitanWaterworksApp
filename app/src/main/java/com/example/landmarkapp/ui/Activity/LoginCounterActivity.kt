package com.example.landmarkapp.ui.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.landmarkapp.Network.Repository
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.ActivityDisplayUpgradeBinding
import com.example.landmarkapp.databinding.ActivityLoginCounterBinding
import com.example.landmarkapp.utils.StaticData
import com.example.landmarkapp.utils.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginCounterActivity: BaseActivity() {
    private lateinit var binding: ActivityLoginCounterBinding
    private val repo = Repository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_counter)
        initInstance()
    }

    private fun initInstance(){
        //isLoggedIn()
        onClickActivate()
    }

    private fun onClickActivate(){
        binding.submitBtn.setOnClickListener {
            when {
                binding.usernameEdt.text.toString().isEmpty() -> {
                    toast("กรุณาระบุชื่อบัญชีด้วยค่ะ")
                }
                binding.passwordEdt.text.toString().isEmpty() -> {
                    toast("กรุณาระบุรหัสผ่านด้วยค่ะ")
                }
                else -> {
                    //val sp: SharedPreferences = getSharedPreferences("SERVICE_QUEUE_SETTING", Context.MODE_PRIVATE)
                    val username = binding.usernameEdt.text.toString()
                    val password = binding.passwordEdt.text.toString()
                    val serviceChannel = StaticData.currentChannelType
                    val serviceCounter = StaticData.currentChannelNo

                    repo.loginEmployee(this, this, username, password, serviceChannel, serviceCounter)
                    //repo.insertAccount("leo", "1234", "กฤตภาส","คงประดิษฐ์","012345678","modernpos@gmail.com","Employee","Normal","Offline")
                    //repo.insertAccount("min", "1234", "นวมินทร์","พิชากูร","012345678","modernpos@gmail.com","Employee","Normal","Offline")
                }
            }
        }
    }

}