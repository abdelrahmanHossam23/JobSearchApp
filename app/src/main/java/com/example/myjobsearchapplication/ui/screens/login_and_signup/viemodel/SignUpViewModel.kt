package com.example.myjobsearchapplication.ui.screens.login_and_signup.viemodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myjobsearchapplication.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> = _state.asStateFlow()




    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.EmailChanged -> {
                _state.value = state.value.copy(
                    email = event.email,
                    emailError = validateEmail(event.email)
                )
            }
            is SignUpEvent.PasswordChanged -> {
                _state.value = state.value.copy(
                    password = event.password,
                    passwordError = validatePassword(event.password)
                )
            }
            is SignUpEvent.ConfirmPasswordChanged -> {
                _state.value = state.value.copy(
                    confirmPassword = event.confirmPassword,
                    confirmPasswordError = validateConfirmPassword(
                        password = state.value.password,
                        confirmPassword = event.confirmPassword
                    )
                )
            }

            is SignUpEvent.NameChanged -> {
                _state.value = state.value.copy(
                    name = event.name,
                    nameError = validateName(event.name)
                )
            }
            SignUpEvent.Submit -> {
                if (validateForm()) {
                    signUp()
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        val emailError = validateEmail(state.value.email)
        val passwordError = validatePassword(state.value.password)
        val confirmPasswordError = validateConfirmPassword(
            password = state.value.password,
            confirmPassword = state.value.confirmPassword
        )
        val nameError = validateName(state.value.name)

        _state.value = state.value.copy(
            emailError = emailError,
            passwordError = passwordError,
            confirmPasswordError = confirmPasswordError,
            nameError = nameError
        )

        return listOf(emailError, passwordError, confirmPasswordError, nameError).all { it == null }
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
            !password.any { it.isDigit() } -> "Password must contain at least one digit"
            !password.any { it.isLetter() } -> "Password must contain at least one letter"
            else -> null
        }
    }

    private fun validateConfirmPassword(password: String, confirmPassword: String): String? {
        return when {
            confirmPassword.isBlank() -> "Confirm Password cannot be empty"
            confirmPassword != password -> "Passwords do not match"
            else -> null
        }
    }

    private fun validateName(name: String): String? {
        return when {
            name.isBlank() -> "Name cannot be empty"
            name.length < 2 -> "Name must be at least 2 characters"
            else -> null
        }
    }
    private fun signUp() {
        viewModelScope.launch {
            _state.value = state.value.copy(isLoading = true, error = null)
            try {
                signUpUseCase(
                    email = state.value.email,
                    password = state.value.password,
                    name = state.value.name
                )
                _state.value = state.value.copy(isSuccess = true, isLoading = false)
            } catch (e: Exception) {
                _state.value = state.value.copy(
                    error = e.message ?: "Sign up failed",
                    isLoading = false
                )
            }
        }
    }
}

data class SignUpState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val name: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val nameError: String? = null,
    val authToken: String? = null
)

sealed class SignUpEvent {
    data class EmailChanged(val email: String) : SignUpEvent()
    data class PasswordChanged(val password: String) : SignUpEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : SignUpEvent()
    data class NameChanged(val name: String) : SignUpEvent()
    object Submit : SignUpEvent()
}