package com.mahantesh.loginscreen.carousel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.mahantesh.loginscreen.R
import kotlin.math.abs

class ImageSliderActivity : AppCompatActivity() {


    private lateinit var viewPager2: ViewPager2
    private lateinit var hander :Handler
    private lateinit var imageList: ArrayList<Int>
    private lateinit var adapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_slider)

        init()

        setUpTransition()


        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                hander.removeCallbacks(runnable)
                hander.postDelayed(runnable,2000)
            }
        })
    }


    override fun onPause() {
        super.onPause()
        hander.removeCallbacks(runnable)
    }


    override fun onResume() {
        super.onResume()
        hander.postDelayed(runnable,2000)
    }


    private val runnable = Runnable {
        viewPager2.currentItem = viewPager2.currentItem +1
    }

    private fun setUpTransition() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer{page,position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r *0.14f
        }


        viewPager2.setPageTransformer(transformer)
    }


    private fun init(){
        viewPager2 = findViewById(R.id.viewPager2)
        hander = Handler(Looper.myLooper()!!)
        imageList = ArrayList()


        imageList.add(R.drawable.image1)
        imageList.add(R.drawable.image2)
        imageList.add(R.drawable.image3)


        adapter = ImageAdapter(imageList,viewPager2)

        viewPager2.adapter = adapter
        viewPager2.offscreenPageLimit = 3
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER


    }
}