package com.mahantesh.loginscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mahantesh.loginscreen.pagination.loader.LoaderAdapter
import com.mahantesh.loginscreen.pagination.paging.QuotePagingAdpater
import com.mahantesh.loginscreen.pagination.paging.QuoteViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PaginationActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter : QuotePagingAdpater
    lateinit var  quoteViewModel : QuoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagination)

        recyclerView = findViewById(R.id.quoteList)
        adapter = QuotePagingAdpater()
        quoteViewModel = ViewModelProvider(this)[QuoteViewModel::class.java]


        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoaderAdapter(),
            footer = LoaderAdapter()

        )


        quoteViewModel.list.observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }
}