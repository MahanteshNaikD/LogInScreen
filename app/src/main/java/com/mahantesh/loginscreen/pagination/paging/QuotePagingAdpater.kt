package com.mahantesh.loginscreen.pagination.paging

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mahantesh.loginscreen.R
import com.mahantesh.loginscreen.pagination.models.Result

class QuotePagingAdpater : PagingDataAdapter<Result, QuotePagingAdpater.QuoteViewAdapter>(COMPARATOR) {

    class  QuoteViewAdapter(itemView :View): RecyclerView.ViewHolder(itemView) {
        val quote: AppCompatTextView = itemView.findViewById(R.id.quote )
    }


    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem._id == newItem._id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: QuoteViewAdapter, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.quote.text = item.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewAdapter {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return QuoteViewAdapter(view)
    }
}
