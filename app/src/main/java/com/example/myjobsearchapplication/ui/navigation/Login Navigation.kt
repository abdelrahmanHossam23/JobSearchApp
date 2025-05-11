//package com.example.myjobsearchapplication.ui.navigation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myjobsearchapplication.ui.screens.login_and_signup.SignInScreen
import com.example.myjobsearchapplication.ui.screens.login_and_signup.SignUpScreen

@Composable
fun AuthNavigation(
    startDestination: String = "signin",
    onAuthSuccess: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("signin") {
            SignInScreen(
                onNavigateToSignUp = { navController.navigate("signup") },
                onSignInSuccess = onAuthSuccess
            )
        }
        composable("signup") {
            SignUpScreen(
                onNavigateToSignIn = { navController.popBackStack() },
                onSignUpSuccess = onAuthSuccess
            )
        }
    }
}