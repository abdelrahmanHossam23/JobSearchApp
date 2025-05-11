package com.example.myjobsearchapplication.di

import com.example.myjobsearchapplication.data.Constants.Companion.BASE_URL
import com.example.myjobsearchapplication.data.dataSources.remote.retrofit.api.JobApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()
    }



    @Provides
    @Singleton
    fun provideJobApi(
        retrofit: Retrofit
    ):JobApi {
        return retrofit.create(JobApi::class.java)
    }




}