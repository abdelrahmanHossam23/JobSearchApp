package com.example.myjobsearchapplication.data.dataSources.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myjobsearchapplication.data.dataSources.local.dao.JobDao
import com.example.myjobsearchapplication.data.dataSources.local.dao.TrackedJobsDao
import com.example.myjobsearchapplication.data.dataSources.local.entity.JobEntity
import com.example.myjobsearchapplication.data.dataSources.local.entity.TrackedJobsEntity

@Database(
    entities = [JobEntity::class, TrackedJobsEntity::class],
    version = 4,
    exportSchema = false
)
abstract class JobDatabase : RoomDatabase() {
    abstract fun jobDao(): JobDao // This is what Hilt will provide
    abstract fun trackedDao(): TrackedJobsDao
}