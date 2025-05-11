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

//    private val _jobList = MutableStateFlow(emptyList<JobUiModel>())
//    val jobList: StateFlow<List<JobUiModel>> = _jobList.asStateFlow()

    private val _jobSearchState = MutableStateFlow(JobListUiState(isLoading = true))
    val jobSearchState: StateFlow<JobListUiState> = _jobSearchState.asStateFlow()

//    private val _searchQuery = MutableStateFlow("")
//    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
//
//    private val _filterOptions = MutableStateFlow(FilterOptions())
//    val filterOptions: StateFlow<FilterOptions> = _filterOptions.asStateFlow()

    private var allJobs: List<JobUiModel> = emptyList() // To keep original list

    init {
        fetchJobs()
    }

    fun fetchJobs() {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                allJobs = fetchIJobsUseCase().toJobsUiModel()
//                _jobList.value = allJobs

                _jobSearchState.value = JobListUiState(
                    isLoading = false,
                    jobList = allJobs,
                    searchQuery = "",
                    filterOptions = FilterOptions()
                )

            } catch (e: Exception) {
//                _jobList.value = emptyList()

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

    @RequiresApi(Build.VERSION_CODES.O)
    fun onSearchQueryChanged(query: String) {
//        _searchQuery.value = query
        _jobSearchState.value = _jobSearchState.value.copy(searchQuery = query)
        filterJobs()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateFilterOptions(newFilters: FilterOptions) {
//        _filterOptions.value = newFilters
        _jobSearchState.value = _jobSearchState.value.copy(filterOptions = newFilters)
        filterJobs()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun filterJobs() {
//        val query = searchQuery.value.lowercase()
//        val filters = filterOptions.value
//
//        _jobList.value = allJobs.filter { job ->
//
//            val matchesQuery = query.isEmpty() ||
//                    job.title.lowercase().contains(query) ||
//                    job.company.lowercase().contains(query)
//
//            val matchesMinSalary = filters.minSalary == null || (job.salary_min ?: 0.0) >= filters.minSalary
//            val matchesMaxSalary = filters.maxSalary == null || (job.salary_min ?: 0.0) <= filters.maxSalary
//
//            val jobDate = job.created
//
//            val dateMatches = when (filters.datePosted) {
//                "24h" -> isWithinDays(jobDate, 1)
//                "7d" -> isWithinDays(jobDate, 7)
//                "30d" -> isWithinDays(jobDate, 30)
//                else -> true
//            }
//
//
//            val matchesContractType = filters.contractType.isEmpty() ||
//                    job.contract_type.equals(filters.contractType, ignoreCase = true)
//
//            val matchesContractTime = filters.contractTime.isEmpty() ||
//                    job.contract_time.equals(filters.contractTime, ignoreCase = true)
//
//            matchesQuery && matchesMinSalary && matchesMaxSalary &&
//                    dateMatches && matchesContractType && matchesContractTime
//
//        }

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

//        Log.d("getJobById", "Looking for jobId: $jobId in job list: ${_jobList.value.map { it.id }}")
//
//        val founded = _jobList.value.find { it.id.toString() == jobId.toString() }
//
//        if (founded != null) {
//            Log.d("getJobById", "Job found: $founded")
//        } else {
//            Log.w("getJobById", "No job found with ID: $jobId")
//        }
//
//        return founded

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

//_jobList.value = allJobs.filter { job ->
//    (query.isEmpty() || job.title.lowercase().contains(query) || job.company.lowercase().contains(query)) &&
//            (filters.location.isEmpty() || job.location.lowercase().contains(filters.location.lowercase())) &&
//            (filters.company.isEmpty() || job.company.lowercase().contains(filters.company.lowercase())) &&
//            (filters.minSalary == null || (job.salary ?: 0.0) >= filters.minSalary) &&
//            (filters.maxSalary == null || (job.salary ?: 0.0) <= filters.maxSalary)
//}


//val matchesDate = filters.datePostedInDays?.let { days ->
//    try {
//        val formatter = java.time.format.DateTimeFormatter.ISO_DATE_TIME
//        val jobPostedInstant = java.time.Instant.from(formatter.parse(job.created ?: ""))
//        val jobPostedMillis = jobPostedInstant.toEpochMilli()
//
//        val currentTime = System.currentTimeMillis()
//        val daysInMillis = days * 24 * 60 * 60 * 1000L
//        jobPostedMillis >= (currentTime - daysInMillis)
//    } catch (e: Exception) {
//        true // If parsing fails, don't exclude the job
//    }
//} ?: true
