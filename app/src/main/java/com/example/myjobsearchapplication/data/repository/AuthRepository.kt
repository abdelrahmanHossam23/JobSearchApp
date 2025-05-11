//package com.example.myjobsearchapplication.data.repository
//
//import com.google.firebase.auth.FirebaseAuth
//import kotlinx.coroutines.tasks.await
//import javax.inject.Inject
//
//class AuthRepository @Inject constructor(
//    private val auth: FirebaseAuth
//) {
//    val currentUser get() = auth.currentUser
//
//    suspend fun signInWithEmailAndPassword(email: String, password: String) {
//        auth.signInWithEmailAndPassword(email, password).await()
//    }
//
//    suspend fun createUserWithEmailAndPassword(email: String, password: String) {
//        auth.createUserWithEmailAndPassword(email, password).await()
//    }
//
//    fun signOut() = auth.signOut()
//
//    // Add other authentication methods as needed
//}