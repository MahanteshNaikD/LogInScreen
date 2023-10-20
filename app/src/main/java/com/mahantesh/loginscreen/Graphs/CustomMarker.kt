package com.mahantesh.loginscreen.Graphs

import android.content.Context
import android.view.LayoutInflater
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.mahantesh.loginscreen.R
import android.widget.TextView
import com.github.mikephil.charting.utils.MPPointF

class CustomMarker(context:Context,layoutResource : Int) :MarkerView(context,layoutResource) {

    private lateinit var tvPrice : TextView;


    init {
        // Inflate the marker layout and find the TextView by its id
        val markerView = LayoutInflater.from(context).inflate(layoutResource, this, false)
        tvPrice = markerView.findViewById(R.id.tvPrice)
        // Add the inflated layout to the MarkerView
        addView(markerView)
    }

    override fun refreshContent(e: Entry?, highlight: Highlight?) {


        val value = e?.y?.toDouble() ?: 0.0
        var resText = ""
        if(value.toString().length > 8) {
            resText = "Val: " + value.toString().substring(0,7)
        }
        else{
            resText = "Val: $value"
        }

        tvPrice.text = resText
        super.refreshContent(e, highlight)


    }


    override fun getOffsetForDrawingAtPoint(posX: Float, posY: Float): MPPointF {
        return super.getOffsetForDrawingAtPoint(-width / 2f, -height - 10f)
    }
}