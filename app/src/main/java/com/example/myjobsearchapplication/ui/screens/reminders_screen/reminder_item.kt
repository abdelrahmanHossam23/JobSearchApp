package com.example.myjobsearchapplication.ui.screens.reminders_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myjobsearchapplication.ui.common_components.DeleteAlertDialog
import com.example.myjobsearchapplication.ui.screens.add_reminder_screen.ReminderUiModel
import com.example.myjobsearchapplication.ui.screens.add_reminder_screen.viewmodel.ReminderViewModel
import com.example.myjobsearchapplication.ui.screens.job_search_screen.JobUiModel
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReminderItem(
    modifier: Modifier = Modifier,
    reminderUiModel: ReminderUiModel,
    onReminderItemClicked: (reminderItem: ReminderUiModel) -> Unit,
) {

    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")

    var showDeleteReminderDialog by remember { mutableStateOf(false) }

    val reminderViewModel: ReminderViewModel = hiltViewModel()

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { onReminderItemClicked(reminderUiModel) }
    ){
        Column (
            modifier = Modifier.padding(16.dp)
        ){
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = reminderUiModel.reminderTitle,
                    style = MaterialTheme.typography.titleMedium,

                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "",
                   modifier = Modifier
                       .size(20.dp)
                       .clickable { showDeleteReminderDialog = true }
                )

                if (showDeleteReminderDialog) {
                    DeleteAlertDialog(
                        alertText = "Are you sure you want to delete this Reminder?",
                        onDismiss = { showDeleteReminderDialog = false },
                        onConfirm = {
                            reminderViewModel.deleteReminder(reminderUiModel.id)
                            showDeleteReminderDialog = false
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(5.dp))

            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = reminderUiModel.reminderTime.format(formatter),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
        

    }
}



