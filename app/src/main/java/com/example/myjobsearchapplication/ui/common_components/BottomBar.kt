package com.example.myjobsearchapplication.ui.common_components

import android.content.Intent
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import com.example.myjobsearchapplication.SecondActivity
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.QueryStats
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myjobsearchapplication.ui.navigation.Screens
import com.example.myjobsearchapplication.ui.screens.job_search_screen.searchBar.MainAppBar
import com.example.myjobsearchapplication.ui.screens.job_search_screen.searchBar.SearchViewModel
import com.example.myjobsearchapplication.ui.screens.job_search_screen.searchBar.SearchWidgetState
import com.example.myjobsearchapplication.ui.screens.job_search_screen.viewmodel.FilterOptions
import com.example.myjobsearchapplication.ui.screens.saved_jobs.viewmodel.SavedJobViewModel

@Composable
fun BottomBar(
    onJobSearchNavigate:  () -> Unit,
    onSavedJobsNavigate: () -> Unit,
    onTrackerNavigate:  () -> Unit,
    onRemindersNavigate:  () -> Unit,
//    navController: NavController,
    currentRoute: String?
) {

    val navigationBarItems = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unSelectedIcon = Icons.Outlined.Home,
            route =
            Screens.JobSearchScreen.route
//            onJobSearchNavigate
        ),
        BottomNavigationItem(
            title = "Saved Jobs",
            selectedIcon = Icons.Filled.Bookmark,
            unSelectedIcon = Icons.Outlined.BookmarkBorder,
            route =
            Screens.SavedJobsScreen.route
//            onSavedJobsNavigate
        ),
        BottomNavigationItem(
            title = "Tracker",
            selectedIcon = Icons.Filled.QueryStats,
            unSelectedIcon = Icons.Outlined.QueryStats,
            route =
            Screens.TrackerScreen.route
//            onTrackerNavigate
        ),
        BottomNavigationItem(
            title = "Reminder",
            selectedIcon = Icons.Filled.Notifications,
            unSelectedIcon = Icons.Outlined.Notifications,
            route =
            Screens.RemindersScreen.route
//            onRemindersNavigate
        )
    )

//    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }


    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background
    ) {
        navigationBarItems.forEach { bottomNavigationItem ->
            NavigationBarItem(
                selected =
//                selectedItemIndex == index
                currentRoute == bottomNavigationItem.route
                ,
                onClick = {
//                    selectedItemIndex = index
//                    bottomNavigationItem.route()

                    when (bottomNavigationItem.route) {
                        Screens.JobSearchScreen.route -> onJobSearchNavigate()
                        Screens.SavedJobsScreen.route -> onSavedJobsNavigate()
                        Screens.TrackerScreen.route -> onTrackerNavigate()
                        Screens.RemindersScreen.route -> onRemindersNavigate()
                    }
                },
                label = {
                    Text(
                        bottomNavigationItem.title,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                icon = {
//                        Icon(
//                            imageVector = if (selectedItemIndex == index) {
//                                bottomNavigationItem.selectedIcon
//                            } else bottomNavigationItem.unSelectedIcon,
//                            contentDescription = "",
//                            tint = MaterialTheme.colorScheme.onSurface
//                        )
                    Icon(
                        imageVector = if (currentRoute == bottomNavigationItem.route) {
                            bottomNavigationItem.selectedIcon
                        } else bottomNavigationItem.unSelectedIcon,
                        contentDescription = bottomNavigationItem.title,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.surface,
                    indicatorColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}




data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
//    val route: () -> Unit,
    val route: String
)

