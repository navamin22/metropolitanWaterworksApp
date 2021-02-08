package com.example.landmarkapp.ui.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.landmarkapp.R
import com.example.landmarkapp.model.ImageSlider
import kotlinx.android.synthetic.main.slide_image_item.view.*
import java.util.zip.Inflater

class ImageSliderAdapter(private val context: Context, private val items: ArrayList<ImageSlider>, viewPager2: ViewPager2): RecyclerView.Adapter<ImageSliderAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.slide_image_item,parent,false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        setImage(holder, position)
    }

    private fun setImage(holder: ViewHolder, position: Int){
        //holder.imgSlider.setImageResource(items[position].image)
        Glide
            .with(context)
            .load(items[position].image)
            .centerCrop()
            .fitCenter()
            .override(2000,1500)
            .into(holder.imgSlider)
    }

    inner class ViewHolder(itemsView: View): RecyclerView.ViewHolder(itemsView){
        val imgSlider = itemsView.findViewById<AppCompatImageView>(R.id.imageSlide)
    }
}