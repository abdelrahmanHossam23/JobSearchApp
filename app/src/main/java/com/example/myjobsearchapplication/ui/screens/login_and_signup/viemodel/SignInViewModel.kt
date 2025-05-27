package com.example.myjobsearchapplication.ui.screens.login_and_signup.viemodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myjobsearchapplication.domain.usecase.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state: StateFlow<SignInState> = _state.asStateFlow()

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.EmailChanged -> {
                _state.value = state.value.copy(
                    email = event.email,
                    emailError = validateEmail(event.email)
                )
            }

            is SignInEvent.PasswordChanged -> {
                _state.value = state.value.copy(
                    password = event.password,
                    passwordError = validatePassword(event.password)
                )
            }

            SignInEvent.Submit -> {
                if (validateForm()) {
                    signIn()
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        val emailError = validateEmail(state.value.email)
        val passwordError = validatePassword(state.value.password)

        _state.value = state.value.copy(
            emailError = emailError,
            passwordError = passwordError
        )

        return emailError == null && passwordError == null
    }

    private fun validateEmail(email: String): String? {
        return when {
            email.isBlank() -> "Email cannot be empty"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email format"
            else -> null
        }
    }

    private fun validatePassword(password: String): String? {
        return when {
            password.isBlank() -> "Password cannot be empty"
            password.length < 6 -> "Password must be at least 6 characters"
            else -> null
        }
    }

    private fun signIn() {
        viewModelScope.launch {
            _state.value = state.value.copy(isLoading = true, error = null)
            try {
                signInUseCase(
                    email = state.value.email,
                    password = state.value.password
                )
                _state.value = state.value.copy(isSuccess = true, isLoading = false)
            } catch (e: Exception) {
                _state.value = state.value.copy(
                    error = e.message ?: "Sign in failed",
                    isLoading = false
                )
            }
        }
    }

}


data class SignInState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null
)

sealed class SignInEvent {
    data class EmailChanged(val email: String) : SignInEvent()
    data class PasswordChanged(val password: String) : SignInEvent()
    object Submit : SignInEvent()
}