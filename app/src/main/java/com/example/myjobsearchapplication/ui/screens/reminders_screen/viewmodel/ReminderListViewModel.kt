//package com.example.myjobsearchapplication.ui.screens.reminders_screen.viewmodel
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.myjobsearchapplication.data.dataSources.local.entity.ReminderEntity
//import com.example.myjobsearchapplication.domain.repository.ReminderRepository
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.SharingStarted
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.stateIn
//import javax.inject.Inject
//import com.example.myjobsearchapplication.ui.mapper.toReminderListUi
//import com.example.myjobsearchapplication.ui.screens.add_reminder_screen.ReminderUiModel
//import kotlinx.coroutines.flow.map
//
//@HiltViewModel
//class ReminderListViewModel @Inject constructor(
//    private val repository: ReminderRepository
//) : ViewModel() {
//    val reminders: StateFlow<List<ReminderUiModel>> = repository.getAllReminders()
//        .map { reminders -> reminders.toReminderListUi() }
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
//
//}
