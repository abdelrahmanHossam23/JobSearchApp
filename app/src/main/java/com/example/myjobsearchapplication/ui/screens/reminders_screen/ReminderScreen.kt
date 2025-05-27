package com.example.myjobsearchapplication.ui.screens.reminders_screen

import android.content.Intent
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.QueryStats
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myjobsearchapplication.MainActivity
import com.example.myjobsearchapplication.ui.common_components.BottomBar
import com.example.myjobsearchapplication.ui.common_components.DeleteAlertDialog
import com.example.myjobsearchapplication.ui.common_components.TopBar
import com.example.myjobsearchapplication.ui.mapper.toUi
import com.example.myjobsearchapplication.ui.screens.add_reminder_screen.ReminderUiModel
import com.example.myjobsearchapplication.ui.screens.add_reminder_screen.viewmodel.ReminderViewModel
import com.example.myjobsearchapplication.ui.screens.job_search_screen.JobUiModel
//import com.example.myjobsearchapplication.ui.screens.add_reminder_screen.preview.fakeReminderList
import com.example.myjobsearchapplication.ui.screens.saved_jobs.viewmodel.SavedJobViewModel
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
    fun ReminderScreen(
    onAddReminder: () -> Unit,
    navController: NavController,
    onReminderItemClicked: (reminderItem: ReminderUiModel) -> Unit,
    ) {

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val viewModel: ReminderViewModel = hiltViewModel()

    val reminders by viewModel.reminders.collectAsState()

    var showDeleteAllRemindersDialog by remember { mutableStateOf(false) }



    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        var expanded by remember { mutableStateOf(false) }

        Scaffold(
            modifier = Modifier.fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopBar(
                    topBarTitle = "Reminders",
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
                                    showDeleteAllRemindersDialog = true
                                    expanded = false
                                }
                            )
                        }

                        if (showDeleteAllRemindersDialog) {
                            DeleteAlertDialog(
                                alertText = "Are you sure you want to delete all reminders?",
                                onDismiss = { showDeleteAllRemindersDialog = false },
                                onConfirm = {
                                    viewModel.deleteAllReminders()
                                    showDeleteAllRemindersDialog = false
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
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        onAddReminder()
                    },
                    containerColor = MaterialTheme.colorScheme.primary

                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
        ) { innerPadding ->

            val visibleItems = remember { mutableStateListOf<Boolean>() }
            LaunchedEffect(reminders.size) {
                visibleItems.clear()
                visibleItems.addAll(List(reminders.size) { false })

                reminders.indices.forEach { index ->
                    delay(100L * index)
                    visibleItems[index] = true
                }
            }


            LazyColumn (
                modifier = Modifier.fillMaxSize()
                    .padding(innerPadding)
                    .padding(top = 16.dp)
            ){
                itemsIndexed(reminders){  index, reminderItem ->


                    AnimatedVisibility(
                        visible = visibleItems.getOrNull(index) ?: false,
                        enter = fadeIn() + slideInVertically { it / 2 },
                        exit = fadeOut() + slideOutVertically { it / 2 }
                    ){
                        key(reminderItem.id){
                            ReminderItem(
                                reminderUiModel = reminderItem,
                                onReminderItemClicked = {
                                    onReminderItemClicked(it)
                                }
                            )

                        }

                    }


                }
            }
        }
    }
}