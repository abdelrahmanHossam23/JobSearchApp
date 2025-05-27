package com.example.myjobsearchapplication.domain.usecase

import com.example.myjobsearchapplication.domain.model.JobsDomainModel
import com.example.myjobsearchapplication.domain.repository.SavedJobRepository
import com.example.myjobsearchapplication.domain.repository.TrackedJobsRepository
import com.example.myjobsearchapplication.ui.common_components.JobStatus
import kotlinx.coroutines.flow.Flow

class SaveJobUseCase(private val repository: SavedJobRepository) {
    suspend operator fun invoke(job: JobsDomainModel) = repository.saveJob(job)
}

class GetSavedJobsUseCase(private val repository: SavedJobRepository) {
    operator fun invoke(): Flow<List<JobsDomainModel>> = repository.getSavedJobs()
}

class DeleteJobUseCase(private val repository: SavedJobRepository) {
    suspend operator fun invoke(id: Long) = repository.deleteJob(id)
}

class IsJobSavedUseCase(private val repository: SavedJobRepository) {
    suspend operator fun invoke(id: Long) = repository.isJobSaved(id)
}


class UpdateSavedJobsStatusUseCase(
    private val repository: SavedJobRepository
) {
    suspend operator fun invoke(id: Long, newStatus: JobStatus) =
        repository.updateJobStatus(id, newStatus)
}

class DeleteAllSavedJobsUseCase(
    private val repository: SavedJobRepository
) {
    suspend operator fun invoke() =
        repository.deleteAll()
}