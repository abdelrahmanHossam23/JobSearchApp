package com.example.myjobsearchapplication.ui.screens.track_jobs_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.icons.outlined.Work
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myjobsearchapplication.ui.common_components.JobStatus
import com.example.myjobsearchapplication.ui.screens.job_search_screen.JobUiModel
import com.example.myjobsearchapplication.ui.screens.track_jobs_screen.viewmodel.TrackedJobsViewModel


@Composable
fun TrackJobCard(
    job: JobUiModel,
    onJobItemClicked: (jobItem: JobUiModel) -> Unit,
    onTrack: () -> Unit,
//    onUpdateJobStatus: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedStatus by remember { mutableStateOf(job.status) }

    val trackedJobViewModel: TrackedJobsViewModel = hiltViewModel()

    var showDeleteDialog by remember { mutableStateOf(false) }

    val statusColors = mapOf(
        JobStatus.APPLIED to Color(0xFF0D47A1),
        JobStatus.INTERVIEW to Color(0xFFC59605),
        JobStatus.OFFER to Color(0xFF126903),
        JobStatus.ACCEPTED to Color(0xFF1A237E),
        JobStatus.REJECTED to Color(0xFFE00716)
    )


    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
            .padding(10.dp)
            .clickable { onJobItemClicked(job) }
        ,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    imageVector = Icons.Outlined.Work,
                    contentDescription = "Job Icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))

                Text(text = job.title,
                    style = MaterialTheme.typography.titleMedium,

                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "",
                    modifier = Modifier.size(20.dp).clickable { showDeleteDialog = true }
                )

                if (showDeleteDialog) {
                    HandleDeletingTrackedJob(
                        onDismiss = { showDeleteDialog = false },
                        onConfirm = {
                            trackedJobViewModel.deleteJob(job.id)
                            showDeleteDialog = false
                        }
                    )
                }

            }
            Spacer(modifier = Modifier.height(5.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Business,
                    contentDescription = "Location Icon",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = job.company ,
                    style = MaterialTheme.typography.titleSmall,

                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),

                    )
            }

            Spacer(modifier = Modifier.height(5.dp))


            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location Icon",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = job.location,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

//            Text(job.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
//            Text(job.company)
//            Text(job.location, color = Color.Gray)

            Spacer(modifier = Modifier.height(8.dp))

            Box {
                OutlinedButton(
                    onClick = { expanded = true },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = statusColors[selectedStatus]?.copy(alpha = 0.2f) ?: Color.LightGray
                    )
                ) {
                    Text(selectedStatus.toString(), color = statusColors[selectedStatus] ?: Color.Black)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    listOf(JobStatus.APPLIED,JobStatus.INTERVIEW, JobStatus.OFFER, JobStatus.REJECTED, JobStatus.ACCEPTED).forEach { status ->
                        DropdownMenuItem(
                            text = { Text(status.toString()) },
                            onClick = {
                                selectedStatus = status
//                                onUpdateJobStatus()
                                trackedJobViewModel.updateJobStatus(job.id, status)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun HandleDeletingTrackedJob(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Confirm Delete") },
        text = { Text("Are you sure you want to delete this Job?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
