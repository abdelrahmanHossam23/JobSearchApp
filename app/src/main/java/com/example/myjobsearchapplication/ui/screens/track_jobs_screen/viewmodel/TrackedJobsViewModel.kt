package com.example.myjobsearchapplication.ui.screens.track_jobs_screen.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myjobsearchapplication.domain.model.JobsDomainModel
import com.example.myjobsearchapplication.domain.usecase.DeleteJobUseCase
import com.example.myjobsearchapplication.domain.usecase.DeleteTrackedJobUseCase
import com.example.myjobsearchapplication.domain.usecase.GetSavedJobsUseCase
import com.example.myjobsearchapplication.domain.usecase.GetTrackedJobsUseCase
import com.example.myjobsearchapplication.domain.usecase.IsJobSavedUseCase
import com.example.myjobsearchapplication.domain.usecase.SaveJobUseCase
import com.example.myjobsearchapplication.domain.usecase.SaveTrackedJobUseCase
import com.example.myjobsearchapplication.domain.usecase.UpdateTrackedJobStatusUseCase
import com.example.myjobsearchapplication.ui.common_components.JobStatus
import com.example.myjobsearchapplication.ui.mapper.toDomain
import com.example.myjobsearchapplication.ui.screens.job_search_screen.JobUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.myjobsearchapplication.ui.mapper.toJobsUiModel
import com.example.myjobsearchapplication.ui.mapper.toUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map


@HiltViewModel
class TrackedJobsViewModel @Inject constructor(
    private val saveTrackedJobUseCase: SaveTrackedJobUseCase,
    private val getTrackedJobsUseCase: GetTrackedJobsUseCase,
    private val deleteTrackedJobUseCase: DeleteTrackedJobUseCase,
    private val updateTrackedJobStatusUseCase: UpdateTrackedJobStatusUseCase
) : ViewModel() {

    val savedJobs = getTrackedJobsUseCase()
        .map { jobs ->
            jobs.map { it.toUi() }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000), // Stop after 5 seconds of inactivity
            emptyList()
        )

    fun saveJob(job: JobUiModel) {
        viewModelScope.launch(Dispatchers.IO) {
            saveTrackedJobUseCase(job.toDomain())
        }
    }

    fun deleteJob(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteTrackedJobUseCase(id)
        }
    }

    fun updateJobStatus(jobId: Long, newStatus: JobStatus) {
        viewModelScope.launch(Dispatchers.IO) {
            updateTrackedJobStatusUseCase(jobId, newStatus)
        }
    }
}
