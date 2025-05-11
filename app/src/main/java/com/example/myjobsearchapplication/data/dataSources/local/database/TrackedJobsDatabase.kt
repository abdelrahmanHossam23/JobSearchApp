package com.example.myjobsearchapplication.data.dataSources.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myjobsearchapplication.data.dataSources.local.dao.JobDao
import com.example.myjobsearchapplication.data.dataSources.local.dao.TrackedJobsDao
import com.example.myjobsearchapplication.data.dataSources.local.entity.JobEntity
import com.example.myjobsearchapplication.data.dataSources.local.entity.TrackedJobsEntity

//// data/dataSources/local/database/JobDatabase.kt
//@Database(
//    entities = [TrackedJobsEntity::class],
//    version = 1,
//    exportSchema = false
//)
//abstract class TrackedJobsDatabase : RoomDatabase() {
//    abstract fun trackedDao(): TrackedJobsDao
//}