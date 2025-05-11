package com.example.myjobsearchapplication.ui.screens.add_reminder_screen

import java.time.LocalDateTime

data class ReminderUiModel(
    val id: Int,
    val reminderTitle: String,
    val reminderText: String,
    val reminderTime: LocalDateTime
)
