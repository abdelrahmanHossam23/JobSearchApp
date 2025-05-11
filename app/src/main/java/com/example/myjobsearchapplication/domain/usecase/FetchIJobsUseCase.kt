package com.example.myjobsearchapplication.domain.usecase

import com.example.myjobsearchapplication.domain.model.JobsDomainModel
import com.example.myjobsearchapplication.domain.repository.JobsRepository
import javax.inject.Inject

class FetchIJobsUseCase @Inject constructor(
    private var jobsRepository: JobsRepository
) {
    suspend operator fun invoke(): List<JobsDomainModel>{
        return jobsRepository.fetchJobs()
    }
}