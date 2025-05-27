package com.example.myjobsearchapplication.ui.screens.track_jobs_screen

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material.icons.filled.ArrowDropUp
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myjobsearchapplication.ui.common_components.DeleteAlertDialog
import com.example.myjobsearchapplication.ui.common_components.JobStatus
import com.example.myjobsearchapplication.ui.screens.job_search_screen.JobUiModel
import com.example.myjobsearchapplication.ui.screens.saved_jobs.viewmodel.SavedJobViewModel
import com.example.myjobsearchapplication.ui.screens.track_jobs_screen.viewmodel.TrackedJobsViewModel
import com.example.myjobsearchapplication.ui.theme.Cloud
import com.example.myjobsearchapplication.ui.theme.DarkCeruleanBlue
import com.example.myjobsearchapplication.ui.theme.MidnightBlue
import com.example.myjobsearchapplication.ui.theme.SpaceCadetBlue


@Composable
fun TrackJobCard(
    job: JobUiModel,
    onJobItemClicked: (jobItem: JobUiModel) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedStatus by remember { mutableStateOf(job.status) }

    val trackedJobViewModel: TrackedJobsViewModel = hiltViewModel()
    val savedJobViewModel: SavedJobViewModel = hiltViewModel()

    var showDeleteTrackedJobDialog by remember { mutableStateOf(false) }

    val statusColors = mapOf(
        JobStatus.APPLIED to Color(0xFF1152B9),
        JobStatus.INTERVIEW to Color(0xFFC59605),
        JobStatus.OFFER to Color(0xFF009688),
        JobStatus.ACCEPTED to Color(0xFF126903),
        JobStatus.REJECTED to Color(0xFF910E18)
    )

    var isPressed by remember { mutableStateOf(false) }
    val cardScale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "CardScale"
    )

    val cardElevation by animateDpAsState(
        targetValue = if (isPressed) 2.dp else 4.dp,
        animationSpec = tween(durationMillis = 100),
        label = "CardElevation"
    )

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .scale(cardScale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { isPressed = true; tryAwaitRelease(); isPressed = false },
                    onTap = { onJobItemClicked(job) }
                )
            },
        elevation = CardDefaults.cardElevation(defaultElevation = cardElevation)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    imageVector = Icons.Outlined.Work,
                    contentDescription = "Job Icon",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))

                Text(text = job.title,
                    style = MaterialTheme.typography.titleMedium,

                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "",
                    modifier = Modifier.size(20.dp).clickable { showDeleteTrackedJobDialog = true }
                )

                if (showDeleteTrackedJobDialog) {
                    DeleteAlertDialog(
                        alertText = "Are you sure you want to delete this Job?",
                        onDismiss = { showDeleteTrackedJobDialog = false },
                        onConfirm = {
                            trackedJobViewModel.deleteJob(job.id)
                            savedJobViewModel.updateJobStatus(job.id, JobStatus.NOT_APPLIED)
                            showDeleteTrackedJobDialog = false
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


            Spacer(modifier = Modifier.height(8.dp))

            Box {
                OutlinedButton(
                    onClick = { expanded = true },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.3f),
                    ),
                    border = BorderStroke(
                        1.dp,
                        if (expanded) MaterialTheme.colorScheme.primary else DarkCeruleanBlue
                    )
                ) {
                    Text(selectedStatus.toString(), color = statusColors[selectedStatus] ?: Color.Black)
                    Icon(if(expanded)Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary)
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