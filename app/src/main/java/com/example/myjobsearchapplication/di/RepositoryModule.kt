package com.example.myjobsearchapplication.di

import com.example.myjobsearchapplication.data.dataSources.local.dao.JobDao
import com.example.myjobsearchapplication.data.dataSources.local.dao.TrackedJobsDao
import com.example.myjobsearchapplication.data.dataSources.remote.JobRemoteDataSource
import com.example.myjobsearchapplication.data.repository.AuthRepositoryImpl
import com.example.myjobsearchapplication.data.repository.JobsRepositoryImpl
import com.example.myjobsearchapplication.data.repository.SavedJobRepositoryImpl
import com.example.myjobsearchapplication.data.repository.TrackedJobsRepositoryImpl
import com.example.myjobsearchapplication.domain.repository.AuthRepository
import com.example.myjobsearchapplication.domain.repository.JobsRepository
import com.example.myjobsearchapplication.domain.repository.SavedJobRepository
import com.example.myjobsearchapplication.domain.repository.TrackedJobsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideJobRepository(
        jobRemoteDataSource: JobRemoteDataSource
    ): JobsRepository {
        return JobsRepositoryImpl(jobRemoteDataSource)
    }

    @Provides
    fun provideSavedJobRepository(jobDao: JobDao): SavedJobRepository {
        return SavedJobRepositoryImpl(jobDao)
    }

    @Provides
    fun provideTrackedJobRepository(trackedJobsDao: TrackedJobsDao): TrackedJobsRepository {
        return TrackedJobsRepositoryImpl(trackedJobsDao)
    }


}