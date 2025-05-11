package com.example.myjobsearchapplication.di

import android.content.Context
import androidx.room.Room
import com.example.myjobsearchapplication.data.dataSources.local.dao.JobDao
import com.example.myjobsearchapplication.data.dataSources.local.dao.ReminderDao
import com.example.myjobsearchapplication.data.dataSources.local.dao.TrackedJobsDao
import com.example.myjobsearchapplication.data.dataSources.local.database.JobDatabase
import com.example.myjobsearchapplication.data.dataSources.local.database.ReminderDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class) // Lives throughout the app's lifecycle
object DatabaseModule {

    @Provides
    @Singleton
    fun provideJobDatabase(@ApplicationContext context: Context): JobDatabase {
        return Room.databaseBuilder(
            context,
            JobDatabase::class.java,
            "job_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideJobDao(database: JobDatabase): JobDao {
        return database.jobDao()
    }

    @Provides
    @Singleton
    fun provideReminderDatabase(@ApplicationContext context: Context): ReminderDatabase {
        return Room.databaseBuilder(
            context,
            ReminderDatabase::class.java,
            "reminder_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideReminderDao(db: ReminderDatabase): ReminderDao {
        return db.reminderDao()
    }



    @Provides
    fun provideTrackedJobsDao(database: JobDatabase): TrackedJobsDao {
        return database.trackedDao()
    }


}