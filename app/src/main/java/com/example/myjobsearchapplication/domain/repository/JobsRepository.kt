package com.example.myjobsearchapplication.domain.repository

import com.example.myjobsearchapplication.domain.model.JobsDomainModel

interface JobsRepository {
    suspend fun fetchJobs(): List<JobsDomainModel>
}