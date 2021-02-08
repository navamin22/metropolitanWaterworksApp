package com.example.landmarkapp.ui.Fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.FragmentRateBinding
import com.example.landmarkapp.ui.Activity.RateActivity
import kotlinx.android.synthetic.main.dialog_thanks.view.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RateFragment(private val activity: RateActivity): BaseFragment() {
    private lateinit var myContext: Context
    private lateinit var binding: FragmentRateBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
            println("Very Poor")
            val dialog = LayoutInflater.from(myContext).inflate(R.layout.dialog_thanks,null)
            val builder = AlertDialog.Builder(myContext).setView(dialog)
            val alertDialog = builder.show()
            dialog.back_btn.setOnClickListener {
                alertDialog.dismiss()
            }
            activity.launch {
                delay(3000)
                alertDialog.dismiss()
            }
        }

        binding.imgPoor.setOnClickListener {
            println("Poor")
            val dialog = LayoutInflater.from(myContext).inflate(R.layout.dialog_thanks,null)
            val builder = AlertDialog.Builder(myContext).setView(dialog)
            val alertDialog = builder.show()
            dialog.back_btn.setOnClickListener {
                alertDialog.dismiss()
            }
            activity.launch {
                delay(3000)
                alertDialog.dismiss()
            }
        }

        binding.imgNormal.setOnClickListener {
            println("Normal")
            val dialog = LayoutInflater.from(myContext).inflate(R.layout.dialog_thanks,null)
            val builder = AlertDialog.Builder(myContext).setView(dialog)
            val alertDialog = builder.show()
            dialog.back_btn.setOnClickListener {
                alertDialog.dismiss()
            }
            activity.launch {
                delay(3000)
                alertDialog.dismiss()
            }
        }

        binding.imgGreat.setOnClickListener {
            println("Great")
            val dialog = LayoutInflater.from(myContext).inflate(R.layout.dialog_thanks,null)
            val builder = AlertDialog.Builder(myContext).setView(dialog)
            val alertDialog = builder.show()
            dialog.back_btn.setOnClickListener {
                alertDialog.dismiss()
            }
            activity.launch {
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