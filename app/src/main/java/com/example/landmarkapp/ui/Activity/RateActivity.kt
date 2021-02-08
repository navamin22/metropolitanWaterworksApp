package com.example.landmarkapp.ui.Activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.ActivityRateBinding
import com.example.landmarkapp.ui.Fragment.RateFragment

class RateActivity : BaseActivity() {
    private lateinit var binding: ActivityRateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rate)
        initInstance()
    }

    private fun initInstance(){
        setFrameLayout()
    }

    private fun setFrameLayout(){
        supportFragmentManager.beginTransaction().apply {
            val frag = RateFragment(this@RateActivity)
            replace(R.id.frameLayout,frag)
            commit()
        }
    }

}