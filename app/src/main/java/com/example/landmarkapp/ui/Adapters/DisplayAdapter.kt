package com.example.landmarkapp.ui.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.RecyclerQueueDisplayBinding
import com.example.landmarkapp.model.Retrofit.response.QueueResponse

class DisplayAdapter : RecyclerView.Adapter<DisplayAdapter.ViewHolder>() {
    private var queueList: ArrayList<QueueResponse>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = DataBindingUtil.inflate<RecyclerQueueDisplayBinding>(
            LayoutInflater.from(parent.context),
            R.layout.recycler_queue_display,parent,false
        )

        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return if (queueList != null){
            queueList!!.size
        } else {
            0
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentQueue = queueList!![position].queueNum
        holder.binding.queueTxt.text = currentQueue
    }

    fun setQueueList(list: ArrayList<QueueResponse>){
        this.queueList = list
        notifyDataSetChanged()
        println("Queue size is ${queueList!!.size}")
    }

    inner class ViewHolder(var binding: RecyclerQueueDisplayBinding): RecyclerView.ViewHolder(binding.root)
}