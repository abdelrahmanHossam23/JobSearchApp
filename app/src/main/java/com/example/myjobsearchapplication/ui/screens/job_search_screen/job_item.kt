package com.example.myjobsearchapplication.ui.screens.job_search_screen

import android.content.Intent
import android.net.Uri
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
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.QueryStats
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.icons.outlined.Work
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myjobsearchapplication.R
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myjobsearchapplication.ui.common_components.BottomNavigationItem
import com.example.myjobsearchapplication.ui.common_components.JobStatus
import com.example.myjobsearchapplication.ui.screens.saved_jobs.viewmodel.SavedJobViewModel
import com.example.myjobsearchapplication.ui.screens.track_jobs_screen.HandleDeletingTrackedJob
import com.example.myjobsearchapplication.ui.screens.track_jobs_screen.viewmodel.TrackedJobsViewModel
import com.example.myjobsearchapplication.ui.theme.Cyan


@Composable
fun JobItem(
    modifier: Modifier = Modifier,
    jobUiModel: JobUiModel,
    onJobItemClicked: (jobItem: JobUiModel) -> Unit,
    onSave: () -> Unit,
    onDelete: () -> Unit,
    isSaved: Boolean,
    jobSearchJobItem: Boolean,
    onTrackJob: () -> Unit
) {



    val trackedJobViewModel: TrackedJobsViewModel = hiltViewModel()
    val savedJobViewModel: SavedJobViewModel = hiltViewModel()

    var showUnSaveDialog by remember { mutableStateOf(false) }

    val isApplied by rememberUpdatedState(newValue = jobUiModel.status.toString() == "APPLIED")
    val context = LocalContext.current

    val isSavedUI by savedJobViewModel.isJobSavedState(jobUiModel.id).collectAsState()

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
//        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { onJobItemClicked(jobUiModel) },

    ){
        Column (
            modifier = Modifier.padding(16.dp)
//                .fillMaxWidth()
        ){
            Row (
//            modifier = Modifier.padding(bottom = 5.dp)
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
//                    imageVector = ImageVector.vectorResource(id = R.drawable.work),
                    imageVector = Icons.Outlined.Work,
                    contentDescription = "Job Icon",
//                    tint = MaterialTheme.colorScheme.primary,
//                    tint = Cyan,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))

                Text(text = jobUiModel.title,
                    style = MaterialTheme.typography.titleMedium,
//                    color = MaterialTheme.colorScheme.onSurface,

                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
//                    color = MaterialTheme.colorScheme.primary,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                // //
                Icon(
//                    imageVector = ImageVector.vectorResource(id = R.drawable.work),
                    imageVector = if (isSavedUI) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                    contentDescription = "Save Icon",
//                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                        .clickable {
                            if (isSaved) {
                                showUnSaveDialog = true

                            } else{
                                onSave()
                            }
                        }
                )

                if (showUnSaveDialog) {
                    HandleUnSaveJob(
                        onDismiss = { showUnSaveDialog = false },
                        onConfirm = {
                            onDelete()
                            showUnSaveDialog = false
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
                    text = jobUiModel.company ,
                    style = MaterialTheme.typography.titleSmall,

                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),

                    )
            }

            Spacer(modifier = Modifier.height(5.dp))


            // Location
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
                    text = jobUiModel.location,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

//            Text(
//                text = "Salary: ${jobUiModel.salary}",
//                style = MaterialTheme.typography.bodyMedium,
//                color = MaterialTheme.colorScheme.onSurface
//            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.dollar),
                    contentDescription = "Salary Icon",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Salary: \$${jobUiModel.salary_min}K",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            Row (
//            modifier = Modifier.padding(2.dp)
            ){
//                Button(
//                    onClick = {},
//                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
//                ) {
//                    Text("View", style = MaterialTheme.typography.labelLarge)
//                }
                Spacer(modifier = Modifier.width(5.dp))




                Button(
                    onClick = {
                        when{
                            jobSearchJobItem -> {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(jobUiModel.redirect_url))
                                context.startActivity(intent)
                            }
                            isApplied -> jobUiModel?.status = JobStatus.APPLIED
                            else -> {
                                jobUiModel?.status = JobStatus.APPLIED
                                trackedJobViewModel.updateJobStatus(jobUiModel.id, JobStatus.APPLIED)
                                savedJobViewModel.updateJobStatus(jobUiModel.id, JobStatus.APPLIED)
                                onTrackJob()
                            }
                        }

                    },
//                    modifier = Modifier.weight(1f),
                    colors = when{
                        jobSearchJobItem -> ButtonDefaults.buttonColors(
//                            contentColor = MaterialTheme.colorScheme.primary
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                        else -> ButtonDefaults.buttonColors(
//                            contentColor = MaterialTheme.colorScheme.primary
                            containerColor = if (isApplied) Color.Gray else MaterialTheme.colorScheme.primary
                        )
                    },
                    enabled = when{
                        jobSearchJobItem -> true
                        else -> !isApplied
                    }

                ) {
                    when{
                        jobSearchJobItem -> Text("Apply", style = MaterialTheme.typography.labelLarge)
                        isApplied -> Text("Moved to Tracker!", style = MaterialTheme.typography.labelLarge)
                        else -> Text("Mark as Applied", style = MaterialTheme.typography.labelLarge)
                    }

                }
            }
        }
    }

}


@Composable
fun HandleUnSaveJob(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Confirm UnSave") },
        text = { Text("Are you sure you want to UnSave this Job?") },
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