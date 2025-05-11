package com.example.myjobsearchapplication.data.dataSources.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.UUID

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val reminderTitle: String,
    val reminderText: String,
    val reminderTime: LocalDateTime
)