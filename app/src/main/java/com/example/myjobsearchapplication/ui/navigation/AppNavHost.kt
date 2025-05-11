package com.example.myjobsearchapplication.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myjobsearchapplication.ui.screens.job_details_screen.JobDetailsScreen
import com.example.myjobsearchapplication.ui.screens.job_search_screen.JobSearchScreen
import com.example.myjobsearchapplication.ui.screens.add_reminder_screen.ReminderSetupScreen
import com.example.myjobsearchapplication.ui.screens.reminders_screen.ReminderScreen
import com.example.myjobsearchapplication.ui.screens.saved_jobs.SavedJobsScreen
import com.example.myjobsearchapplication.ui.screens.track_jobs_screen.TrackerListScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost() {
    val navController = rememberNavController()
//    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavHost(
        navController = navController,
        startDestination = Screens.JobSearchScreen.route
//        startDestination = Screens.ReminderScreen.route
//          startDestination = Screens.SavedJobsScreen.route
    ){

        composable(route = Screens.JobSearchScreen.route){

            JobSearchScreen (
                onJobItemClicked = { jobItem ->
                    navController.navigate(Screens.JobDetailsScreen.passId(jobItem.id))
                },
                onSavedJobsNavigate = {
                    navController.navigate(Screens.SavedJobsScreen.route)
                },
                onJobSearchNavigate = {
                    navController.navigate(Screens.JobSearchScreen.route)
                },
                onRemindersNavigate = {
                    navController.navigate(Screens.RemindersScreen.route)
                },
                onTrackerNavigate = {
                    navController.navigate(Screens.TrackerScreen.route)
                },
                navController = navController
            )
        }

        composable(
            route = Screens.JobDetailsScreen.route,
            arguments = listOf(
                navArgument("jobId"){
                    type = NavType.LongType
                }
            )
            ){ backStackEntry ->

            val jobId = backStackEntry.arguments?.getLong("jobId")
            if (jobId != null){
                JobDetailsScreen(
                    jobId,
                    onBackNavigation = {navController.popBackStack()}
                )
            }

        }

        composable(
            route = Screens.AddReminderScreen.route,
            arguments = listOf(
                navArgument("reminderId"){
                    type = NavType.IntType
                }
            )
        ){ backStackEntry ->

            val reminderId = backStackEntry.arguments?.getInt("reminderId")

            ReminderSetupScreen(
                reminderId = if (reminderId == -1) null else reminderId,
                onBackNavigation = {navController.popBackStack()}
            )


        }

        composable(route = Screens.SavedJobsScreen.route){
            SavedJobsScreen(
                onJobItemClicked = { jobItem ->
                    navController.navigate(Screens.JobDetailsScreen.passId(jobItem.id))
                },
                onSavedJobsNavigate = {
                    navController.navigate(Screens.SavedJobsScreen.route)
                },
                onJobSearchNavigate = {
                    navController.navigate(Screens.JobSearchScreen.route)
                },
                onRemindersNavigate = {
                    navController.navigate(Screens.RemindersScreen.route)
                },
                onTrackerNavigate = {
                    navController.navigate(Screens.TrackerScreen.route)
                },
                navController = navController
            )
        }

        composable(route = Screens.RemindersScreen.route){
            ReminderScreen(
                onReminderItemClicked = { reminderItem ->
                    navController.navigate(Screens.AddReminderScreen.passId(reminderItem.id))
                },
                onAddReminder = {
                    navController.navigate(Screens.AddReminderScreen.passId(-1))
                },
                onSavedJobsNavigate = {
                    navController.navigate(Screens.SavedJobsScreen.route)
                },
                onJobSearchNavigate = {
                    navController.navigate(Screens.JobSearchScreen.route)
                },
                onRemindersNavigate = {
                    navController.navigate(Screens.RemindersScreen.route)
                },
                onTrackerNavigate = {
                    navController.navigate(Screens.TrackerScreen.route)
                },
                navController = navController
            )
        }

        composable(route = Screens.TrackerScreen.route){
            TrackerListScreen(
                onJobItemClicked = { jobItem ->
                    navController.navigate(Screens.JobDetailsScreen.passId(jobItem.id))
                },
                onSavedJobsNavigate = {
                    navController.navigate(Screens.SavedJobsScreen.route)
                },
                onJobSearchNavigate = {
                    navController.navigate(Screens.JobSearchScreen.route)
                },
                onRemindersNavigate = {
                    navController.navigate(Screens.RemindersScreen.route)
                },
                onTrackerNavigate = {
                    navController.navigate(Screens.TrackerScreen.route)
                },
                navController = navController
            )
        }

    }
}



