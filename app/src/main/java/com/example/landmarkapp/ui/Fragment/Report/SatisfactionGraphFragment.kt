package com.example.landmarkapp.ui.Fragment.Report

import MyValueFormatter
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.example.landmarkapp.R
import com.example.landmarkapp.databinding.FragmentSatisfactionGraphBinding
import com.example.landmarkapp.ui.Activity.Report.DashboardActivity
import com.example.landmarkapp.ui.Fragment.BaseFragment
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class SatisfactionGraphFragment(private val activity: DashboardActivity, private val year: Int, private val month: String
                                , private val countexcellent: Int, private val countgood: Int, private val countaverage: Int
                                , private val countpoor: Int, private val countverypoor: Int): BaseFragment()
{
    private lateinit var binding: FragmentSatisfactionGraphBinding
    private lateinit var myContext: Context
    private lateinit var chart : HorizontalBarChart

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_satisfaction_graph,container,false)
        binding.monthyear.setText("เดือน"+month+" "+year.toString())
        initInstance()

        return binding.root
    }

    private fun initInstance(){
        createBarChart()
    }

    private fun createBarChart(){
        chart = binding.horizontalChart
        chart.setDrawBarShadow(false)
//        val description = Description()
//        description.text = ""
        chart.description = null
        chart.legend.isEnabled = false
        chart.setPinchZoom(false)
        chart.setDrawValueAboveBar(true)


        val xAxis = chart.getXAxis()
        xAxis.setDrawGridLines(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.isEnabled = true
        xAxis.setDrawAxisLine(true)
        xAxis.axisLineWidth = 2f
        xAxis.axisLineColor = Color.BLACK
        xAxis.labelCount = 5

        val values = arrayOf("ปรับปรุง", "พอใช้", "ดี", "ดีมาก", "ดีเยี่ยม")
        val tf = ResourcesCompat.getFont(myContext, R.font.rsu_light)
        xAxis.valueFormatter = IndexAxisValueFormatter(values)
        xAxis.typeface = tf
        xAxis.textSize = 13f

        val yLeft = chart.axisLeft
//        yLeft.axisMaximum = 100f
        yLeft.axisMinimum = 0f
        yLeft.isEnabled = false

        val yRight = chart.axisRight
        yRight.setDrawAxisLine(true)
        yRight.setDrawGridLines(false)
        yRight.axisMinimum = 0f
        yRight.axisLineWidth = 2f
        yRight.axisLineColor = Color.BLACK
        yRight.isEnabled = true

        yRight.typeface = tf
        yRight.textSize = 13f

        setGraphData()
    }

    private fun setGraphData() {
        //Add a list of bar entries
        val entries = ArrayList<BarEntry?>()
        entries.add(BarEntry(0f, countverypoor.toFloat()))
        entries.add(BarEntry(1f, countpoor.toFloat()))
        entries.add(BarEntry(2f, countaverage.toFloat()))
        entries.add(BarEntry(3f, countgood.toFloat()))
        entries.add(BarEntry(4f, countexcellent.toFloat()))

        val barDataSet = BarDataSet(entries, "Bar Data Set")

        barDataSet.setColors(
            ContextCompat.getColor(chart.context, R.color.graphverypoor),
            ContextCompat.getColor(chart.context, R.color.graphpoor),
            ContextCompat.getColor(chart.context, R.color.graphaverage),
            ContextCompat.getColor(chart.context, R.color.graphgood),
            ContextCompat.getColor(chart.context, R.color.graphexcellent))


//        chart.setDrawBarShadow(true)
//        barDataSet.barShadowColor = Color.argb(40, 150, 150, 150)

        val data = BarData(barDataSet)
        data.barWidth = 0.5f
        data.setValueTextSize(10f)
        data.setValueFormatter(MyValueFormatter())
        val tf = ResourcesCompat.getFont(myContext, R.font.rsu_light)
        data.setValueTypeface(tf)
        data.setValueTextSize(13f)

        chart.data = data
        chart.invalidate()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
    }

}