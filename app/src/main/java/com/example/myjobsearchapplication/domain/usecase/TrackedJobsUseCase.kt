package com.example.myjobsearchapplication.domain.usecase

import com.example.myjobsearchapplication.domain.model.JobsDomainModel
import com.example.myjobsearchapplication.domain.repository.SavedJobRepository
import com.example.myjobsearchapplication.domain.repository.TrackedJobsRepository
import com.example.myjobsearchapplication.ui.common_components.JobStatus
import kotlinx.coroutines.flow.Flow

class SaveTrackedJobUseCase(private val repository: TrackedJobsRepository) {
    suspend operator fun invoke(job: JobsDomainModel) = repository.saveJob(job)
}

class GetTrackedJobsUseCase(private val repository: TrackedJobsRepository) {
    operator fun invoke(): Flow<List<JobsDomainModel>> = repository.getSavedJobs()
}

class DeleteTrackedJobUseCase(private val repository: TrackedJobsRepository) {
    suspend operator fun invoke(id: Long) = repository.deleteJob(id)
}

class UpdateTrackedJobStatusUseCase(
    private val repository: TrackedJobsRepository
) {
    suspend operator fun invoke(id: Long, newStatus: JobStatus) =
        repository.updateJobStatus(id, newStatus)
}

class DeleteAllTrackedJobsUseCase(
    private val repository: TrackedJobsRepository
) {
    suspend operator fun invoke() =
        repository.deleteAll()
}