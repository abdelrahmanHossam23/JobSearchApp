package com.example.myjobsearchapplication.domain.repository

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun signUp(email: String, password: String, name: String)
    suspend fun signIn(email: String, password: String)
    fun getCurrentUser(): FirebaseUser?
    suspend fun signOut()
}