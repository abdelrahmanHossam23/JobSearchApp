package com.example.myjobsearchapplication.ui.navigation

sealed class Screens (val route: String){
    data object JobSearchScreen: Screens("job_search_screen")
    data object JobDetailsScreen: Screens("job_details_screen/{jobId}"){
        fun passId(id: Long): String {
            return "job_details_screen/$id"
        }
    }
    data object AddReminderScreen: Screens("add_reminder_screen/{reminderId}"){
        fun passId(id: Int): String {
            return "add_reminder_screen/$id"
        }
    }
    data object SavedJobsScreen: Screens("saved_jobs_screen")
    data object RemindersScreen: Screens("reminders_screen")
    data object TrackerScreen: Screens("tracker_screen")
    data object LoginScreen: Screens("tracker_screen")
    data object SignUpScreen: Screens("tracker_screen")
}