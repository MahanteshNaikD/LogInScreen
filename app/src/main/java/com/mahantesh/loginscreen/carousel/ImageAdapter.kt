package com.mahantesh.loginscreen.carousel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.mahantesh.loginscreen.R
import android.widget.ImageView

class ImageAdapter(val imageList :ArrayList<Int>,private val viewPager2: ViewPager2)
    : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>(){


    class ImageViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
         val imageView :ImageView  = itemView.findViewById<ImageView>(R.id.ImageView);

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_card_view,parent,false)
        return  ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
       holder.imageView.setImageResource(imageList[position])

        if (position == imageList.size -1){
            viewPager2.post(runnable)
        }
    }


    private val runnable = Runnable {
        imageList.addAll(imageList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
       return  imageList.size
    }

}