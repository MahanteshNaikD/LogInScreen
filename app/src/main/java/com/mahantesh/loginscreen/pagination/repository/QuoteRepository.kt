package com.mahantesh.loginscreen.pagination.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.mahantesh.loginscreen.pagination.retrofit.QuoteApi
import com.mahantesh.loginscreen.pagination.paging.QuotePagingSource
import javax.inject.Inject

class QuoteRepository @Inject constructor(val quoteApi: QuoteApi) {

    fun getQuotes() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        pagingSourceFactory = { QuotePagingSource(quoteApi) }
    ).liveData
}