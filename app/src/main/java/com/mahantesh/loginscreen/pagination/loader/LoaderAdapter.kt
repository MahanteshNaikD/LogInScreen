package com.mahantesh.loginscreen.pagination.loader

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.ContentLoadingProgressBar
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mahantesh.loginscreen.R

class LoaderAdapter : LoadStateAdapter<LoaderAdapter.LoaderViewHolder>() {


    class  LoaderViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
         val progressBar = itemView.findViewById<ContentLoadingProgressBar>(R.id.progress_bar);


        fun bind(loadState:LoadState) {
            progressBar.isVisible = loadState is LoadState.Loading
        }
    }

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
       holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.loader_item,parent,false)
        return  LoaderViewHolder(view)
    }


}