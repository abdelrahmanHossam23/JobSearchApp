package com.example.myjobsearchapplication.data.mapper

import com.example.myjobsearchapplication.data.dataSources.remote.retrofit.datamodel.JobDataModel
import com.example.myjobsearchapplication.domain.model.JobsDomainModel
import com.example.myjobsearchapplication.ui.common_components.JobStatus

fun JobDataModel.toJobDomainModel(): List<JobsDomainModel> {
    return this.results.map { item ->
        JobsDomainModel(
             id = item.id,
             title =  item.title,
             description =  item.description,
             salary_min =  item.salary_min,
             salary_max =  item.salary_max,
             contract_time =  item.contract_time,
            contract_type= item.contract_type,
             created =  item.created,
             redirect_url =  item.redirect_url,
             company =  item.company.display_name,
             location =  item.location.display_name,
             category = item.category.label,
            status = JobStatus.NOT_APPLIED
        )
    }
}