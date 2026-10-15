package com.floresvalencia.yourpeak.ui.screens.register

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

data class RegisterUiState(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val nameError: Boolean = false,
    val emailError: Boolean = false,
    val passwordError: Boolean = false,
    val confirmError: Boolean = false,
    val isLoading: Boolean = false,
    val message: String? = null,
    val isRegistered: Boolean = false
)

class RegisterViewModel(
    private val repository: AuthRepository = FakeAuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onNameChange(v: String) = _uiState.update { it.copy(fullName = v, nameError = false) }
    fun onEmailChange(v: String) = _uiState.update { it.copy(email = v, emailError = false) }
    fun onPasswordChange(v: String) = _uiState.update { it.copy(password = v, passwordError = false) }
    fun onConfirmChange(v: String) = _uiState.update { it.copy(confirmPassword = v, confirmError = false) }

    fun register() {
        val s = _uiState.value
        val nameError = s.fullName.isBlank()
        val emailError = !Validators.isValidEmail(s.email)
        val passwordError = !Validators.isValidPassword(s.password)
        val confirmError = s.confirmPassword != s.password || s.confirmPassword.isEmpty()

        val message = when {
            nameError -> "Ingresa tu nombre completo"
            emailError -> "Ingresa un correo válido"
            passwordError -> "La contraseña debe tener al menos 6 caracteres"
            confirmError -> "Las contraseñas no coinciden"
            else -> null
        }
        if (message != null) {
            _uiState.update {
                it.copy(
                    nameError = nameError, emailError = emailError,
                    passwordError = passwordError, confirmError = confirmError,
                    message = message
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            repository.register(s.fullName.trim(), s.email.trim(), s.password)
                .onSuccess {
                    _uiState.update {
                        it.copy(isLoading = false, isRegistered = true, message = "Cuenta creada con éxito")
                    }
                }
                .onFailure { e -> _uiState.update { it.copy(isLoading = false, message = e.message) } }
        }
    }

    fun onMessageShown() = _uiState.update { it.copy(message = null) }
    fun onRegisterHandled() = _uiState.update { it.copy(isRegistered = false) }
}
