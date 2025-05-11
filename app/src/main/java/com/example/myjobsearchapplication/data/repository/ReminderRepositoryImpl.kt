package com.example.myjobsearchapplication.data.repository

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.myjobsearchapplication.data.dataSources.local.dao.ReminderDao
import com.example.myjobsearchapplication.data.dataSources.local.database.ReminderDatabase
import com.example.myjobsearchapplication.data.dataSources.local.entity.ReminderEntity
import com.example.myjobsearchapplication.data.workers.NotificationWorker
import com.example.myjobsearchapplication.domain.repository.ReminderRepository
import com.example.myjobsearchapplication.ui.common_components.NotificationHelper
import kotlinx.coroutines.flow.Flow
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
//import kotlin.time.Duration

class ReminderRepositoryImpl(
    private val context: Context,
    private val reminderDao: ReminderDao
) : ReminderRepository {


    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun scheduleReminder(
        reminderTitle: String,
        reminderText: String,
        reminderTime: LocalDateTime,
    ) {
        val id = reminderDao.insertReminder(
            ReminderEntity(
                reminderText = reminderText,
                reminderTime = reminderTime,
                reminderTitle = reminderTitle
            )
        ).toInt()

        val delay = calculateDelay(reminderTime)

        val inputData = workDataOf(
            "title" to reminderTitle,
            "message" to reminderText,
            "id" to id
        )

        val request = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInputData(inputData)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(context.applicationContext)
            .enqueueUniqueWork(
                "reminder_$id",
                ExistingWorkPolicy.REPLACE,
                request
            )



    }

    override suspend fun cancelReminder(reminderId: Int) {
        WorkManager.getInstance(context)
            .cancelUniqueWork("reminder_$reminderId")

        reminderDao.deleteReminder(reminderId)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun calculateDelay(
        reminderTime: LocalDateTime
    ): Long {
        return Duration.between(LocalDateTime.now(), reminderTime).toMillis()
            .coerceAtLeast(0)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun updateReminder(
        reminderId: Int,
        reminderTitle: String,
        reminderText: String,
        reminderTime: LocalDateTime,
    ) {
        val updatedReminder = ReminderEntity(
            id = reminderId,
            reminderTitle = reminderTitle,
            reminderText = reminderText,
            reminderTime = reminderTime
        )

        reminderDao.updateReminder(updatedReminder)

        WorkManager.getInstance(context)
            .cancelUniqueWork("reminder_${updatedReminder.id}")

        val delay = calculateDelay(reminderTime)

        val inputData = workDataOf(
            "title" to reminderTitle,
            "message" to reminderText,
            "id" to updatedReminder.id
        )

        val request = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInputData(inputData)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(context)
            .enqueueUniqueWork(
                "reminder_${updatedReminder.id}",
                ExistingWorkPolicy.REPLACE,
                request
            )
    }

    override fun getAllReminders(): Flow<List<ReminderEntity>> = reminderDao.getAllReminders()
}