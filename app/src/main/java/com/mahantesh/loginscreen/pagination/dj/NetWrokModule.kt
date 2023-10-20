package com.mahantesh.loginscreen.pagination.dj

import com.mahantesh.loginscreen.pagination.retrofit.QuoteApi
import com.mahantesh.loginscreen.pagination.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetWorkModule {


    @Singleton
    @Provides
    fun getRetrofit() : Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }


    @Singleton
    @Provides
    fun getQuoteApi(retrofit: Retrofit) :QuoteApi {
        return  retrofit.create(QuoteApi::class.java)
    }
}