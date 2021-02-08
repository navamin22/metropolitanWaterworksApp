package com.example.landmarkapp.ui.DialogFragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.DialogRate2Binding
import com.example.landmarkapp.ui.Activity.QueueUserActivity
import com.example.landmarkapp.utils.StaticData
import com.example.landmarkapp.utils.snackbarRate
import com.example.landmarkapp.viewmodel.QueueUserViewModel
import kotlinx.android.synthetic.main.dialog_thanks.view.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RateDialog2(private val activity: QueueUserActivity, private val viewModel: QueueUserViewModel): DialogFragment()  {
    private lateinit var myContext: Context
    private lateinit var binding: DialogRate2Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.dialog_rate2,container,false)
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
                dismiss()
            }
            activity.launch {
                delay(3000)
                alertDialog.dismiss()
                dismiss()
            }
        }

        binding.imgPoor.setOnClickListener {
            println("Poor")
            val dialog = LayoutInflater.from(myContext).inflate(R.layout.dialog_thanks,null)
            val builder = AlertDialog.Builder(myContext).setView(dialog)
            val alertDialog = builder.show()
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

        binding.imgNormal.setOnClickListener {
            println("Normal")
            val dialog = LayoutInflater.from(myContext).inflate(R.layout.dialog_thanks,null)
            val builder = AlertDialog.Builder(myContext).setView(dialog)
            val alertDialog = builder.show()
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

        binding.imgGreat.setOnClickListener {
            println("Great")
            val dialog = LayoutInflater.from(myContext).inflate(R.layout.dialog_thanks,null)
            val builder = AlertDialog.Builder(myContext).setView(dialog)
            val alertDialog = builder.show()
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

        binding.imgExcellent.setOnClickListener {
            println("Excellent")
            val dialog = LayoutInflater.from(myContext).inflate(R.layout.dialog_thanks,null)
            val builder = AlertDialog.Builder(myContext).setView(dialog)
            val alertDialog = builder.show()
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
//        binding.submitRate.setOnClickListener {
//            if (rating != -1){
//                if (rating < 3){
//                    viewModel.insertRate(rating,ratingTxt,"Enable","Below")
//                } else {
//                    viewModel.insertRate(rating,ratingTxt,"Enable","Normal")
//                }
//                val dialog = LayoutInflater.from(myContext).inflate(R.layout.dialog_thanks,null)
//                val builder = AlertDialog.Builder(myContext).setView(dialog)
//                val alertDialog = builder.show()
//                dialog.back_btn.setOnClickListener {
//                    alertDialog.dismiss()
//                    dismiss()
//                }
//
//            } else {
//                binding.root.snackbarRate("กรุณาให้คะเเนนความพึงพอใจด้วยค่ะ")
//            }
//        }

        binding.dialogClose.setOnClickListener {
            dismiss()
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
    }

}
