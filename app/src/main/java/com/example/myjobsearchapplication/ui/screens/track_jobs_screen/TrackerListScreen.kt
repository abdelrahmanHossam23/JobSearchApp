package com.example.myjobsearchapplication.ui.screens.track_jobs_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.QueryStats
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myjobsearchapplication.ui.common_components.BottomBar
import com.example.myjobsearchapplication.ui.screens.job_search_screen.JobItem

import android.content.Intent
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.QueryStats
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myjobsearchapplication.ui.common_components.BottomBar
import com.example.myjobsearchapplication.ui.common_components.DeleteAlertDialog
import com.example.myjobsearchapplication.ui.common_components.JobStatus
import com.example.myjobsearchapplication.ui.common_components.TopBar
import com.example.myjobsearchapplication.ui.screens.job_search_screen.JobUiModel
import com.example.myjobsearchapplication.ui.screens.job_search_screen.searchBar.SearchViewModel
import com.example.myjobsearchapplication.ui.screens.job_search_screen.searchBar.SearchWidgetState
import com.example.myjobsearchapplication.ui.screens.job_search_screen.viewmodel.FilterOptions
import com.example.myjobsearchapplication.ui.screens.saved_jobs.viewmodel.SavedJobViewModel
import com.example.myjobsearchapplication.ui.screens.track_jobs_screen.viewmodel.TrackedJobsViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackerListScreen(
    onJobItemClicked: (jobItem: JobUiModel) -> Unit,
    navController: NavController
) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val trackedJobViewModel: TrackedJobsViewModel = hiltViewModel()
    val savedJobViewModel: SavedJobViewModel = hiltViewModel()


    val jobList by trackedJobViewModel.savedJobs.collectAsStateWithLifecycle()

    var showDeleteAllTrackedJobsDialog by remember { mutableStateOf(false) }


    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        var expanded by remember { mutableStateOf(false) }

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopBar(
                    topBarTitle = "Tracked Jobs",
                    topBarActions = {
                        IconButton(
                            onClick = {expanded = true}
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Clear All") },
                                onClick = {
                                    showDeleteAllTrackedJobsDialog = true
                                    expanded = false
                                }
                            )
                        }

                        if (showDeleteAllTrackedJobsDialog) {
                            DeleteAlertDialog(
                                alertText = "Are you sure you want to delete all tracked Job?",
                                onDismiss = { showDeleteAllTrackedJobsDialog = false },
                                onConfirm = {
                                    jobList.forEach { job ->
                                        savedJobViewModel.updateJobStatus(job.id, JobStatus.NOT_APPLIED)
                                    }
                                    trackedJobViewModel.deleteAllJobs()
                                    showDeleteAllTrackedJobsDialog = false
                                }
                            )
                        }
                    }
                )
                     },
            bottomBar = {
                BottomBar(
                    currentRoute = currentRoute,
                    navController = navController
                )
            }
        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {

                val visibleItems = remember { mutableStateListOf<Boolean>() }

                LaunchedEffect(jobList.size) {
                    visibleItems.clear()
                    visibleItems.addAll(List(jobList.size) { false })

                    jobList.indices.forEach { index ->
                        delay(100L * index)
                        visibleItems[index] = true
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    itemsIndexed(jobList) { index, jobItem ->


                        AnimatedVisibility(
                            visible = visibleItems.getOrNull(index) ?: false,
                            enter = fadeIn() + slideInVertically { it / 2 },
                            exit = fadeOut() + slideOutVertically { it / 2 }
                        ){
                            TrackJobCard(
                                job = jobItem,
                                onJobItemClicked = {
                                    onJobItemClicked(it)
                                }
                            )
                        }

                    }
                }
            }

        }
    }


}
