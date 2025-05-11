package com.example.myjobsearchapplication.domain.repository

import com.example.myjobsearchapplication.data.dataSources.local.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface ReminderRepository {
    suspend fun scheduleReminder(
        reminderTitle: String,
        reminderText: String,
        reminderTime: LocalDateTime,
    )

    suspend fun updateReminder(
        reminderId: Int,
        reminderTitle: String,
        reminderText: String,
        reminderTime: LocalDateTime,
    )

    suspend fun cancelReminder(reminderId: Int)
    fun getAllReminders(): Flow<List<ReminderEntity>>
}