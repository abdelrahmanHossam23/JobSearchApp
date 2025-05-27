package com.example.myjobsearchapplication.ui.screens.add_reminder_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeSelectorDialog(
    initialTime: LocalTime = LocalTime.now(),
    onTimeSelected: (LocalTime) -> Unit,
    onDismiss: () -> Unit
) {
    val timeState = rememberTimePickerState(
        initialHour = initialTime.hour,
        initialMinute = initialTime.minute,
        is24Hour = false
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Reminder Time", style = MaterialTheme.typography.headlineSmall) },
        text = {
            Column(modifier = Modifier.padding(16.dp)) {
                TimePicker(state = timeState,
                    colors = TimePickerDefaults.colors(
                    clockDialColor = MaterialTheme.colorScheme.background,
                    clockDialSelectedContentColor = MaterialTheme.colorScheme.onSecondary,
                    clockDialUnselectedContentColor = MaterialTheme.colorScheme.primary,
                    selectorColor = MaterialTheme.colorScheme.primary,
                    containerColor = MaterialTheme.colorScheme.primary,
                   periodSelectorBorderColor = MaterialTheme.colorScheme.primary,
                   periodSelectorSelectedContainerColor = MaterialTheme.colorScheme.primary,
                   periodSelectorUnselectedContainerColor = MaterialTheme.colorScheme.background,
                   periodSelectorSelectedContentColor = MaterialTheme.colorScheme.onSecondary,
                   periodSelectorUnselectedContentColor = MaterialTheme.colorScheme.primary,
                   timeSelectorSelectedContainerColor = MaterialTheme.colorScheme.primary,
                   timeSelectorUnselectedContainerColor = MaterialTheme.colorScheme.background,
                   timeSelectorSelectedContentColor = MaterialTheme.colorScheme.onSecondary,
                   timeSelectorUnselectedContentColor = MaterialTheme.colorScheme.primary,
                )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Selected: ${timeState.hour}:${timeState.minute} ${if (timeState.hour < 12) "AM" else "PM"}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onTimeSelected(LocalTime.of(timeState.hour, timeState.minute))
                    onDismiss()
                }
            ) {
                Text("Confirm", color = MaterialTheme.colorScheme.onSecondary)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}