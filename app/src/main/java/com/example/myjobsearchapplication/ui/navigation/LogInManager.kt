package com.example.myjobsearchapplication.ui.navigation

import android.content.Context

class LogInManager(private val context: Context) {
    private val sharedPref = context.getSharedPreferences("user_logIn_status", Context.MODE_PRIVATE)

    fun saveLoginStatus() {
        sharedPref.edit().putString("loggedIn", "true").apply()
    }

    private fun getLoginStatus(): String? {
        return sharedPref.getString("loggedIn", null)
    }

    fun logOut() {
        sharedPref.edit().remove("loggedIn").apply()
    }

    fun isLoggedIn(): Boolean {
        return getLoginStatus() == "true"
    }
}