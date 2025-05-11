package com.example.myjobsearchapplication.data.dataSources.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myjobsearchapplication.data.converters.Converters
import com.example.myjobsearchapplication.data.dataSources.local.dao.ReminderDao
import com.example.myjobsearchapplication.data.dataSources.local.entity.ReminderEntity

@Database(
    entities = [ReminderEntity::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ReminderDatabase : RoomDatabase() {
    abstract fun reminderDao(): ReminderDao
}