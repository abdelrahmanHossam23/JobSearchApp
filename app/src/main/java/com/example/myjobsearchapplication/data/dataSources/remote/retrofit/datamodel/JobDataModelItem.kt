package com.example.myjobsearchapplication.data.dataSources.remote.retrofit.datamodel

import androidx.compose.runtime.Immutable

@Immutable
data class JobDataModelItem(
    val id: Long,
    val title: String,
    val description: String,
    val salary_min: Double?,
    val salary_max: Double?,
    val contract_time: String,
    val contract_type: String?,
    val created: String,
    val redirect_url: String,
    val company: Company,
    val location: Location,
    val category: Category
)
