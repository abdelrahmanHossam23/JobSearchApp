package com.example.myjobsearchapplication.ui.screens.job_details_screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.DeveloperMode
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreTime
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.WorkHistory
import androidx.compose.material.icons.outlined.Work
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myjobsearchapplication.ui.common_components.JobStatus
import com.example.myjobsearchapplication.ui.common_components.LoadingAnimation
import com.example.myjobsearchapplication.ui.common_components.TopBar
import com.example.myjobsearchapplication.ui.common_components.shimmer.JobDetailsShimmer
import com.example.myjobsearchapplication.ui.screens.job_search_screen.JobUiModel
import com.example.myjobsearchapplication.ui.screens.job_search_screen.viewmodel.JobSearchViewModel
import com.example.myjobsearchapplication.ui.screens.saved_jobs.viewmodel.SavedJobViewModel
import com.example.myjobsearchapplication.ui.screens.track_jobs_screen.viewmodel.TrackedJobsViewModel
import kotlinx.coroutines.flow.map


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobDetailsScreen(
    jobId: Long,
    onBackNavigation: () -> Unit
) {

    val jobSearchViewModel: JobSearchViewModel = hiltViewModel()
    val savedJobViewModel: SavedJobViewModel = hiltViewModel()
    val trackedJobViewModel: TrackedJobsViewModel = hiltViewModel()


    jobSearchViewModel.fetchJobsIfNeeded()
    val jobSearchState  by jobSearchViewModel.jobSearchState.collectAsStateWithLifecycle()
    val jobList = jobSearchState.jobList

    val savedJobs by savedJobViewModel.savedJobs.collectAsState()
    val trackedJobs by trackedJobViewModel.savedJobs.collectAsState()

    val isLoading = remember(jobSearchState.isLoading) {
        jobSearchState.isLoading || jobList.isEmpty()
    }

    val job = remember(jobId, jobSearchState.jobList, savedJobs, trackedJobs){

        trackedJobs.find { it.id == jobId }?.let { trackedJob ->
            jobSearchState.jobList.find { it.id == jobId }?.copy(
                status = trackedJob.status
            ) ?: trackedJob
        }
            ?: savedJobs.find { it.id == jobId }?.let { savedJob ->
                jobSearchState.jobList.find { it.id == jobId }?.copy(
                    status = savedJob.status
                ) ?: savedJob
            }
            ?: jobSearchState.jobList.find { it.id == jobId }
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
                TopBar(
                    topBarTitle = "Job Details",
                    navigationIcon = {
                        IconButton(onClick =  onBackNavigation ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->

            if (isLoading) {
                LoadingAnimation()
            } else {

                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(16.dp)
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Work,
                                    contentDescription = "Job Icon",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = job?.title ?: "N/A",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Business,
                                    contentDescription = "Company Icon",
                                    tint = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(8.dp))

                                Text(text = job?.company ?: "N/A",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
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
                                    text = job?.location ?: "N/A",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            DetailRow(icon = Icons.Default.AttachMoney, text = "Salary: \$${job?.salary_min ?: "N/A"}K")
                            DetailRow(icon = Icons.Default.Handshake, text = "Contract Type: ${job?.contract_type ?: "N/A"}")
                            DetailRow(icon = Icons.Default.WorkHistory, text = "Contract Time: ${job?.contract_time ?: "N/A"}")
                            DetailRow(icon = Icons.Default.Category, text= "Category: ${job?.category ?: "N/A"}")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Description",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = job?.description ?: "No description available",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    val context = LocalContext.current

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(job?.redirect_url))
                                context.startActivity(intent)
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text("Apply", style = MaterialTheme.typography.labelLarge, fontSize = 15.sp, color = MaterialTheme.colorScheme.onSecondary)
                        }

                        Spacer(modifier = Modifier.width(8.dp))


                        val isApplied by rememberUpdatedState(newValue = job?.status.toString() == "APPLIED")

                        Button(
                            onClick = {
                                when{
                                    isApplied -> job?.status = JobStatus.APPLIED
                                    else -> {

                                        job?.let {
                                            job.status = JobStatus.APPLIED
                                            trackedJobViewModel.saveJob(job)
                                            trackedJobViewModel.updateJobStatus(job.id, JobStatus.APPLIED)
                                            savedJobViewModel.updateJobStatus(job.id, JobStatus.APPLIED)
                                        }
                                    }
                                }

                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isApplied) Color.Gray else MaterialTheme.colorScheme.primary
                            ),
                            enabled = !isApplied
                        ) {
                            when{
                                isApplied -> Text("Moved to Tracker!", style = MaterialTheme.typography.labelLarge, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSecondary)
                                else -> Text("Mark as Applied", style = MaterialTheme.typography.labelLarge, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSecondary)
                            }

                        }
                    }
                }
            }

        }
    }
}

@Composable
fun DetailRow(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        )
    }
}