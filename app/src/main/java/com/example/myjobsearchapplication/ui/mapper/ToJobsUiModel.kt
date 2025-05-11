package com.example.myjobsearchapplication.ui.mapper

import com.example.myjobsearchapplication.domain.model.JobsDomainModel
import com.example.myjobsearchapplication.ui.common_components.JobStatus
import com.example.myjobsearchapplication.ui.screens.job_search_screen.JobUiModel


fun List<JobsDomainModel>.toJobsUiModel(): List<JobUiModel> {
    return this.map { item ->
        JobUiModel(
            id = item.id,
            title = item.title,
         location =  item.location,
         company = item.company,
         salary_max = item.salary_max,
            salary_min = item.salary_min,
            contract_time = item.contract_time,
            contract_type = item.contract_type,
            created = item.created,
            category = item.category,
            description = item.description,
            redirect_url = item.redirect_url,
            status = JobStatus.NOT_APPLIED
        )
    }
}

fun JobsDomainModel.toUi(): JobUiModel = JobUiModel(id, title, location, company, salary_min, salary_max, contract_time, contract_type, created, category, description, redirect_url, status)

