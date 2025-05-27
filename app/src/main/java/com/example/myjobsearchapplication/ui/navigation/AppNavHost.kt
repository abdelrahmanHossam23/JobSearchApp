package com.example.myjobsearchapplication.ui.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myjobsearchapplication.ui.screens.job_details_screen.JobDetailsScreen
import com.example.myjobsearchapplication.ui.screens.job_search_screen.JobSearchScreen
import com.example.myjobsearchapplication.ui.screens.add_reminder_screen.ReminderSetupScreen
import com.example.myjobsearchapplication.ui.screens.job_search_screen.viewmodel.JobSearchViewModel
import com.example.myjobsearchapplication.ui.screens.login_and_signup.SignInScreen
import com.example.myjobsearchapplication.ui.screens.login_and_signup.SignUpScreen
import com.example.myjobsearchapplication.ui.screens.reminders_screen.ReminderScreen
import com.example.myjobsearchapplication.ui.screens.saved_jobs.SavedJobsScreen
import com.example.myjobsearchapplication.ui.screens.track_jobs_screen.TrackerListScreen

@SuppressLint("UnrememberedGetBackStackEntry")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    val context = LocalContext.current
    val logInManager = remember { LogInManager(context) }

    val startDestination = remember {
        if (logInManager.isLoggedIn()) {
            Screens.JobSearchScreen.route
        } else {
            Screens.LoginScreen.route
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ){

        composable(Screens.LoginScreen.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 300 },
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ) + fadeIn(tween(300))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -300 },
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ) + fadeOut(tween(300))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -300 },
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ) + fadeIn(tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 300 },
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ) + fadeOut(tween(300))
            }
        ) {
            SignInScreen(
                onNavigateToSignUp = { navController.navigate(Screens.SignUpScreen.route) },
                onSignInSuccess = {
                    logInManager.saveLoginStatus()
                    navController.navigate(Screens.JobSearchScreen.route){
                        popUpTo(Screens.LoginScreen.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screens.SignUpScreen.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 300 },
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ) + fadeIn(tween(300))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -300 },
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ) + fadeOut(tween(300))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -300 },
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ) + fadeIn(tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 300 },
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ) + fadeOut(tween(300))
            }
        ) {
            SignUpScreen(
                onNavigateToSignIn = { navController.popBackStack() },
                onSignUpSuccess = {
                    logInManager.saveLoginStatus()
                    navController.navigate(Screens.JobSearchScreen.route){
                        popUpTo(Screens.LoginScreen.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Screens.JobSearchScreen.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 300 },
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ) + fadeIn(tween(300))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -300 },
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ) + fadeOut(tween(300))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -300 },
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ) + fadeIn(tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 300 },
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ) + fadeOut(tween(300))
            }

        ){


            JobSearchScreen (
                onJobItemClicked = { jobItem ->
                    navController.navigate(Screens.JobDetailsScreen.passId(jobItem.id))
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
            ),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 300 },
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ) + fadeIn(tween(300))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -300 },
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ) + fadeOut(tween(300))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -300 },
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ) + fadeIn(tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 300 },
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ) + fadeOut(tween(300))
            }
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
            ),
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -300 },
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ) + fadeOut(tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 300 },
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ) + fadeOut(tween(300))
            }
        ){ backStackEntry ->

            val reminderId = backStackEntry.arguments?.getInt("reminderId")

            ReminderSetupScreen(
                reminderId = if (reminderId == -1) null else reminderId,
                onBackNavigation = {navController.popBackStack()}
            )


        }

        composable(route = Screens.SavedJobsScreen.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 300 },
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ) + fadeIn(tween(300))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -300 },
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ) + fadeOut(tween(300))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -300 },
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ) + fadeIn(tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 300 },
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ) + fadeOut(tween(300))
            }
            ){
            SavedJobsScreen(
                onJobItemClicked = { jobItem ->
                    navController.navigate(Screens.JobDetailsScreen.passId(jobItem.id))
                },
                navController = navController
            )
        }

        composable(route = Screens.RemindersScreen.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 300 },
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ) + fadeIn(tween(300))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -300 },
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ) + fadeOut(tween(300))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -300 },
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ) + fadeIn(tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 300 },
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ) + fadeOut(tween(300))
            }
            ){
            ReminderScreen(
                onReminderItemClicked = { reminderItem ->
                    navController.navigate(Screens.AddReminderScreen.passId(reminderItem.id))
                },
                onAddReminder = {
                    navController.navigate(Screens.AddReminderScreen.passId(-1))
                },
                navController = navController
            )
        }

        composable(route = Screens.TrackerScreen.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 300 },
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ) + fadeIn(tween(300))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -300 },
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ) + fadeOut(tween(300))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -300 },
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ) + fadeIn(tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 300 },
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ) + fadeOut(tween(300))
            }
        ){
            TrackerListScreen(
                onJobItemClicked = { jobItem ->
                    navController.navigate(Screens.JobDetailsScreen.passId(jobItem.id))
                },
                navController = navController
            )
        }

    }
}



