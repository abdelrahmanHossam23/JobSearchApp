package com.example.myjobsearchapplication.ui.mapper

import com.example.myjobsearchapplication.domain.model.JobsDomainModel
import com.example.myjobsearchapplication.ui.screens.job_search_screen.JobUiModel

fun JobUiModel.toDomain(): JobsDomainModel = JobsDomainModel(id, title, description, salary_min, salary_max, contract_time, contract_type, created, redirect_url, company, location, category, status)
