package com.example.myjobsearchapplication.domain.usecase

import com.example.myjobsearchapplication.domain.repository.ReminderRepository
import java.time.LocalDateTime
import javax.inject.Inject

class ScheduleReminderUseCase @Inject constructor(
    private val repository: ReminderRepository
) {
    suspend operator fun invoke(
        reminderText: String,
        reminderTime: LocalDateTime,
        reminderTitle: String
    ) = repository.scheduleReminder(
        reminderText = reminderText ,
        reminderTime = reminderTime,
        reminderTitle = reminderTitle

    )
}

class UpdateReminderUseCase @Inject constructor(
    private val repository: ReminderRepository
) {
    suspend operator fun invoke(
        reminderId: Int,
        reminderText: String,
        reminderTime: LocalDateTime,
        reminderTitle: String
    ) = repository.updateReminder(
        reminderId = reminderId,
        reminderText = reminderText ,
        reminderTime = reminderTime,
        reminderTitle = reminderTitle

    )
}

class DeleteReminderUseCase @Inject constructor(
    private val repository: ReminderRepository
) {
    suspend operator fun invoke(
        reminderId: Int,
    ) = repository.cancelReminder(
        reminderId = reminderId,
    )
}