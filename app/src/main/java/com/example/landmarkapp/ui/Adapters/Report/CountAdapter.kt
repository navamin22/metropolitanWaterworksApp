package com.example.landmarkapp.ui.Adapters.Report

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.landmarkapp.R
import com.example.landmarkapp.model.ReportTotalQueue
import com.example.landmarkapp.ui.Activity.Report.DashboardActivity
import kotlinx.android.synthetic.main.recycler_count.view.*

class CountAdapter(private val activity: DashboardActivity, private val items: ArrayList<ReportTotalQueue>, private val showEachTime: Boolean): RecyclerView.Adapter<CountAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_count, parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.date.text = items[position].date
        if (showEachTime) {
            setPeopleCountEachTime(holder,position)
        } else {
            setPeopleCount(holder, position)
        }
    }

    private fun setPeopleCount(holder: ViewHolder, position: Int){
        var count = 0
        holder.count.visibility = View.VISIBLE
        holder.linear.visibility = View.GONE
        for (i in items[position].totalList.indices){
            count += items[position].totalList[i]
        }
        holder.count.text = count.toString()
    }

    private fun setPeopleCountEachTime(holder: ViewHolder, position: Int){
        holder.linear.visibility = View.VISIBLE
        for (i in items[position].totalList.indices){
            //println("Total Count${i + 1} is ${items[position].totalList[i]}")
            when (i) {
                0 -> {
                    holder.count.visibility = View.VISIBLE
                    holder.count.text = items[position].totalList[i].toString()
                }
                1 -> {
                    holder.count2.visibility = View.VISIBLE
                    holder.count2.text = items[position].totalList[i].toString()
                }
                2 -> {
                    holder.count3.visibility = View.VISIBLE
                    holder.count3.text = items[position].totalList[i].toString()
                }
                3 -> {
                    holder.count4.visibility = View.VISIBLE
                    holder.count4.text = items[position].totalList[i].toString()
                }
                4 -> {
                    holder.count5.visibility = View.VISIBLE
                    holder.count5.text = items[position].totalList[i].toString()
                }
                5 -> {
                    holder.count6.visibility = View.VISIBLE
                    holder.count6.text = items[position].totalList[i].toString()
                }
                6 -> {
                    holder.count7.visibility = View.VISIBLE
                    holder.count7.text = items[position].totalList[i].toString()
                }
                7 -> {
                    holder.count8.visibility = View.VISIBLE
                    holder.count8.text = items[position].totalList[i].toString()
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val date = itemView.count_date
        val linear = itemView.linearEachTime
        val count = itemView.count1
        val count2 = itemView.count2
        val count3 = itemView.count3
        val count4 = itemView.count4
        val count5 = itemView.count5
        val count6 = itemView.count6
        val count7 = itemView.count7
        val count8 = itemView.count8

    }
}