package com.floresvalencia.yourpeak.ui.screens.forgot

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

enum class ForgotEvent { CODE_SENT, CODE_VERIFIED, PASSWORD_SAVED }

data class ForgotUiState(
    val email: String = "",
    val code: String = "",
    val newPassword: String = "",
    val confirmPassword: String = "",
    val emailError: Boolean = false,
    val codeError: Boolean = false,
    val passwordError: Boolean = false,
    val confirmError: Boolean = false,
    val isLoading: Boolean = false,
    val message: String? = null,
    val event: ForgotEvent? = null
)

/** ViewModel compartido por las 3 pantallas del flujo "Olvidaste tu contraseña". */
class ForgotPasswordViewModel(
    private val repository: AuthRepository = FakeAuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ForgotUiState())
    val uiState: StateFlow<ForgotUiState> = _uiState.asStateFlow()

    fun onEmailChange(v: String) = _uiState.update { it.copy(email = v, emailError = false) }
    fun onCodeChange(v: String) {
        if (v.length <= 6 && v.all(Char::isDigit)) _uiState.update { it.copy(code = v, codeError = false) }
    }
    fun onNewPasswordChange(v: String) = _uiState.update { it.copy(newPassword = v, passwordError = false) }
    fun onConfirmChange(v: String) = _uiState.update { it.copy(confirmPassword = v, confirmError = false) }

    fun sendCode() {
        val email = _uiState.value.email.trim()
        if (!Validators.isValidEmail(email)) {
            _uiState.update { it.copy(emailError = true, message = "Ingresa un correo válido") }
            return
        }
        launchRequest(ForgotEvent.CODE_SENT, "Código enviado a $email") {
            repository.sendResetCode(email)
        }
    }

    fun resendCode() {
        val email = _uiState.value.email.trim()
        launchRequest(null, "Código reenviado") { repository.sendResetCode(email) }
    }

    fun verifyCode() {
        val s = _uiState.value
        if (s.code.length != 6) {
            _uiState.update { it.copy(codeError = true, message = "El código tiene 6 dígitos") }
            return
        }
        launchRequest(ForgotEvent.CODE_VERIFIED) { repository.verifyCode(s.email.trim(), s.code) }
    }

    fun savePassword() {
        val s = _uiState.value
        val passwordError = !Validators.isValidPassword(s.newPassword)
        val confirmError = s.confirmPassword != s.newPassword
        if (passwordError || confirmError) {
            _uiState.update {
                it.copy(
                    passwordError = passwordError,
                    confirmError = confirmError,
                    message = if (passwordError) "La contraseña debe tener al menos 6 caracteres"
                    else "Las contraseñas no coinciden"
                )
            }
            return
        }
        launchRequest(ForgotEvent.PASSWORD_SAVED, "Contraseña actualizada") {
            repository.resetPassword(s.email.trim(), s.newPassword)
        }
    }

    private fun launchRequest(
        successEvent: ForgotEvent?,
        successMessage: String? = null,
        request: suspend () -> Result<Unit>
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            request()
                .onSuccess {
                    _uiState.update {
                        it.copy(isLoading = false, event = successEvent, message = successMessage)
                    }
                }
                .onFailure { e ->
                    _uiState.update { it.copy(isLoading = false, codeError = true, message = e.message) }
                }
        }
    }

    fun onMessageShown() = _uiState.update { it.copy(message = null) }
    fun onEventHandled() = _uiState.update { it.copy(event = null) }
}
