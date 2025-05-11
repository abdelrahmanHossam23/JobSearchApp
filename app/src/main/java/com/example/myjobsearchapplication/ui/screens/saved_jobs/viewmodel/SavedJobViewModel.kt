package com.example.myjobsearchapplication.ui.screens.saved_jobs.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myjobsearchapplication.domain.model.JobsDomainModel
import com.example.myjobsearchapplication.domain.usecase.DeleteJobUseCase
import com.example.myjobsearchapplication.domain.usecase.GetSavedJobsUseCase
import com.example.myjobsearchapplication.domain.usecase.IsJobSavedUseCase
import com.example.myjobsearchapplication.domain.usecase.SaveJobUseCase
import com.example.myjobsearchapplication.domain.usecase.UpdateSavedJobsStatusUseCase
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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map


@HiltViewModel
class SavedJobViewModel @Inject constructor(
    private val saveJobUseCase: SaveJobUseCase,
    private val getSavedJobsUseCase: GetSavedJobsUseCase,
    private val deleteJobUseCase: DeleteJobUseCase,
    private val isJobSavedUseCase: IsJobSavedUseCase,
    private val updateSavedJobStatusUseCase: UpdateSavedJobsStatusUseCase
) : ViewModel() {

    // Cache for quick saved status checks
//    private val _savedJobIds = mutableStateSetOf<Long>()
//    val savedJobIds: Set<Long> get() = _savedJobIds

    // Use stateIn with WhileSubscribed for better resource management

    val savedJobs = getSavedJobsUseCase()
        .map { jobs ->
//            _savedJobIds.clear()
//            _savedJobIds.addAll(jobs.map { it.id })
            jobs.map { it.toUi() }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000), // Stop after 5 seconds of inactivity
            emptyList()
        )



    private val _savedJobIds = getSavedJobsUseCase()
        .map { jobs -> jobs.map { it.id }.toSet() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptySet()
        )

    // Public function to check if job is saved (as StateFlow)
    fun isJobSavedState(jobId: Long): StateFlow<Boolean> {
        return _savedJobIds.map { ids -> ids.contains(jobId) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = false
            )
    }


    fun saveJob(job: JobUiModel) {
        viewModelScope.launch(Dispatchers.IO) {
            saveJobUseCase(job.toDomain())
//            _savedJobIds.add(job.id) // Update cache immediately
        }
    }

    fun deleteJob(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteJobUseCase(id)
//            _savedJobIds.remove(id) // Update cache immediately
        }
    }

    suspend fun isJobSaved(id: Long): Boolean {
        return isJobSavedUseCase(id)
    }

    fun updateJobStatus(jobId: Long, newStatus: JobStatus) {
        viewModelScope.launch(Dispatchers.IO) {
            updateSavedJobStatusUseCase(jobId, newStatus)
        }
    }

    // Fast in-memory check first, fallback to DB if needed
//    suspend fun isJobSaved(id: Long): Boolean {
//        return if (_savedJobIds.contains(id)) {
//            true
//        } else {
//            isJobSavedUseCase(id).also { isSaved ->
//                if (isSaved) _savedJobIds.add(id)
//            }
//        }
//    }

//    // Non-suspending version for Composable use
//    fun isJobSavedNow(id: Long): Boolean = _savedJobIds.contains(id)

}
