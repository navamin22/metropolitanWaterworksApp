package com.example.landmarkapp.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.landmarkapp.R
import com.example.tps780app.View.Class.UndoListener
import com.google.android.material.snackbar.Snackbar

fun Context.toast(message: String){
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}

fun View.snackbar(message: String){
    Snackbar.make(this,message,Snackbar.LENGTH_SHORT).setAction("Done",UndoListener()).show()
}

fun View.snackbarRate(message: String){
    Snackbar.make(this,message,Snackbar.LENGTH_SHORT).setAction("Done",UndoListener()).setBackgroundTint(
        ContextCompat.getColor(context,R.color.moreRed)).show()
}