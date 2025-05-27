package com.example.myjobsearchapplication.data.dataSources.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myjobsearchapplication.ui.common_components.JobStatus

@Entity(tableName = "saved_jobs")
data class JobEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val description: String,
    val salary_min: Double?,
    val salary_max: Double?,
    val contract_time: String?,
    val contract_type: String?,
    val created: String,
    val redirect_url: String,
    val company: String,
    val location: String,
    val category: String,
    var status: JobStatus
)


