package com.example.myjobsearchapplication

import android.app.Application
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import androidx.hilt.work.HiltWorkerFactory
//import com.google.firebase.ktx.Firebase


@HiltAndroidApp
class MyApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

//    override fun onCreate() {
//        super.onCreate()
//        // Initialize Firebase
////        Firebase.initializeApp(this)
//    }
}
