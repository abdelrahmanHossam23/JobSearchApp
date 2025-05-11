package com.example.myjobsearchapplication.ui.screens.add_reminder_screen

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myjobsearchapplication.ui.screens.add_reminder_screen.viewmodel.ReminderViewModel
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReminderSetupScreen(
    viewModel: ReminderViewModel = hiltViewModel(),
    reminderId: Int? = null,
    onBackNavigation: () -> Unit
) {
//    val reminderListViewModel: ReminderListViewModel = hiltViewModel()
    val reminders by viewModel.reminders.collectAsState()
    val reminder = reminders.find { it.id == reminderId}

    var showTimePicker by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")
    var reminderTitle by remember (reminder?.id) {
        mutableStateOf(reminder?.reminderTitle ?: "")
    }

    var reminderText by remember(reminder?.id) {
        mutableStateOf(reminder?.reminderText ?: "")
    }

    val context = LocalContext.current

    LaunchedEffect(reminder) {
        reminder?.let {
            if (viewModel.selectedDate.value == null) {
                viewModel.setReminderDate(it.reminderTime.toLocalDate())
            }
            if (viewModel.selectedTime.value == null) {
                viewModel.setReminderTime(it.reminderTime.toLocalTime())
            }
        }
    }


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Add Reminder",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        scrolledContainerColor = MaterialTheme.colorScheme.tertiary
                    ),
                    navigationIcon = {
                        IconButton(onClick = onBackNavigation) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    actions = {
//                        IconButton(onClick = { /* Handle search */ }) {
//                            Icon(
//                                imageVector = Icons.Default.Search,
//                                contentDescription = "Search",
//                                tint = MaterialTheme.colorScheme.onPrimary
//                            )
//                        }
                        IconButton(onClick = { /* Handle more options */ }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "More",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            }
        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .padding(top = 24.dp)
            ) {

                OutlinedTextField(
                    value = reminderTitle,
                    onValueChange = { reminderTitle = it },
                    label = { Text("Reminder Title") },

                    modifier = Modifier
                        .fillMaxWidth()
//                .padding(16.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = reminderText,
                    onValueChange = { reminderText = it },
                    label = { Text("Reminder Text") },

                    modifier = Modifier
                        .fillMaxWidth()
//                .padding(16.dp)
                )

                Spacer(modifier = Modifier.height(28.dp))

                // Current selection display
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text =
                        "Date: ${viewModel.selectedDate.value?.format(dateFormatter) ?: "Not set"}",
                        modifier = Modifier.weight(1f)
                    )
                    Button(onClick = { showDatePicker = true }) {
                        Text("Change Date")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text =
                        "Time: ${viewModel.selectedTime.value?.format(timeFormatter) ?: "Not set"}",
                        modifier = Modifier.weight(1f)
                    )
                    Button(onClick = { showTimePicker = true }) {
                        Text("Change Time")
                    }
                }



                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {

                            if (reminder != null) {
                                viewModel.updateReminder(
                                    reminderId = reminder.id,
                                    newTitle = reminderTitle,
                                    newText = reminderText,
                                )
                                Toast.makeText(context, "Reminder Updated Successfully", Toast.LENGTH_SHORT).show()
                                onBackNavigation()
                            } else {
                                viewModel.scheduleReminder(
                                    reminderText = reminderText,
                                    reminderTitle = reminderTitle
                                )
                                Toast.makeText(context, "Reminder Scheduled Successfully", Toast.LENGTH_SHORT).show()
                                onBackNavigation()
                            }

                    },
                    enabled = viewModel.selectedTime.value != null && reminderTitle.isNotBlank() && reminderText.isNotBlank(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Set Reminder")
                }

                // Dialogs
                if (showDatePicker) {
                    val dateState = rememberDatePickerState()
                    DatePickerDialog(
                        onDismissRequest = { showDatePicker = false },
                        confirmButton = {
                            Button(
                                onClick = {
                                    dateState.selectedDateMillis?.let {
                                        viewModel.setReminderDate(
                                            Instant.ofEpochMilli(it)
                                                .atZone(ZoneId.systemDefault())
                                                .toLocalDate()
                                        )
                                    }
                                    showDatePicker = false
                                }
                            ) {
                                Text("OK")
                            }
                        }
                    ) {
                        DatePicker(state = dateState)
                    }
                }

                if (showTimePicker) {
                    TimeSelectorDialog(
                        initialTime = viewModel.selectedTime.value ?: LocalTime.now().plusHours(1),
                        onTimeSelected = { time ->
                            viewModel.setReminderTime(time)
                        },
                        onDismiss = { showTimePicker = false }
                    )
                }
            }
        }

    }

}

//@Preview
//@Composable
//private fun AddReminderPrev() {
//    ReminderSetupScreen()
//}