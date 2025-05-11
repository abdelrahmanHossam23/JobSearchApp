package com.example.myjobsearchapplication.data.dataSources.remote.retrofit.api

import com.example.myjobsearchapplication.data.dataSources.remote.retrofit.datamodel.JobDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface JobApi {
    @GET("search/1")
    suspend fun fetchJobs(
        @Query("app_id") appId: String,
        @Query("app_key") appKey: String,
        @Query("results_per_page") resultsPerPage: Int = 10
    ): Response<JobDataModel>
}