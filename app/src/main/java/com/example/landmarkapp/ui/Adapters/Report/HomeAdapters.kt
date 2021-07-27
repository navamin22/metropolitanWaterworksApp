package com.example.landmarkapp.ui.Adapters.Report

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.landmarkapp.R
import com.example.landmarkapp.model.HomeReport
import com.example.landmarkapp.model.Retrofit.response.RateResponse
import com.example.landmarkapp.model.Retrofit.response.ReportResponse
import com.example.landmarkapp.ui.Activity.Report.DashboardActivity
import kotlinx.android.synthetic.main.recycler_home.view.*

class HomeAdapters(private val activity: DashboardActivity, private val context: Context, private val items: ArrayList<HomeReport>): RecyclerView.Adapter<HomeAdapters.ViewHolder>() {
    private var dataList2 = mutableListOf<RateResponse>()
    private var dataList = mutableListOf<ReportResponse>()

    fun setRateData(data: MutableList<RateResponse>){
        dataList2 = data
    }

    fun setReportData(data: MutableList<ReportResponse>){
        dataList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_home, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder.username.text = dataList[position].username
        //holder.name.text = dataList[position].name
        holder.counter_number.text = items[position].counterNumber.toString()
        holder.number.text = items[position].totalUser.toString()
        holder.lowNumber.text = items[position].totalBelowRate.toString()

    }

    inner class ViewHolder(itemsView: View): RecyclerView.ViewHolder(itemsView){
        val counter_number = itemsView.counter_no
        val number = itemsView.no
        val lowNumber = itemView.lowno
    }


//    override fun getItemCount(): Int {
//        return if (dataList.size > 0){
//            dataList.size
//        } else {
//            0
//        }
//    }

}