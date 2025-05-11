package com.example.myjobsearchapplication.data.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.myjobsearchapplication.data.dataSources.local.dao.ReminderDao
import com.example.myjobsearchapplication.domain.usecase.DeleteReminderUseCase
import com.example.myjobsearchapplication.ui.common_components.NotificationHelper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.time.delay

@HiltWorker
class NotificationWorker @AssistedInject constructor(@Assisted context: Context,
                                                     @Assisted workerParams: WorkerParameters,

): CoroutineWorker(context, workerParams) {



    override suspend fun doWork(): Result {
        val title = inputData.getString("title") ?: return Result.failure()
        val message = inputData.getString("message") ?: return Result.failure()
        val reminderId = inputData.getInt("id", -1)
        if (reminderId == -1) return Result.failure()


        NotificationHelper(applicationContext).showReminderNotification(
            reminderTitle = title,
           reminderText = message,
        )

kotlinx.coroutines.delay(1000)


        return Result.success()
    }

}
