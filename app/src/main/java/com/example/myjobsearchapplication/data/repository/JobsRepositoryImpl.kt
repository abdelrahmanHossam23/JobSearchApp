package com.example.myjobsearchapplication.data.repository

import com.example.myjobsearchapplication.data.dataSources.remote.JobRemoteDataSource
import com.example.myjobsearchapplication.data.mapper.toJobDomainModel
import com.example.myjobsearchapplication.domain.model.JobsDomainModel
import com.example.myjobsearchapplication.domain.repository.JobsRepository
import javax.inject.Inject

class JobsRepositoryImpl @Inject constructor(
    private val jobRemoteDataSource: JobRemoteDataSource
): JobsRepository {
    override suspend fun fetchJobs(): List<JobsDomainModel> {
        return jobRemoteDataSource.fetchJobs().toJobDomainModel()
    }
}