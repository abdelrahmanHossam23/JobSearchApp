package com.example.myjobsearchapplication.ui.screens.job_search_screen

import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.QueryStats
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myjobsearchapplication.SecondActivity
import com.example.myjobsearchapplication.ui.common_components.BottomBar
import com.example.myjobsearchapplication.ui.common_components.ErrorSection
import com.example.myjobsearchapplication.ui.common_components.shimmer.AnimateShimmerJobList
import com.example.myjobsearchapplication.ui.screens.job_search_screen.searchBar.MainAppBar
import com.example.myjobsearchapplication.ui.screens.job_search_screen.searchBar.SearchAppBar2
import com.example.myjobsearchapplication.ui.screens.job_search_screen.searchBar.SearchViewModel
import com.example.myjobsearchapplication.ui.screens.job_search_screen.searchBar.SearchWidgetState
import com.example.myjobsearchapplication.ui.screens.job_search_screen.viewmodel.FilterOptions
import com.example.myjobsearchapplication.ui.screens.job_search_screen.viewmodel.JobSearchViewModel
import com.example.myjobsearchapplication.ui.screens.saved_jobs.viewmodel.SavedJobViewModel
import com.example.myjobsearchapplication.ui.screens.track_jobs_screen.viewmodel.TrackedJobsViewModel
import com.example.myjobsearchapplication.ui.theme.Cool1
import com.example.myjobsearchapplication.ui.theme.Cyan
import com.example.myjobsearchapplication.ui.theme.LightCyan
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun JobSearchScreen(
    onJobItemClicked: (jobItem: JobUiModel) -> Unit,
    onSavedJobsNavigate: () -> Unit,
    onJobSearchNavigate:  () -> Unit,
    onRemindersNavigate:  () -> Unit,
    onTrackerNavigate:  () -> Unit,
    navController: NavController
) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val jobSearchViewModel: JobSearchViewModel = hiltViewModel()
    val searchViewModel: SearchViewModel = hiltViewModel()

    val savedJobViewModel: SavedJobViewModel = hiltViewModel()

//    val jobList by jobSearchViewModel.jobList.collectAsStateWithLifecycle()
    val jobSearchState  by jobSearchViewModel.jobSearchState.collectAsStateWithLifecycle()


//    val searchQuery by jobSearchViewModel.searchQuery.collectAsStateWithLifecycle()
//    val filterOptions by jobSearchViewModel.filterOptions.collectAsStateWithLifecycle()


    val trackedJobViewModel: TrackedJobsViewModel = hiltViewModel()



    var showFilterMenu by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = true) {
        jobSearchViewModel.fetchJobs()
    }


    val searchWidgetState by searchViewModel.searchWidgetState
    val searchTextState by searchViewModel.searchTextState

    val coroutineScope = rememberCoroutineScope()

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                MainAppBar(
                    searchWidgetState = searchWidgetState,
                    searchTextState = searchTextState,
                    onTextChange = {
                        searchViewModel.updateSearchTextState(newValue = it)
                    },
                    onCloseClicked = {
                        searchViewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)

                    },
                    onSearchClicked = {
                        Log.d("Searched Text", it)
                    },
                    onSearchTriggered = {
                        searchViewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                    }
                )
            },
            bottomBar = {
                BottomBar(
                    onJobSearchNavigate = onJobSearchNavigate, onSavedJobsNavigate = onSavedJobsNavigate, onTrackerNavigate = onTrackerNavigate, onRemindersNavigate = onRemindersNavigate,
                    currentRoute = currentRoute
//                    navController = navController
                )
            }
        ) { innerPadding ->

            when {
                jobSearchState.isLoading -> {
                    AnimateShimmerJobList(innerPadding)
                }

                jobSearchState.isError -> {
                    jobSearchState.errorMessage?.let {
                        ErrorSection(
                            onRefreshButtonClicked = {
                                coroutineScope.launch {
                                    jobSearchViewModel.fetchJobs()
                                }
                            },
                            customErrorExceptionUiModel = it
                        )
                    }
                }

                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
//                        SearchAppBar2(
//                            text = searchTextState,
//                            onTextChange = {
//                                searchViewModel.updateSearchTextState(newValue = it)
//                            },
//                            onCloseClicked = {
//                                searchViewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
//
//                            },
//                            onSearchClicked = {
//                                Log.d("Searched Text", it)
//                            },
//                        )
//
//                        JobFilters(currentFilters = jobSearchState.filterOptions,
////                filterOptions,
//                            onFilterChanged = { jobSearchViewModel.updateFilterOptions(it) })

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
//                        .weight(1f)
//                        .padding(innerPadding)
                        ) {
                            // Search App Bar as first item
                            item {
                                SearchAppBar2(
                                    text = searchTextState,
                                    onTextChange = {
                                        searchViewModel.updateSearchTextState(newValue = it)
                                    },
                                    onCloseClicked = {
                                        searchViewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                                    },
                                    onSearchClicked = {
                                        Log.d("Searched Text", it)
                                    },
                                )
                            }

                            item {
                                Text(
                                    "Categories",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(top = 30.dp)
                                        .padding(horizontal = 20.dp)
                                )
                            }

                            // Filters as sticky header
                            item {
                                JobFilters(
                                    currentFilters = jobSearchState.filterOptions,
                                    onFilterChanged = { jobSearchViewModel.updateFilterOptions(it) }
                                )
                            }

                            items( jobSearchState.jobList
//                        jobList
                            ) { jobItem ->
                                val isSaved by produceState(initialValue = false) {
                                    value = savedJobViewModel.isJobSaved(jobItem.id)
                                }

                                JobItem(
                                    jobUiModel = jobItem,
                                    onJobItemClicked = {
                                        onJobItemClicked(it)
                                    },
                                    onSave = {savedJobViewModel.saveJob(jobItem)},
                                    onDelete = {savedJobViewModel.deleteJob(jobItem.id)},
                                    isSaved = isSaved,
                                    jobSearchJobItem = true,
                                    onTrackJob = {}
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobFilters(
    currentFilters: FilterOptions,
    onFilterChanged: (FilterOptions) -> Unit
) {
    var expandedContractType by remember { mutableStateOf(false) }
    var expandedDate by remember { mutableStateOf(false) }
    var expandedContractTime by remember { mutableStateOf(false) }
    var expandedSalary by remember { mutableStateOf(false) }

    var minSalary by remember { mutableStateOf(currentFilters.minSalary?.toString() ?: "") }
    var maxSalary by remember { mutableStateOf(currentFilters.maxSalary?.toString() ?: "") }

    val context = LocalContext.current


    //
//    val cornerRadius = 12.dp
    val cornerRadius = 25.dp
    val buttonHeight = 42.dp
    val buttonColors = ButtonDefaults.buttonColors(
//        containerColor = MaterialTheme.colorScheme.surfaceVariant,
//        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        containerColor = Cool1,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    )
    //

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .horizontalScroll(rememberScrollState()) ,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterButton(
            text = "Contract Type",

            //
            leadingIcon = Icons.Default.Work,
            //

//            trailingIcon =
//            if(expandedContractType){
//                Icons.Default.ArrowDropUp
//            }else if(currentFilters.contractType.isEmpty() && !expandedContractType) {
//                Icons.Default.ArrowDropDown
//            }else {
//                Icons.Default.Close
//            }
//            ,

            expanded = expandedContractType,
            onClick = { expandedContractType = !expandedContractType },

            //
            buttonColors = buttonColors,
            buttonHeight = buttonHeight,
            cornerRadius = cornerRadius,
            filter = currentFilters.contractType,
            onClearFilter = {onFilterChanged(currentFilters.copy(contractType = ""))},
            onExpand = {expandedContractType = true}
            //
        ) {
            DropdownMenuItem(text = { Text("Permanent") }, onClick = {
                onFilterChanged(currentFilters.copy(contractType = "permanent"))
                expandedContractType  = false })
            DropdownMenuItem(text = { Text("Contract") }, onClick = {
                onFilterChanged(currentFilters.copy(contractType = "contract"))
                expandedContractType = false })

            //
            DropdownMenuItem(
                text = { Text("All Types") },
                onClick = {
                    onFilterChanged(currentFilters.copy(contractType = ""))
                    expandedContractType = false
                }
            )
            //
        }

        FilterButton(
            text = "Date posted",

            //
            leadingIcon = Icons.Default.Schedule,
            //
//            trailingIcon = Icons.Default.Schedule,

            expanded = expandedDate,
            onClick = { expandedDate = !expandedDate },

            //
            buttonColors = buttonColors,
            buttonHeight = buttonHeight,
            cornerRadius = cornerRadius,
            filter = currentFilters.datePosted,
            onClearFilter = {onFilterChanged(currentFilters.copy(datePosted = ""))},
            onExpand = {expandedDate = true}
            //
        ) {
            DropdownMenuItem(text = { Text("Last 24 hours") }, onClick = {
                onFilterChanged(currentFilters.copy(datePosted = "24h"))
                expandedDate = false })
            DropdownMenuItem(text = { Text("Last 7 days") }, onClick = {
                onFilterChanged(currentFilters.copy(datePosted = "7d"))
                expandedDate = false })
            DropdownMenuItem(text = { Text("Last 30 days") }, onClick = {
                onFilterChanged(currentFilters.copy(datePosted = "30d"))
                expandedDate = false })

            DropdownMenuItem(
                text = { Text("Any time") },
                onClick = {
                    onFilterChanged(currentFilters.copy(datePosted = ""))
                    expandedDate = false
                }
            )

        }

        FilterButton(
            text = "Contract Time",
            leadingIcon = Icons.Default.AccessTime,
//            trailingIcon = Icons.Default.AccessTime,
            expanded = expandedContractTime,
            onClick = { expandedContractTime = !expandedContractTime },
            //
            buttonColors = buttonColors,
            buttonHeight = buttonHeight,
            cornerRadius = cornerRadius,
            filter = currentFilters.contractTime,
            onClearFilter = {onFilterChanged(currentFilters.copy(contractTime = ""))},
            onExpand = {expandedContractTime = true}
            //
        ) {
            DropdownMenuItem(text = { Text("Full Time") }, onClick = {
                onFilterChanged(currentFilters.copy(contractTime = "full_time"))
                expandedContractTime = false })
            DropdownMenuItem(text = { Text("Part Time") }, onClick = {
                onFilterChanged(currentFilters.copy(contractTime = "part_time"))
                expandedContractTime = false })

            //
            DropdownMenuItem(
                text = { Text("Any") },
                onClick = {
                    onFilterChanged(currentFilters.copy(contractTime = ""))
                    expandedContractTime = false
                }
            )
            //
        }

        FilterButton(
            text = "Salary Range",

            //
            leadingIcon = Icons.Default.AttachMoney,
            //
//            trailingIcon = Icons.Default.AttachMoney,


            expanded  = expandedSalary,
            onClick  = { expandedSalary = !expandedSalary },

            //
            buttonColors = buttonColors,
            buttonHeight = buttonHeight,
            cornerRadius = cornerRadius,
            onClearFilter = {
                onFilterChanged(
                    currentFilters.copy(
                        minSalary = null,
                        maxSalary = null
                    )
                )
                minSalary = ""
                maxSalary = ""
            },
            filter = if(minSalary.isNotEmpty() && maxSalary.isNotEmpty()) minSalary else "",
            onExpand = {expandedSalary = true}
            //
        ) {
//
            DropdownMenuItem(
                text = {
                    Column(
                        modifier = Modifier
                            .width(IntrinsicSize.Max)
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            "Salary Range",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = minSalary,
                                onValueChange = { minSalary = it },
                                label = { Text("Min") },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                                    unfocusedBorderColor = LightCyan,
                                )
                            )

                            Text(":", style = MaterialTheme.typography.bodyMedium)

                            OutlinedTextField(
                                value = maxSalary,
                                onValueChange = { maxSalary = it },
                                label = { Text("Max") },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                                    unfocusedBorderColor = LightCyan,
                                )
                            )
                        }

                        //
//                        var salaryRange by remember { mutableStateOf(0f..100f) }
//
//                        Column(modifier = Modifier.padding(16.dp)) {
//                            Text(text = "Select Salary Range", style = MaterialTheme.typography.titleSmall)
//
//                            Spacer(modifier = Modifier.height(16.dp))
//
//                            RangeSlider(
//                                value = salaryRange,
//                                onValueChange = { salaryRange = it },
//                                valueRange = 0f..200f,
//                                steps = 30
//                            )
//
//                            Spacer(modifier = Modifier.height(8.dp))
//
//                            Text(
//                                text = "Selected: ${salaryRange.start.toInt() * 1000}$ - ${salaryRange.endInclusive.toInt() * 1000}$",
//                                style = MaterialTheme.typography.titleSmall,
//                            )
//                        }
                        //

                        /////////////////////////////////////////////////

//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.spacedBy(8.dp)
//                        ) {
//                            Button(
//                                onClick = {
//                                    onFilterChanged(
//                                        currentFilters.copy(
//                                            minSalary = null,
//                                            maxSalary = null
//                                        )
//                                    )
//                                    minSalary = ""
//                                    maxSalary = ""
//                                    expandedSalary = false
//                                },
//                                modifier = Modifier.weight(1f),
//                                colors = ButtonDefaults.buttonColors(
//                                    containerColor = MaterialTheme.colorScheme.errorContainer,
//                                    contentColor = MaterialTheme.colorScheme.onErrorContainer
//                                ),
//                                shape = RoundedCornerShape(8.dp)
//                            ) {
//                                Text("Clear")
//                            }

                            Button(
                                onClick = {
                                    if(minSalary.isEmpty() || maxSalary.isEmpty()){
                                        Toast.makeText(context, "You must add minimum and maximum salary", Toast.LENGTH_SHORT).show()
                                    }else{
                                        onFilterChanged(
                                            currentFilters.copy(
                                                minSalary = minSalary.toDoubleOrNull(),
                                                maxSalary = maxSalary.toDoubleOrNull()
                                            )
                                        )
                                        expandedSalary = false
                                    }

                                },
//                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(25.dp),
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            ) {
                                Text("Apply")
                            }
//                        }
                    }
                },
                onClick = { /* Prevent auto-closing */ }
            )
        }
    }
}


@Composable
fun FilterButton(
    text: String,
    leadingIcon: ImageVector,
//    trailingIcon: ImageVector,
    expanded: Boolean,
    onClick: () -> Unit,
    buttonColors: ButtonColors,
    buttonHeight: Dp,
    cornerRadius: Dp,
    filter: String,
    onClearFilter: () -> Unit,
    onExpand: () -> Unit,
    dropdownContent: @Composable () -> Unit
) {
    Box {
        Button(
            onClick = onClick,
            colors = buttonColors,
            shape = RoundedCornerShape(cornerRadius),
            modifier = Modifier
                .height(buttonHeight)
                .padding(vertical = 4.dp)
            ,
            border = BorderStroke(
                1.dp,
                if (expanded || filter.isNotEmpty()) MaterialTheme.colorScheme.primary else LightCyan
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 0.dp,
                pressedElevation = 2.dp
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    leadingIcon,
                    contentDescription = null,
                )
                Text(text, style = MaterialTheme.typography.labelLarge)
//                Icon(
//                    if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
//                    contentDescription = "Dropdown Arrow",
//                )


//                if(expandedContractType){
//                Icons.Default.ArrowDropUp
//            }else if(currentFilters.contractType.isEmpty() && !expandedContractType) {
//                Icons.Default.ArrowDropDown
//            }else {
//                Icons.Default.Close
//            }

                IconButton(
                    onClick ={
                        if(filter.isNotEmpty()){
                            onClearFilter()
                        }else{
                            onExpand()
                        }
                    }
                )
                {
                    Icon(
                        if(expanded){
                            Icons.Default.ArrowDropUp
                        }else if(filter.isEmpty() && !expanded){
                            Icons.Default.ArrowDropDown
                        }else{
                            Icons.Default.Close
                        }
                            ,
                        contentDescription = null,
                    )

                }


            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onClick,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .padding(4.dp)
            ,
            offset = DpOffset(x = 0.dp, y = 4.dp)
        ) {
            dropdownContent()
        }
    }
}


