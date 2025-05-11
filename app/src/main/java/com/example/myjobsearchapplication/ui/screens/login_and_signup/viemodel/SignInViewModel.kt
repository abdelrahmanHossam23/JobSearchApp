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
                _state.value = state.value.copy(email = event.email)
            }
            is SignInEvent.PasswordChanged -> {
                _state.value = state.value.copy(password = event.password)
            }
            SignInEvent.Submit -> {
                signIn()
            }
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
                    error = e.message,
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
    val error: String? = null
)

sealed class SignInEvent {
    data class EmailChanged(val email: String) : SignInEvent()
    data class PasswordChanged(val password: String) : SignInEvent()
    object Submit : SignInEvent()
}