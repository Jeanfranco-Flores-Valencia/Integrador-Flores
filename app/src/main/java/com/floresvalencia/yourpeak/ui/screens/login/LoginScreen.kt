package com.floresvalencia.yourpeak.ui.screens.login

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
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
import com.floresvalencia.yourpeak.ui.components.SocialDivider
import com.floresvalencia.yourpeak.ui.theme.TextPrimary
import com.floresvalencia.yourpeak.ui.theme.TextSecondary
import com.floresvalencia.yourpeak.ui.theme.YourPeakTheme

@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onNavigateToForgot: () -> Unit,
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(state.message) {
        state.message?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.onMessageShown()
        }
    }
    LaunchedEffect(state.isLoggedIn) {
        if (state.isLoggedIn) {
            onLoginSuccess()
            viewModel.onLoginHandled()
        }
    }

    LoginContent(
        state = state,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onLoginClick = viewModel::login,
        onGoogleClick = viewModel::onGoogleClick,
        onForgotClick = onNavigateToForgot,
        onRegisterClick = onNavigateToRegister
    )
}

@Composable
private fun LoginContent(
    state: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onGoogleClick: () -> Unit,
    onForgotClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    AuthScaffold(
        top = {
            PeakLogo()
            Spacer(Modifier.height(40.dp))
            HeaderTitle("Busca\nYour Peak")
        },
        bottom = {
            AuthCard {
                Text(
                    text = "Inicia sesión en tu cuenta",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary
                )
                Spacer(Modifier.height(20.dp))
                PeakTextField(
                    value = state.email,
                    onValueChange = onEmailChange,
                    placeholder = "example@email.com",
                    leadingIcon = Icons.Outlined.Email,
                    keyboardType = KeyboardType.Email,
                    isError = state.emailError
                )
                Spacer(Modifier.height(12.dp))
                PeakTextField(
                    value = state.password,
                    onValueChange = onPasswordChange,
                    placeholder = "Contraseña",
                    leadingIcon = Icons.Outlined.Lock,
                    isPassword = true,
                    isError = state.passwordError,
                    imeAction = ImeAction.Done,
                    onImeAction = {
                        focusManager.clearFocus()
                        onLoginClick()
                    }
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "¿Olvidaste tu contraseña?",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary,
                    modifier = Modifier
                        .clickable(onClick = onForgotClick)
                        .padding(4.dp)
                )
                Spacer(Modifier.height(10.dp))
                PeakButton(
                    text = "Iniciar sesion",
                    onClick = {
                        focusManager.clearFocus()
                        onLoginClick()
                    },
                    isLoading = state.isLoading,
                    modifier = Modifier.fillMaxWidth(0.78f)
                )
                Spacer(Modifier.height(18.dp))
                SocialDivider(onGoogleClick = onGoogleClick)
                Spacer(Modifier.height(16.dp))
                FooterLink(
                    prefix = "¿ No tienes una cuenta ?  ",
                    action = "REGISTRATE",
                    onClick = onRegisterClick
                )
                Spacer(Modifier.height(14.dp))
                PageIndicator(selected = 1)
            }
        }
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 760)
@Composable
private fun LoginPreview() {
    YourPeakTheme { LoginContent(LoginUiState(), {}, {}, {}, {}, {}, {}) }
}
