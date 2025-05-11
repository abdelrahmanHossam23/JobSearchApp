package com.example.myjobsearchapplication.ui.screens.job_search_screen

import com.example.myjobsearchapplication.ui.model.CustomExceptionUiModel
import com.example.myjobsearchapplication.ui.screens.job_search_screen.viewmodel.FilterOptions

data class JobListUiState(
    val isLoading:Boolean = false,
    val isError:Boolean = false,
    val errorMessage: CustomExceptionUiModel? = null,
    val jobList: List<JobUiModel> = emptyList(),
    val searchQuery: String = "",
    val filterOptions: FilterOptions = FilterOptions()
)