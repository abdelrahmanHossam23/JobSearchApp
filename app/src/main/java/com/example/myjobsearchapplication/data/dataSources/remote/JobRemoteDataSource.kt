package com.example.myjobsearchapplication.data.dataSources.remote

import com.example.myjobsearchapplication.data.dataSources.remote.retrofit.api.JobApi
import com.example.myjobsearchapplication.data.dataSources.remote.retrofit.datamodel.JobDataModel
import javax.inject.Inject

class JobRemoteDataSource @Inject constructor(
    private val jobApi: JobApi
) {
    suspend fun fetchJobs(): JobDataModel {
        val response = jobApi.fetchJobs(
            appId = "9d0b649b",
            appKey = "9bc4139d9f91cf3c0d6a70aad6561f70"
        )

        if (response.isSuccessful) {
            return response.body() as JobDataModel
        } else {
            throw Exception("API Error: ${response.errorBody()?.string()}")
        }
    }
}