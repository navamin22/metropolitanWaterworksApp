package com.example.landmarkapp.ui.Adapters.Report

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.landmarkapp.R
import com.example.landmarkapp.model.Retrofit.response.ReportResponse
import com.example.landmarkapp.ui.Activity.Report.DashboardActivity
import kotlinx.android.synthetic.main.recycler_time.view.*

class TimeAdapter(private val activity: DashboardActivity, private val items: ArrayList<ReportResponse>): RecyclerView.Adapter<TimeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_time, parent,false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dateTime.text = items[position].queueDate
        holder.queueNo.text = items[position].queueNumber
        holder.waitingTime.text = items[position].queueDate
        holder.serviceTime.text = items[position].latestCallDate
        holder.serviceEndTime.text = items[position].queueFinishDate
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val dateTime = itemView.date
        val queueNo = itemView.que_no
        val waitingTime = itemView.waiting_time
        val serviceTime = itemView.service_time
        val serviceEndTime = itemView.service_end_time
    }
}