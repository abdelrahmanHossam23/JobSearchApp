package com.example.myjobsearchapplication


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.myjobsearchapplication.ui.screens.reminders_screen.ReminderScreen
import com.example.myjobsearchapplication.ui.theme.JobSearchApplicationTheme

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JobSearchApplicationTheme {

            }
        }
    }
}



