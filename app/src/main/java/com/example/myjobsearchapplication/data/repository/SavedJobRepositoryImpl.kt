package com.example.myjobsearchapplication.data.repository

import com.example.myjobsearchapplication.data.dataSources.local.dao.JobDao
import com.example.myjobsearchapplication.data.mapper.toEntity
import com.example.myjobsearchapplication.domain.model.JobsDomainModel
import com.example.myjobsearchapplication.domain.repository.SavedJobRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlinx.coroutines.flow.map
import com.example.myjobsearchapplication.data.mapper.toDomain
import com.example.myjobsearchapplication.ui.common_components.JobStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.withContext


class SavedJobRepositoryImpl @Inject constructor(private val dao: JobDao) : SavedJobRepository {
    override suspend fun saveJob(job: JobsDomainModel) = withContext(Dispatchers.IO) {
        dao.insert(job.toEntity())
    }

    override fun getSavedJobs(): Flow<List<JobsDomainModel>> {
        return dao.getAll().distinctUntilChanged().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun deleteJob(id: Long) = withContext(Dispatchers.IO){
        dao.delete(id)
    }

//    override suspend fun isJobSaved(id: Long): Boolean {
//        return dao.isSaved(id)
//    }
private val savedIdsCache = mutableSetOf<Long>()

    override suspend fun isJobSaved(id: Long): Boolean {
        return if (savedIdsCache.contains(id)) {
            true
        } else {
            val exists = dao.isSaved(id)
            if (exists) savedIdsCache.add(id)
            exists
        }
    }

    override suspend fun updateJobStatus(id: Long, newStatus: JobStatus) =
        withContext(Dispatchers.IO) {
            dao.updateJobStatus(id, newStatus)
        }
}