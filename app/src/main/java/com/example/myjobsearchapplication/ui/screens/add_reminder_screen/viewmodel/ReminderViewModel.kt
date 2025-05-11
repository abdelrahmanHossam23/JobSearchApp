package com.example.myjobsearchapplication.ui.screens.add_reminder_screen.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myjobsearchapplication.domain.repository.ReminderRepository
import com.example.myjobsearchapplication.domain.usecase.DeleteReminderUseCase
import com.example.myjobsearchapplication.domain.usecase.ScheduleReminderUseCase
import com.example.myjobsearchapplication.domain.usecase.UpdateReminderUseCase
import com.example.myjobsearchapplication.ui.mapper.toReminderListUi
import com.example.myjobsearchapplication.ui.screens.add_reminder_screen.ReminderUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ReminderViewModel @Inject constructor(
    private val scheduleReminderUseCase: ScheduleReminderUseCase,
    private val updateReminderUseCase: UpdateReminderUseCase,
    private val deleteReminderUseCase: DeleteReminderUseCase,
    private val repository: ReminderRepository
) : ViewModel() {

    val reminders: StateFlow<List<ReminderUiModel>> = repository.getAllReminders()
        .map { reminders -> reminders.toReminderListUi() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    private val _selectedTime = mutableStateOf<LocalTime?>(null)
    val selectedTime: State<LocalTime?> = _selectedTime

    private val _selectedDate = mutableStateOf<LocalDate?>(null)
    val selectedDate: State<LocalDate?> = _selectedDate


    init {
        viewModelScope.launch {
            checkAndDeleteExpiredReminders()
        }
    }



    fun setReminderTime(time: LocalTime) {
        _selectedTime.value = time
    }

    fun setReminderDate(date: LocalDate) {
        _selectedDate.value = date
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun scheduleReminder(reminderTitle: String, reminderText: String) {
        viewModelScope.launch {
            val dateTime = LocalDateTime.of(
                selectedDate.value ?: LocalDate.now(),
                selectedTime.value ?: LocalTime.now().plusHours(1)
            )

            scheduleReminderUseCase(
                reminderText = reminderText,
                reminderTime = dateTime,
                reminderTitle = reminderTitle
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateReminder(reminderId: Int, newTitle: String, newText: String) {
        viewModelScope.launch {
            val dateTime = LocalDateTime.of(
                selectedDate.value ?: LocalDate.now(),
                selectedTime.value ?: LocalTime.now().plusHours(1)
            )

            updateReminderUseCase(
                reminderId =reminderId,
                reminderTitle = newTitle,
                reminderText = newText,
                reminderTime = dateTime,
            )
        }
    }

    fun deleteReminder(reminderId: Int) {
        viewModelScope.launch {
            deleteReminderUseCase(
                reminderId =reminderId,
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun checkAndDeleteExpiredReminders() {
//        val now = LocalDateTime.now()
//        reminders.value.forEach { reminder ->
//            if (reminder.reminderTime.isBefore(now)) {
//                deleteReminderUseCase(reminder.id)
//            }
//        }
        repository.getAllReminders().collect { reminderList ->
            val now = LocalDateTime.now()
            reminderList
                .filter { it.reminderTime.isBefore(now) }
                .forEach { expired ->
                    deleteReminderUseCase(expired.id)
                }
        }
    }

//    val visiblePermissionDialogQueue = mutableStateListOf<String>()
//
//    fun dismissDialog(){
//        visiblePermissionDialogQueue.removeAt(0)
//    }
//
//    fun onPermissionResult(permission: String, isGranted: Boolean){
//        if(!isGranted && !visiblePermissionDialogQueue.contains(permission)) {
//            visiblePermissionDialogQueue.add(permission)
//        }
//
//    }
}