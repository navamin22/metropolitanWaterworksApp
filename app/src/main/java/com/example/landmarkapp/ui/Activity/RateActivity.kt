package com.example.landmarkapp.ui.Activity

import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.landmarkapp.Network.Repository
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.ActivityRateBinding
import com.example.landmarkapp.ui.Fragment.RateFragment
import com.example.landmarkapp.utils.StaticData
import com.example.landmarkapp.viewmodel.Display2ViewModel
import com.example.landmarkapp.viewmodel.RateViewModel

class RateActivity : BaseActivity() {
    private lateinit var binding: ActivityRateBinding
    private lateinit var handler: Handler
    private lateinit var rateViewModel: RateViewModel
    val repo = Repository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rate)
        initInstance()
    }

    private fun initInstance(){
        setViewModel()
        observeData()
        setFrameLayout()
        getEmployeeProfile()
    }

    private fun setViewModel(){
        rateViewModel = ViewModelProviders.of(this).get(RateViewModel::class.java)
        binding.lifecycleOwner = this
    }

    private fun observeData(){
        rateViewModel.mutableLiveData.observe(this,{
            rateViewModel.setAccountCounter(it)
        })
        rateViewModel.getName().observe(this,
            {
                name -> run{
                if (name.isEmpty()){
                    binding.name.text = "-"
                } else {
                    binding.name.text = name.toString()
                }
            }
        })
        rateViewModel.getSurname().observe(this,
            {
                surname -> run{
                binding.surname.text = surname.toString()
            }
        })
        rateViewModel.getEmpId().observe(this,
            {
                id -> run{
                if (id == "0"){
                    binding.officerIdNumber.text = "-"
                } else {
                    binding.officerIdNumber.text = id.toString()
                }
            }
        })
    }

    private fun getEmployeeProfile(){
        handler = Handler()
        handler.postDelayed(object : Runnable{
            override fun run() {
                rateViewModel.getAccountCounter(StaticData.currentChannelNo)
                handler.postDelayed(this,5000)
            }
        },5000)
    }

    private fun setFrameLayout(){
        supportFragmentManager.beginTransaction().apply {
            val frag = RateFragment(this@RateActivity,rateViewModel)
            replace(R.id.frameLayout,frag)
            commit()
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        StaticData.screen_width = binding.root.measuredWidth
        StaticData.screen_height = binding.root.measuredHeight
        println("Width of screen = ${StaticData.screen_width}")
        println("Height of screen = ${StaticData.screen_height}")
    }

}