package com.example.landmarkapp.ui.Fragment.Report

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.FragmentLoginBinding
import com.example.landmarkapp.ui.Activity.Report.AccountActivity
import com.example.landmarkapp.ui.Activity.Report.DashboardActivity
import com.example.landmarkapp.ui.Fragment.BaseFragment
import java.text.SimpleDateFormat
import java.util.*

class LoginFragment(private val activity: AccountActivity): BaseFragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var myContext: Context
    private var showhide: Boolean = true


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container, false)
        initInstance()

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
    }

    private fun initInstance() {
        binding.loginPassword.transformationMethod = PasswordTransformationMethod.getInstance()
        onClickActivate()

    }

    private fun onClickActivate(){
        binding.showpass.setOnClickListener {
            showHidePass()
        }
        binding.loginButton.setOnClickListener {
            login()
//            addDataAccount("hello","world","0644195145","testuser","testpass")
        }
        binding.loginRegis.setOnClickListener {
            activity.supportFragmentManager.beginTransaction().apply {
                replace(R.id.flfragment, RegisterFragment(activity))
                addToBackStack(null)
                commit()
            }
        }
    }

//    fun addDataAccount(name: String, surname: String, tel: String, user: String, pass: String) {
//        val key = db.collection("Member").document().id
//        val create_date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
//        val account = Member(key,name,surname,tel,user,pass,"admin","","","",create_date,"","","")
//
//        db.collection("Member").document(key)
//            .set(account)
//            .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!") }
//            .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }
//    }

    fun login() {
        val email = binding.loginUser.text.toString()
        val password = binding.loginPassword.text.toString()
        var boo: Boolean = false

        val intent = Intent(activity, DashboardActivity::class.java)
        startActivity(intent)
        activity.finish()

    }

    fun showHidePass() {
        if (showhide) {
            binding.loginPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            showhide = false
        } else {
            binding.loginPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            showhide = true
        }
    }
}