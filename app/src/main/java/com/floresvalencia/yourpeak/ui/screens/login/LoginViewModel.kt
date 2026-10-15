package com.floresvalencia.yourpeak.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.floresvalencia.yourpeak.data.AuthRepository
import com.floresvalencia.yourpeak.data.FakeAuthRepository
import com.floresvalencia.yourpeak.data.Validators
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val emailError: Boolean = false,
    val passwordError: Boolean = false,
    val isLoading: Boolean = false,
    val message: String? = null,
    val isLoggedIn: Boolean = false
)

class LoginViewModel(
    private val repository: AuthRepository = FakeAuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(value: String) = _uiState.update { it.copy(email = value, emailError = false) }

    fun onPasswordChange(value: String) = _uiState.update { it.copy(password = value, passwordError = false) }

    fun login() {
        val state = _uiState.value
        val emailError = !Validators.isValidEmail(state.email)
        val passwordError = state.password.isBlank()
        if (emailError || passwordError) {
            _uiState.update {
                it.copy(
                    emailError = emailError,
                    passwordError = passwordError,
                    message = if (emailError) "Ingresa un correo válido" else "Ingresa tu contraseña"
                )
            }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            repository.login(state.email.trim(), state.password)
                .onSuccess { _uiState.update { it.copy(isLoading = false, isLoggedIn = true) } }
                .onFailure { e -> _uiState.update { it.copy(isLoading = false, message = e.message) } }
        }
    }

    fun onGoogleClick() = _uiState.update { it.copy(message = "Inicio con Google próximamente") }

    fun onMessageShown() = _uiState.update { it.copy(message = null) }

    fun onLoginHandled() = _uiState.update { it.copy(isLoggedIn = false) }
}
