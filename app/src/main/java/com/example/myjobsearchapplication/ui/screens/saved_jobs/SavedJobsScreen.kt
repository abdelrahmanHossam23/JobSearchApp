package com.example.myjobsearchapplication.ui.screens.saved_jobs

import com.example.myjobsearchapplication.ui.screens.job_search_screen.JobItem
import com.example.myjobsearchapplication.ui.screens.job_search_screen.JobUiModel
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myjobsearchapplication.ui.common_components.BottomBar
import com.example.myjobsearchapplication.ui.common_components.DeleteAlertDialog
import com.example.myjobsearchapplication.ui.common_components.TopBar
import com.example.myjobsearchapplication.ui.screens.saved_jobs.viewmodel.SavedJobViewModel
import com.example.myjobsearchapplication.ui.screens.track_jobs_screen.viewmodel.TrackedJobsViewModel
import kotlinx.coroutines.delay


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedJobsScreen(
    onJobItemClicked: (jobItem: JobUiModel) -> Unit,
    navController: NavController
) {

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route


    val savedJobViewModel: SavedJobViewModel = hiltViewModel()

    val trackedJobViewModel: TrackedJobsViewModel = hiltViewModel()

    val jobList by savedJobViewModel.savedJobs.collectAsStateWithLifecycle()

    var showDeleteAllSavedJobsDialog by remember { mutableStateOf(false) }





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
                    topBarTitle = "Saved Jobs",
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
                                    showDeleteAllSavedJobsDialog = true
                                    expanded = false
                                }
                            )
                        }

                        if (showDeleteAllSavedJobsDialog) {
                            DeleteAlertDialog(
                                alertText = "Are you sure you want to delete all saved Job?",
                                onDismiss = { showDeleteAllSavedJobsDialog = false },
                                onConfirm = {
                                    savedJobViewModel.deleteAllJobs()
                                    showDeleteAllSavedJobsDialog = false
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

            val visibleItems = remember { mutableStateListOf<Boolean>() }

            LaunchedEffect(jobList.size) {
                visibleItems.clear()
                visibleItems.addAll(List(jobList.size) { false })

                jobList.indices.forEach { index ->
                    delay(100L * index)
                    visibleItems[index] = true
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {

                val listState = rememberLazyListState()

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    state = listState,
                ) {
                    itemsIndexed(jobList) {index, jobItem ->

                        val onSave by rememberUpdatedState { savedJobViewModel.saveJob(jobItem) }
                        val onDelete by rememberUpdatedState { savedJobViewModel.deleteJob(jobItem.id) }
                        val onTrack by rememberUpdatedState { trackedJobViewModel.saveJob(jobItem) }



                        AnimatedVisibility(
                            visible = visibleItems.getOrNull(index) ?: false,
                            enter = fadeIn() + slideInVertically { it / 2 },
                            exit = fadeOut() + slideOutVertically { it / 2 }
                        ){
                            key(jobItem.id){
                                JobItem(
                                    jobUiModel = jobItem,
                                    onJobItemClicked = {
                                        onJobItemClicked(it)
                                    },
                                    onSave = onSave,
                                    onDelete = onDelete,
                                    jobSearchJobItem = false,
                                    onTrackJob = onTrack
                                )
                            }

                        }


                    }
                }
            }

        }
    }
}


