package com.mahantesh.loginscreen.Graphs

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.mahantesh.loginscreen.R

class GraphActivity : AppCompatActivity() {



    private lateinit var lineChart : LineChart


    private lateinit var barChart: BarChart

    private lateinit var horizontalBarChart: HorizontalBarChart

    private lateinit var pieChart: PieChart

    @SuppressLint("MissingInflatedId", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.line_graph)

        lineChart = findViewById(R.id.lineChart)
        barChart = findViewById(R.id.barChart);
        horizontalBarChart = findViewById(R.id.horizontalBar_chart);
        pieChart = findViewById(R.id.pic_chart);

        val entries = ArrayList<Entry>();

        val barEntries = ArrayList<BarEntry>();

        val pieEntries = ArrayList<PieEntry>();




        entries.add(Entry(1f,10f));
        entries.add(Entry(2f,2f));
        entries.add(Entry(3f,7f));
        entries.add(Entry(4f,20f));
        entries.add(Entry(5f,16f));


        barEntries.add(BarEntry(1f,10f));
        barEntries.add(BarEntry(2f,2f));
        barEntries.add(BarEntry(3f,7f));
        barEntries.add(BarEntry(4f,20f));
        barEntries.add(BarEntry(5f,16f));


        pieEntries.add(PieEntry(20F,"Orders"))
        pieEntries.add(PieEntry(30F,"Delivered"))
        pieEntries.add(PieEntry(40F,"Dispatched"))
        pieEntries.add(PieEntry(10F,"Not Delivered"))




        val v1 = LineDataSet(entries,"Line Graph");

        val v2 = BarDataSet(barEntries ,"Bar chart")

        val v3 = PieDataSet(pieEntries,"Pie Chart");




        v1.setDrawValues(false)
        v1.setDrawFilled(true)
        v1.lineWidth = 3f
        v1.fillColor = R.color.light_blue_600
        v1.fillAlpha = R.color.primaryColor


        v3.setDrawIcons(false)
        v3.sliceSpace = 3f
        v3.iconsOffset = MPPointF(0F, 40F)
        v3.selectionShift = 5f

        val colors: ArrayList<Int> = ArrayList()

        colors.add(ColorTemplate.rgb(getString(R.color.red)))
        colors.add(ColorTemplate.rgb(getString(R.color.blue)))

        colors.add(ColorTemplate.getHoloBlue())
        v3.colors = colors



        lineChart.xAxis.labelRotationAngle = 0f


        lineChart.data = LineData(v1)

        lineChart.axisRight.isEnabled = false

        lineChart.xAxis.mAxisMaximum = 1+0.1f

        lineChart.setTouchEnabled(true)
        lineChart.setPinchZoom(true)


        lineChart.description.text = "Days"
        lineChart.setNoDataText("No forex yet!")

        lineChart.animateX(1800, Easing.EaseInExpo)


        val markerView = CustomMarker(this@GraphActivity, R.layout.marker_view)
        lineChart.marker = markerView



        barChart.xAxis.labelRotationAngle = 0f
        barChart.data = BarData(v2)
        barChart.axisRight.isEnabled = false
        barChart.xAxis.mAxisMaximum = 1+0.1f
        barChart.setTouchEnabled(true)
        barChart.setPinchZoom(true)
        barChart.description.text = "Days"
        barChart.setNoDataText("No forex yet!")
        barChart.animateX(1800,Easing.EaseInExpo)
        barChart.marker = markerView



        horizontalBarChart.xAxis.labelRotationAngle = 0f
        horizontalBarChart.data = BarData(v2)
        horizontalBarChart.axisRight.isEnabled = false
        horizontalBarChart.xAxis.mAxisMaximum = 1+0.1f
        horizontalBarChart.setTouchEnabled(true)
        horizontalBarChart.setPinchZoom(true)
        horizontalBarChart.description.text = "Days"
        horizontalBarChart.setNoDataText("No forex yet!")
        horizontalBarChart.animateX(1800,Easing.EaseInExpo)
        horizontalBarChart.marker = markerView



        val data = PieData(v3)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        //data.setValueTypeface(tfLight)
        pieChart.data = data
        pieChart.marker = markerView
    }
}