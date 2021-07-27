package com.example.landmarkapp.ui.Fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.FragmentRateBinding
import com.example.landmarkapp.ui.Activity.RateActivity
import com.example.landmarkapp.ui.DialogFragment.ReasonRateLow
import com.example.landmarkapp.ui.DialogFragment.ReasonRateLowCounter
import com.example.landmarkapp.utils.StaticData
import com.example.landmarkapp.viewmodel.RateViewModel
import kotlinx.android.synthetic.main.dialog_thanks.view.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class RateFragment(private val activity: RateActivity, private val viewModel: RateViewModel): BaseFragment() {
    private lateinit var myContext: Context
    private lateinit var binding: FragmentRateBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_rate,container,false)
        initInstance()

        return binding.root
    }

    private fun initInstance(){
        onClickActivate()
    }

    private fun onClickActivate(){
        binding.imgVerypoor.setOnClickListener {
            println("Poor")
            val fm = activity.supportFragmentManager
            val dialog_reason = ReasonRateLowCounter(activity)
            val bundle = Bundle()
            bundle.putInt("rateScore",1)
            bundle.putString("rateTitle","Poor")
            bundle.putString("active","Enable")
            bundle.putString("rateStatus","Below")
            bundle.putInt("serviceCounter", StaticData.currentChannelNo)
            dialog_reason.arguments = bundle
            dialog_reason.show(fm,"reason")

//            val dialog = LayoutInflater.from(myContext).inflate(R.layout.dialog_thanks,null)
//            val builder = AlertDialog.Builder(myContext).setView(dialog)
//            val alertDialog = builder.show()
//            dialog.back_btn.setOnClickListener {
//                alertDialog.dismiss()
//            }
//            activity.launch {
//                delay(3000)
//                alertDialog.dismiss()
//            }
        }

        binding.imgPoor.setOnClickListener {
            println("Fair")
            val fm = activity.supportFragmentManager
            val dialog_reason = ReasonRateLow()
            val bundle = Bundle()
            bundle.putInt("rateScore",2)
            bundle.putString("rateTitle","Fair")
            bundle.putString("active","Enable")
            bundle.putString("rateStatus","Below")
            bundle.putInt("serviceCounter", StaticData.currentChannelNo)
            dialog_reason.arguments = bundle
            dialog_reason.show(fm,"reason")

//            val dialog = LayoutInflater.from(myContext).inflate(R.layout.dialog_thanks,null)
//            val builder = AlertDialog.Builder(myContext).setView(dialog)
//            val alertDialog = builder.show()
//            dialog.back_btn.setOnClickListener {
//                alertDialog.dismiss()
//            }
//            activity.launch {
//                delay(3000)
//                alertDialog.dismiss()
//            }
        }

        binding.imgNormal.setOnClickListener {
            println("Good")
            val dialog = LayoutInflater.from(myContext).inflate(R.layout.dialog_thanks,null)
            val builder = AlertDialog.Builder(myContext).setView(dialog)
            val alertDialog = builder.show()
            dialog.back_btn.setOnClickListener {
                alertDialog.dismiss()
            }
            activity.launch {
                activity.repo.insertRate(3,"Good","Enable","Normal","","", StaticData.currentChannelNo,0)
                delay(3000)
                alertDialog.dismiss()
            }
        }

        binding.imgGreat.setOnClickListener {
            println("Very Good")
            val dialog = LayoutInflater.from(myContext).inflate(R.layout.dialog_thanks,null)
            val builder = AlertDialog.Builder(myContext).setView(dialog)
            val alertDialog = builder.show()
            dialog.back_btn.setOnClickListener {
                alertDialog.dismiss()
            }
            activity.launch {
                activity.repo.insertRate(4,"Very Good","Enable","Normal","","", StaticData.currentChannelNo,0)
                delay(3000)
                alertDialog.dismiss()
            }
        }

        binding.imgExcellent.setOnClickListener {
            println("Excellent")
            val dialog = LayoutInflater.from(myContext).inflate(R.layout.dialog_thanks,null)
            val builder = AlertDialog.Builder(myContext).setView(dialog)
            val alertDialog = builder.show()
            dialog.back_btn.setOnClickListener {
                alertDialog.dismiss()
            }
            activity.launch {
                activity.repo.insertRate(5,"Excellent","Enable","Normal","","", StaticData.currentChannelNo,0)
                delay(3000)
                alertDialog.dismiss()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
    }
}