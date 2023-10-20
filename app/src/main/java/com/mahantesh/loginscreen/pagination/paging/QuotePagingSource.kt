package com.mahantesh.loginscreen.pagination.paging

import android.annotation.SuppressLint
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mahantesh.loginscreen.pagination.models.Result
import com.mahantesh.loginscreen.pagination.retrofit.QuoteApi

class QuotePagingSource(val quoteApi: QuoteApi) :PagingSource <Int, Result>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
       return try {

            val position = params.key ?: 1
            val response = quoteApi.getQuotes(position)
              LoadResult.Page(
                data = response.results,
                prevKey = if (position ==1 ) null else position-1,
                nextKey = if (position == response.totalPage) null else position+1
            )
        }catch (e:java.lang.Exception){
            LoadResult.Error(e)

        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }


//        if (state.anchorPosition != null){
//            val anchorpage = state.closestPageToPosition(state.anchorPosition!!)
//            if(anchorpage?.prevKey != null){
//                return anchorpage.prevKey!!.plus(1);
//            }
//            else if (anchorpage?.nextKey != null){
//                return anchorpage.nextKey!!.minus(1);
//            }
//        }else{
//            return  null
//        }
    }
}