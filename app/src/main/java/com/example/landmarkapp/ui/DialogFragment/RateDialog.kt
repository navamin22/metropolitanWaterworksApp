package com.example.landmarkapp.ui.DialogFragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.DialogRateBinding
import com.example.landmarkapp.ui.Activity.QueueUserActivity
import com.example.landmarkapp.utils.StaticData
import com.example.landmarkapp.utils.snackbarRate
import com.example.landmarkapp.utils.toast
import com.example.landmarkapp.viewmodel.QueueUserViewModel
import com.hsalf.smileyrating.SmileyRating
import kotlinx.android.synthetic.main.dialog_thanks.view.*
import java.util.zip.Inflater

class RateDialog(private val activity: QueueUserActivity, private val viewModel: QueueUserViewModel): DialogFragment() {
    private lateinit var myContext: Context
    private lateinit var binding: DialogRateBinding
    private val smileTitle = arrayOf("ปรับปรุง","เเย่","พอใช้","ดี","ดีมาก")
    private var rating: Int = -1
    private var ratingTxt: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.dialog_rate,container,false)
        initInstance()

        return binding.root
    }

    private fun initInstance(){
        setRatingBar()
        onClickActivate()
    }

    private fun onClickActivate(){
        binding.submitRate.setOnClickListener {
            if (rating != -1){
                if (rating < 3){
                    viewModel.insertRate(rating,ratingTxt,"Enable","Below")
                } else {
                    viewModel.insertRate(rating,ratingTxt,"Enable","Normal")
                }
                val dialog = LayoutInflater.from(myContext).inflate(R.layout.dialog_thanks,null)
                val builder = AlertDialog.Builder(myContext).setView(dialog)
                val alertDialog = builder.show()
                dialog.back_btn.setOnClickListener {
                    alertDialog.dismiss()
                    dismiss()
                }

            } else {
                binding.root.snackbarRate("กรุณาให้คะเเนนความพึงพอใจด้วยค่ะ")
            }
        }

        binding.dialogClose.setOnClickListener {
            dismiss()
        }

    }

    private fun setRatingBar(){
        binding.ratingBar.setTitle(SmileyRating.Type.TERRIBLE, smileTitle[0])
        binding.ratingBar.setFaceBackgroundColor(SmileyRating.Type.TERRIBLE, ContextCompat.getColor(activity, R.color.red))
        binding.ratingBar.setTitle(SmileyRating.Type.BAD, smileTitle[1])
        binding.ratingBar.setFaceBackgroundColor(SmileyRating.Type.BAD, ContextCompat.getColor(activity, R.color.orange))
        binding.ratingBar.setTitle(SmileyRating.Type.OKAY, smileTitle[2])
        binding.ratingBar.setFaceBackgroundColor(SmileyRating.Type.OKAY, ContextCompat.getColor(activity, R.color.redPink))
        binding.ratingBar.setTitle(SmileyRating.Type.GOOD, smileTitle[3])
        binding.ratingBar.setFaceBackgroundColor(SmileyRating.Type.GOOD, ContextCompat.getColor(activity, R.color.yellow))
        binding.ratingBar.setTitle(SmileyRating.Type.GREAT, smileTitle[4])
        binding.ratingBar.setFaceBackgroundColor(SmileyRating.Type.GREAT, ContextCompat.getColor(activity, R.color.gold))
        binding.ratingBar.setSmileySelectedListener {
            rating = it.rating
            ratingTxt = smileTitle[rating-1]
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