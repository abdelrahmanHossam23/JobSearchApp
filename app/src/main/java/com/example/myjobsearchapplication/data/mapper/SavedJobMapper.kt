package com.example.myjobsearchapplication.data.mapper

import com.example.myjobsearchapplication.data.dataSources.local.entity.JobEntity
import com.example.myjobsearchapplication.domain.model.JobsDomainModel

fun JobEntity.toDomain(): JobsDomainModel = JobsDomainModel(id, title, description, salary_min, salary_max, contract_time, contract_type, created, redirect_url, company, location, category, status)

fun JobsDomainModel.toEntity(): JobEntity = JobEntity(id, title, description, salary_min, salary_max, contract_time, contract_type, created, redirect_url, company, location, category, status)


