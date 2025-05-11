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
                _state.value = state.value.copy(email = event.email)
            }
            is SignUpEvent.PasswordChanged -> {
                _state.value = state.value.copy(password = event.password)
            }
            is SignUpEvent.NameChanged -> {
                _state.value = state.value.copy(name = event.name)
            }
            SignUpEvent.Submit -> {
                signUp()
            }
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
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }
}

data class SignUpState(
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

sealed class SignUpEvent {
    data class EmailChanged(val email: String) : SignUpEvent()
    data class PasswordChanged(val password: String) : SignUpEvent()
    data class NameChanged(val name: String) : SignUpEvent()
    object Submit : SignUpEvent()
}