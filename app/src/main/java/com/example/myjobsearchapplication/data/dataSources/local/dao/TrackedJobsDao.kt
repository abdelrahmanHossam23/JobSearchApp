package com.example.myjobsearchapplication.data.dataSources.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myjobsearchapplication.data.dataSources.local.entity.JobEntity
import com.example.myjobsearchapplication.data.dataSources.local.entity.TrackedJobsEntity
import com.example.myjobsearchapplication.ui.common_components.JobStatus
import kotlinx.coroutines.flow.Flow


@Dao
interface TrackedJobsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(job: TrackedJobsEntity)

    @Query("DELETE FROM tracked_jobs WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM tracked_jobs")
    fun getAll(): Flow<List<JobEntity>>

    @Query("UPDATE tracked_jobs SET status = :newStatus WHERE id = :id")
    suspend fun updateJobStatus(id: Long, newStatus: JobStatus)

    @Query("DELETE FROM tracked_jobs")
    suspend fun deleteAll()
}

