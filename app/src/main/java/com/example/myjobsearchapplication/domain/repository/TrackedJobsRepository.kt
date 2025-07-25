package com.example.myjobsearchapplication.domain.repository
import com.example.myjobsearchapplication.domain.model.JobsDomainModel
import com.example.myjobsearchapplication.ui.common_components.JobStatus
import kotlinx.coroutines.flow.Flow

interface TrackedJobsRepository {
    suspend fun saveJob(job: JobsDomainModel)
    fun getSavedJobs(): Flow<List<JobsDomainModel>>
    suspend fun deleteJob(id: Long)
    suspend fun updateJobStatus(id: Long, newStatus: JobStatus)
    suspend fun deleteAll()
}