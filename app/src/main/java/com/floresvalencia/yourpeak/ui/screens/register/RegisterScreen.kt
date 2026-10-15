package com.floresvalencia.yourpeak.ui.screens.register

import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.floresvalencia.yourpeak.ui.components.AuthCard
import com.floresvalencia.yourpeak.ui.components.AuthScaffold
import com.floresvalencia.yourpeak.ui.components.FooterLink
import com.floresvalencia.yourpeak.ui.components.HeaderTitle
import com.floresvalencia.yourpeak.ui.components.PageIndicator
import com.floresvalencia.yourpeak.ui.components.PeakButton
import com.floresvalencia.yourpeak.ui.components.PeakLogo
import com.floresvalencia.yourpeak.ui.components.PeakTextField
import com.floresvalencia.yourpeak.ui.theme.TextPrimary
import com.floresvalencia.yourpeak.ui.theme.YourPeakTheme

@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit,
    onRegisterSuccess: () -> Unit,
    viewModel: RegisterViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(state.message) {
        state.message?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.onMessageShown()
        }
    }
    LaunchedEffect(state.isRegistered) {
        if (state.isRegistered) {
            onRegisterSuccess()
            viewModel.onRegisterHandled()
        }
    }

    RegisterContent(
        state = state,
        onNameChange = viewModel::onNameChange,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onConfirmChange = viewModel::onConfirmChange,
        onRegisterClick = viewModel::register,
        onLoginClick = onNavigateToLogin
    )
}

@Composable
private fun RegisterContent(
    state: RegisterUiState,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    AuthScaffold(
        top = {
            PeakLogo()
            Spacer(Modifier.height(12.dp))
            HeaderTitle("Encuentra\nYour Peak")
        },
        bottom = { minCardHeight ->
            AuthCard(modifier = Modifier.heightIn(min = minCardHeight)) {
                Text(
                    text = "Registrate para usar la app",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary
                )
                Spacer(Modifier.height(20.dp))
                PeakTextField(
                    value = state.fullName,
                    onValueChange = onNameChange,
                    placeholder = "Nombre completo",
                    leadingIcon = Icons.Outlined.Person,
                    isError = state.nameError
                )
                Spacer(Modifier.height(16.dp))
                PeakTextField(
                    value = state.email,
                    onValueChange = onEmailChange,
                    placeholder = "example@email.com",
                    leadingIcon = Icons.Outlined.Email,
                    keyboardType = KeyboardType.Email,
                    isError = state.emailError
                )
                Spacer(Modifier.height(16.dp))
                PeakTextField(
                    value = state.password,
                    onValueChange = onPasswordChange,
                    placeholder = "Contraseña",
                    leadingIcon = Icons.Outlined.Lock,
                    isPassword = true,
                    isError = state.passwordError
                )
                Spacer(Modifier.height(16.dp))
                PeakTextField(
                    value = state.confirmPassword,
                    onValueChange = onConfirmChange,
                    placeholder = "Confirmar contraseña",
                    leadingIcon = Icons.Outlined.Lock,
                    isPassword = true,
                    isError = state.confirmError,
                    imeAction = ImeAction.Done,
                    onImeAction = {
                        focusManager.clearFocus()
                        onRegisterClick()
                    }
                )
                Spacer(Modifier.height(22.dp))
                PeakButton(
                    text = "Registrate",
                    onClick = {
                        focusManager.clearFocus()
                        onRegisterClick()
                    },
                    isLoading = state.isLoading,
                    modifier = Modifier.fillMaxWidth(0.78f)
                )
                Spacer(Modifier.height(18.dp))
                FooterLink(
                    prefix = "Yo tengo una cuenta  ",
                    action = "INICIAR SESION",
                    onClick = onLoginClick
                )
            }
            Spacer(Modifier.height(14.dp))
            PageIndicator(selected = 2)
        }
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 760)
@Composable
private fun RegisterPreview() {
    YourPeakTheme { RegisterContent(RegisterUiState(), {}, {}, {}, {}, {}, {}) }
}
