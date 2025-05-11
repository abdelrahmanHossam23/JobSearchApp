package com.example.myjobsearchapplication.data.dataSources.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    suspend fun signUpWithEmailAndPassword(email: String, password: String, name: String) {
        val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            .build()
        authResult.user?.updateProfile(profileUpdates)?.await()
    }

    suspend fun signInWithEmailAndPassword(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
    }

    fun getCurrentUser() = firebaseAuth.currentUser

    suspend fun signOut() {
        firebaseAuth.signOut()
    }
}