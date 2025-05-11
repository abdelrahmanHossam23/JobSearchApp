package com.example.myjobsearchapplication.di

import com.example.myjobsearchapplication.domain.repository.SavedJobRepository
import com.example.myjobsearchapplication.domain.repository.TrackedJobsRepository
import com.example.myjobsearchapplication.domain.usecase.DeleteJobUseCase
import com.example.myjobsearchapplication.domain.usecase.DeleteTrackedJobUseCase
import com.example.myjobsearchapplication.domain.usecase.GetSavedJobsUseCase
import com.example.myjobsearchapplication.domain.usecase.GetTrackedJobsUseCase
import com.example.myjobsearchapplication.domain.usecase.IsJobSavedUseCase
import com.example.myjobsearchapplication.domain.usecase.SaveJobUseCase
import com.example.myjobsearchapplication.domain.usecase.SaveTrackedJobUseCase
import com.example.myjobsearchapplication.domain.usecase.UpdateSavedJobsStatusUseCase
import com.example.myjobsearchapplication.domain.usecase.UpdateTrackedJobStatusUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideSaveJobUseCase(repository: SavedJobRepository): SaveJobUseCase {
        return SaveJobUseCase(repository)
    }

    @Provides
    fun provideGetSavedJobsUseCase(repository: SavedJobRepository): GetSavedJobsUseCase {
        return GetSavedJobsUseCase(repository)
    }

    @Provides
    fun provideDeleteJobUseCase(repository: SavedJobRepository): DeleteJobUseCase {
        return DeleteJobUseCase(repository)
    }

    @Provides
    fun provideIsJobSavedUseCase(repository: SavedJobRepository): IsJobSavedUseCase {
        return IsJobSavedUseCase(repository)
    }

    @Provides
    fun provideUpdateSavedJobUseCase(repository: SavedJobRepository): UpdateSavedJobsStatusUseCase {
        return UpdateSavedJobsStatusUseCase(repository)
    }



    @Provides
    fun provideTrackJobUseCase(repository: TrackedJobsRepository): SaveTrackedJobUseCase {
        return SaveTrackedJobUseCase(repository)
    }

    @Provides
    fun provideGetTrackedJobsUseCase(repository: TrackedJobsRepository): GetTrackedJobsUseCase {
        return GetTrackedJobsUseCase(repository)
    }

    @Provides
    fun provideDeleteTrackedJobUseCase(repository: TrackedJobsRepository): DeleteTrackedJobUseCase {
        return DeleteTrackedJobUseCase(repository)
    }

    @Provides
    fun provideUpdateTrackedJobUseCase(repository: TrackedJobsRepository): UpdateTrackedJobStatusUseCase {
        return UpdateTrackedJobStatusUseCase(repository)
    }


}