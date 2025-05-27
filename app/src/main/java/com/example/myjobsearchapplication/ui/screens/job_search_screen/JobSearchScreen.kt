package com.example.myjobsearchapplication.ui.screens.job_search_screen

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import com.example.myjobsearchapplication.ui.common_components.BottomBar
import com.example.myjobsearchapplication.ui.common_components.ErrorSection
import com.example.myjobsearchapplication.ui.common_components.TopBar
import com.example.myjobsearchapplication.ui.common_components.shimmer.AnimateShimmerJobList
import com.example.myjobsearchapplication.ui.navigation.LogInManager
import com.example.myjobsearchapplication.ui.navigation.Screens
import com.example.myjobsearchapplication.ui.screens.job_search_screen.searchBar.SearchAppBar
import com.example.myjobsearchapplication.ui.screens.job_search_screen.searchBar.SearchViewModel
import com.example.myjobsearchapplication.ui.screens.job_search_screen.searchBar.SearchWidgetState
import com.example.myjobsearchapplication.ui.screens.job_search_screen.viewmodel.FilterOptions
import com.example.myjobsearchapplication.ui.screens.job_search_screen.viewmodel.JobSearchViewModel
import com.example.myjobsearchapplication.ui.screens.saved_jobs.viewmodel.SavedJobViewModel
import com.example.myjobsearchapplication.ui.screens.track_jobs_screen.viewmodel.TrackedJobsViewModel
import com.example.myjobsearchapplication.ui.theme.SpaceCadetBlue
import com.example.myjobsearchapplication.ui.theme.Cyan
import com.example.myjobsearchapplication.ui.theme.DarkCeruleanBlue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WorkHistory
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.derivedStateOf

import androidx.compose.runtime.key
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import com.example.myjobsearchapplication.R
import com.example.myjobsearchapplication.ui.common_components.ThemeManager


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun JobSearchScreen(
    onJobItemClicked: (jobItem: JobUiModel) -> Unit,
    navController: NavController
) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val jobSearchViewModel: JobSearchViewModel = hiltViewModel()
    val searchViewModel: SearchViewModel = hiltViewModel()

    val savedJobViewModel: SavedJobViewModel = hiltViewModel()

    val jobSearchState  by jobSearchViewModel.jobSearchState.collectAsStateWithLifecycle()



    LaunchedEffect(Unit) {
        jobSearchViewModel.fetchJobsIfNeeded()
    }


    val searchTextState by searchViewModel.searchTextState

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val context = LocalContext.current
    val logInManager = remember { LogInManager(context) }


    Surface(
        modifier = Modifier.fillMaxSize()

    ) {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet (
                    modifier = Modifier.width(280.dp),
                    drawerShape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
                ){
                    Text(
                        text = stringResource(R.string.app_name),
                        fontSize = 24.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(25.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                val newTheme = !ThemeManager.isDarkTheme.value
                                ThemeManager.isDarkTheme.value = newTheme
                                context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                                    .edit()
                                    .putBoolean("dark_theme", newTheme)
                                    .apply()
                            }
                            .padding(NavigationDrawerItemDefaults.ItemPadding),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector =
                            if (ThemeManager.isDarkTheme.value) Icons.Filled.DarkMode else Icons.Filled.LightMode,
                            contentDescription = "Theme Toggle"
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(text =
                        if (ThemeManager.isDarkTheme.value) "Dark Theme" else "Light Theme")
                        Spacer(modifier = Modifier.weight(1f))
                        Switch(
                            checked = ThemeManager.isDarkTheme.value,
                            onCheckedChange = { isChecked ->
                                ThemeManager.isDarkTheme.value = isChecked
                                context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                                    .edit()
                                    .putBoolean("dark_theme", isChecked)
                                    .apply()
                            },
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                logInManager.logOut()
                                navController.navigate(Screens.LoginScreen.route) {
                                    popUpTo(0)
                                }
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                            .padding(NavigationDrawerItemDefaults.ItemPadding),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector =
                            Icons.Default.Logout
                            ,
                            contentDescription = "Logout",
                            tint = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Logout",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            },
            drawerState = drawerState,
        ){
            Scaffold(
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState) { data ->
                        Snackbar(
                            snackbarData = data,
                            containerColor = Color.DarkGray,
                            contentColor = Color.White,
                            actionColor = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    TopBar(
                        topBarTitle = "Job Search",

                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Menu"
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

                        val visibleItems = remember { mutableStateListOf<Boolean>() }
                        LaunchedEffect(jobSearchState.jobList.size) {
                            visibleItems.clear()
                            visibleItems.addAll(List(jobSearchState.jobList.size) { false })

                            jobSearchState.jobList.indices.forEach { index ->
                                delay(100L * index)
                                visibleItems[index] = true
                            }
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {


                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize(),
//                            state = rememberLazyListState()
                            ) {

                                item {
                                    AnimatedVisibility(
                                        visible = true,
                                        enter = fadeIn() + slideInVertically { -40 },
                                        exit = fadeOut() + slideOutVertically { -40 }
                                    ){
                                        SearchAppBar(
                                            text = searchTextState,
                                            onTextChange = {
                                                searchViewModel.updateSearchTextState(newValue = it)
                                            },
                                            onSearchClicked = {
                                                Log.d("Searched Text", it)
                                            },
                                        )
                                    }

                                }

                                item {
                                    AnimatedVisibility(
                                        visible = true,
                                        enter = fadeIn() + slideInVertically { -20 },
                                        exit = fadeOut() + slideOutVertically { -20 }
                                    ){
                                        Text(
                                            "Categories",
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(top = 30.dp)
                                                .padding(horizontal = 20.dp),
                                            color = MaterialTheme.colorScheme.onPrimary
                                        )
                                    }

                                }

                                item {
                                    AnimatedVisibility(
                                        visible = true,
                                        enter = fadeIn() + expandVertically(),
                                        exit = fadeOut() + shrinkVertically()
                                    ){
                                        JobFilters(
                                            currentFilters = jobSearchState.filterOptions,
                                            onFilterChanged = { jobSearchViewModel.updateFilterOptions(it) }
                                        )
                                    }

                                }


                                itemsIndexed(jobSearchState.jobList) { index, jobItem ->

                                    val onSave by rememberUpdatedState { savedJobViewModel.saveJob(jobItem) }
                                    val onDelete by rememberUpdatedState { savedJobViewModel.deleteJob(jobItem.id) }


                                    AnimatedVisibility(
                                        visible = visibleItems.getOrNull(index) ?: false,
                                        enter = fadeIn() + slideInVertically { it / 2 },
                                        exit = fadeOut() + slideOutVertically { it / 2 }
                                    ){

                                        key(jobItem.id) {
                                            JobItem(
                                                jobUiModel = jobItem,
                                                onJobItemClicked = {
                                                    onJobItemClicked(it)
                                                },
                                                onSave = {
                                                    coroutineScope.launch {
                                                        snackbarHostState.showSnackbar(
                                                            message = "Job Saved Successfully!",
                                                            actionLabel = "Dismiss",
                                                            duration = SnackbarDuration.Short
                                                        )

                                                    }
                                                    onSave()
                                                },
                                                onDelete = onDelete,
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


    val cornerRadius = 25.dp
    val buttonHeight = 42.dp
    val buttonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .horizontalScroll(rememberScrollState()) ,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterButton(
            text = "Contract Type",
            leadingIcon = Icons.Default.Work,
            expanded = expandedContractType,
            onClick = { expandedContractType = !expandedContractType },
            buttonColors = buttonColors,
            buttonHeight = buttonHeight,
            cornerRadius = cornerRadius,
            filter = currentFilters.contractType,
            onClearFilter = {onFilterChanged(currentFilters.copy(contractType = ""))},
            onExpand = {expandedContractType = true}
        ) {
            DropdownMenuItem(text = { Text("Permanent") }, onClick = {
                onFilterChanged(currentFilters.copy(contractType = "permanent"))
                expandedContractType  = false })
            DropdownMenuItem(text = { Text("Contract") }, onClick = {
                onFilterChanged(currentFilters.copy(contractType = "contract"))
                expandedContractType = false })


            DropdownMenuItem(
                text = { Text("All Types") },
                onClick = {
                    onFilterChanged(currentFilters.copy(contractType = ""))
                    expandedContractType = false
                }
            )
        }

        FilterButton(
            text = "Date posted",
            leadingIcon = Icons.Default.Schedule,
            expanded = expandedDate,
            onClick = { expandedDate = !expandedDate },
            buttonColors = buttonColors,
            buttonHeight = buttonHeight,
            cornerRadius = cornerRadius,
            filter = currentFilters.datePosted,
            onClearFilter = {onFilterChanged(currentFilters.copy(datePosted = ""))},
            onExpand = {expandedDate = true}
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
            leadingIcon = Icons.Default.WorkHistory,
            expanded = expandedContractTime,
            onClick = { expandedContractTime = !expandedContractTime },
            buttonColors = buttonColors,
            buttonHeight = buttonHeight,
            cornerRadius = cornerRadius,
            filter = currentFilters.contractTime,
            onClearFilter = {onFilterChanged(currentFilters.copy(contractTime = ""))},
            onExpand = {expandedContractTime = true}
        ) {
            DropdownMenuItem(text = { Text("Full Time") }, onClick = {
                onFilterChanged(currentFilters.copy(contractTime = "full_time"))
                expandedContractTime = false })
            DropdownMenuItem(text = { Text("Part Time") }, onClick = {
                onFilterChanged(currentFilters.copy(contractTime = "part_time"))
                expandedContractTime = false })

            DropdownMenuItem(
                text = { Text("Any") },
                onClick = {
                    onFilterChanged(currentFilters.copy(contractTime = ""))
                    expandedContractTime = false
                }
            )
        }

        FilterButton(
            text = "Salary Range",
            leadingIcon = Icons.Default.AttachMoney,
            expanded  = expandedSalary,
            onClick  = { expandedSalary = !expandedSalary },
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
        ) {
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
                                    unfocusedBorderColor = DarkCeruleanBlue,
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
                                    unfocusedBorderColor = DarkCeruleanBlue,
                                )
                            )
                        }
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
                                shape = RoundedCornerShape(25.dp),
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            ) {
                                Text("Apply")
                            }
                    }
                },
                onClick = {  }
            )
        }
    }
}


@Composable
fun FilterButton(
    text: String,
    leadingIcon: ImageVector,
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
                if (expanded || filter.isNotEmpty()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary
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


