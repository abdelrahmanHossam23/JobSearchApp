package com.example.myjobsearchapplication.ui.screens.job_search_screen.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.core.i18n.DateTimeFormatter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myjobsearchapplication.domain.model.CustomExceptionDomainModel
import com.example.myjobsearchapplication.domain.usecase.FetchIJobsUseCase
import com.example.myjobsearchapplication.ui.mapper.toCustomExceptionPresentationModel
import com.example.myjobsearchapplication.ui.mapper.toJobsUiModel
import com.example.myjobsearchapplication.ui.screens.job_search_screen.JobListUiState
import com.example.myjobsearchapplication.ui.screens.job_search_screen.JobUiModel
import com.example.myjobsearchapplication.ui.screens.saved_jobs.viewmodel.SavedJobViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class JobSearchViewModel @Inject constructor(
    private val fetchIJobsUseCase: FetchIJobsUseCase
) : ViewModel() {


    private val _jobSearchState = MutableStateFlow(JobListUiState(isLoading = true))
    val jobSearchState: StateFlow<JobListUiState> = _jobSearchState.asStateFlow()


    private var allJobs: List<JobUiModel> = emptyList()
    private var hasLoadedData = false



    fun fetchJobs(
    ) {

        viewModelScope.launch(Dispatchers.IO) {
            try {

                allJobs = fetchIJobsUseCase().toJobsUiModel()

                _jobSearchState.value = JobListUiState(
                    isLoading = false,
                    jobList = allJobs,
                    searchQuery = "",
                    filterOptions = FilterOptions()
                )

                hasLoadedData = true

            } catch (e: Exception) {

                val customError = when (e) {
                    is CustomExceptionDomainModel -> e
                    is UnknownHostException -> CustomExceptionDomainModel.NoInternetConnectionExceptionDomainModel
                    is SocketTimeoutException -> CustomExceptionDomainModel.TimeoutExceptionDomainModel
                    is HttpException -> when (e.code()) {
                        403 -> CustomExceptionDomainModel.AccessDeniedExceptionDomainModel
                        404 -> CustomExceptionDomainModel.ServiceNotFoundExceptionDomainModel
                        503 -> CustomExceptionDomainModel.ServiceUnavailableExceptionDomainModel
                        else -> CustomExceptionDomainModel.NetworkExceptionDomainModel
                    }
                    else -> CustomExceptionDomainModel.UnknownExceptionDomainModel
                }

                _jobSearchState.value = JobListUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = customError.toCustomExceptionPresentationModel()
                )
            }
        }
    }

    fun fetchJobsIfNeeded() {
        if (!hasLoadedData) {
            fetchJobs()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onSearchQueryChanged(query: String) {
        _jobSearchState.value = _jobSearchState.value.copy(searchQuery = query)
        filterJobs()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateFilterOptions(newFilters: FilterOptions) {
        _jobSearchState.value = _jobSearchState.value.copy(filterOptions = newFilters)
        filterJobs()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun filterJobs() {

        val currentState = _jobSearchState.value
        val query = currentState.searchQuery.lowercase()
        val filters = currentState.filterOptions

        val filteredJobs = allJobs.filter { job ->
            val matchesQuery = query.isEmpty() ||
                    job.title.lowercase().contains(query) ||
                    job.company.lowercase().contains(query)

            val matchesMinSalary = filters.minSalary == null || (job.salary_min ?: 0.0) >= filters.minSalary
            val matchesMaxSalary = filters.maxSalary == null || (job.salary_min ?: 0.0) <= filters.maxSalary

            val jobDate = job.created

            val dateMatches = when (filters.datePosted) {
                "24h" -> isWithinDays(jobDate, 1)
                "7d" -> isWithinDays(jobDate, 7)
                "30d" -> isWithinDays(jobDate, 30)
                else -> true
            }

            val matchesContractType = filters.contractType.isEmpty() ||
                    job.contract_type.equals(filters.contractType, ignoreCase = true)

            val matchesContractTime = filters.contractTime.isEmpty() ||
                    job.contract_time.equals(filters.contractTime, ignoreCase = true)

            matchesQuery && matchesMinSalary && matchesMaxSalary &&
                    dateMatches && matchesContractType && matchesContractTime
        }

        _jobSearchState.value = currentState.copy(jobList = filteredJobs)

    }

    fun getJobById(jobId: Long): JobUiModel? {


        Log.d("getJobById", "Looking for jobId: $jobId in job list: ${_jobSearchState.value.jobList.map { it.id }}")

        val founded = _jobSearchState.value.jobList.find { it.id.toString() == jobId.toString() }

        if (founded != null) {
            Log.d("getJobById", "Job found: $founded")
        } else {
            Log.w("getJobById", "No job found with ID: $jobId")
        }

        return founded
    }

}




@RequiresApi(Build.VERSION_CODES.O)
private fun isWithinDays(dateStr: String, days: Int): Boolean {
    val formatter = DateTimeFormatter.ISO_DATE_TIME
    val jobDate = LocalDateTime.parse(dateStr, formatter)
    return jobDate.isAfter(LocalDateTime.now().minusDays(days.toLong()))
}


data class FilterOptions(
    val minSalary: Double? = null,
    val maxSalary: Double? = null,
    val datePosted: String = "",
    val contractType: String = "",
    val contractTime: String = ""
)
