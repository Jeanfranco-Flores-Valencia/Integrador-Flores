package com.floresvalencia.yourpeak.ui.screens.forgot

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Drafts
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Pin
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.floresvalencia.yourpeak.ui.components.AuthCard
import com.floresvalencia.yourpeak.ui.components.AuthScaffold
import com.floresvalencia.yourpeak.ui.components.BackButton
import com.floresvalencia.yourpeak.ui.components.CardMessage
import com.floresvalencia.yourpeak.ui.components.GlowIcon
import com.floresvalencia.yourpeak.ui.components.PageIndicator
import com.floresvalencia.yourpeak.ui.components.PeakButton
import com.floresvalencia.yourpeak.ui.components.PeakTextField
import com.floresvalencia.yourpeak.ui.theme.PeakBackground
import com.floresvalencia.yourpeak.ui.theme.PeakRed
import com.floresvalencia.yourpeak.ui.theme.TextPrimary
import com.floresvalencia.yourpeak.ui.theme.YourPeakTheme

/** Muestra mensajes (Toast) y reacciona a los eventos del ViewModel compartido. */
@Composable
private fun ForgotEffects(
    state: ForgotUiState,
    viewModel: ForgotPasswordViewModel,
    expectedEvent: ForgotEvent,
    onEvent: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(state.message) {
        state.message?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.onMessageShown()
        }
    }
    LaunchedEffect(state.event) {
        if (state.event == expectedEvent) {
            viewModel.onEventHandled()
            onEvent()
        }
    }
}

/** Plantilla común: flecha atrás + ícono con halo arriba, tarjeta abajo. */
@Composable
private fun ForgotLayout(
    onBack: () -> Unit,
    icon: @Composable () -> Unit,
    card: @Composable () -> Unit
) {
    AuthScaffold(
        top = {
            BackButton(onClick = onBack)
            Spacer(Modifier.height(4.dp))
            GlowIcon(Modifier.align(Alignment.CenterHorizontally)) { icon() }
        },
        bottom = {
            card()
            Spacer(Modifier.height(24.dp))
        }
    )
}

// ---------- 1. Ingresar correo ----------

@Composable
fun ForgotEmailScreen(
    viewModel: ForgotPasswordViewModel,
    state: ForgotUiState,
    onBack: () -> Unit,
    onCodeSent: () -> Unit
) {
    ForgotEffects(state, viewModel, ForgotEvent.CODE_SENT, onCodeSent)
    ForgotEmailContent(state, viewModel::onEmailChange, viewModel::sendCode, onBack)
}

@Composable
private fun ForgotEmailContent(
    state: ForgotUiState,
    onEmailChange: (String) -> Unit,
    onSend: () -> Unit,
    onBack: () -> Unit
) {
    val focus = LocalFocusManager.current
    ForgotLayout(
        onBack = onBack,
        icon = { BigIcon { Icon(Icons.Filled.Lock, null, tint = PeakRed, modifier = Modifier.fillMaxSize()) } }
    ) {
        AuthCard {
            Spacer(Modifier.height(8.dp))
            CardMessage(
                "Por favor, introduzca su dirección de correo electrónico para recibir un código de verificación."
            )
            Spacer(Modifier.height(28.dp))
            PeakTextField(
                value = state.email,
                onValueChange = onEmailChange,
                placeholder = "example@email.com",
                leadingIcon = Icons.Outlined.Email,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done,
                isError = state.emailError,
                onImeAction = { focus.clearFocus(); onSend() }
            )
            Spacer(Modifier.height(36.dp))
            PeakButton(
                text = "Enviar",
                onClick = { focus.clearFocus(); onSend() },
                isLoading = state.isLoading,
                modifier = Modifier.fillMaxWidth(0.42f)
            )
            Spacer(Modifier.height(20.dp))
            PageIndicator(selected = 0)
        }
    }
}

// ---------- 2. Verificar código ----------

@Composable
fun VerifyCodeScreen(
    viewModel: ForgotPasswordViewModel,
    state: ForgotUiState,
    onBack: () -> Unit,
    onVerified: () -> Unit
) {
    ForgotEffects(state, viewModel, ForgotEvent.CODE_VERIFIED, onVerified)
    VerifyCodeContent(state, viewModel::onCodeChange, viewModel::verifyCode, viewModel::resendCode, onBack)
}

@Composable
private fun VerifyCodeContent(
    state: ForgotUiState,
    onCodeChange: (String) -> Unit,
    onVerify: () -> Unit,
    onResend: () -> Unit,
    onBack: () -> Unit
) {
    val focus = LocalFocusManager.current
    ForgotLayout(
        onBack = onBack,
        icon = { BigIcon { Icon(Icons.Filled.Drafts, null, tint = PeakRed, modifier = Modifier.fillMaxSize()) } }
    ) {
        AuthCard {
            Spacer(Modifier.height(8.dp))
            CardMessage(
                "Ingrese el código de verificación que se acaba de enviar a su dirección de correo electrónico."
            )
            Spacer(Modifier.height(24.dp))
            PeakTextField(
                value = state.code,
                onValueChange = onCodeChange,
                placeholder = "Ingrese el código de verificación",
                leadingIcon = Icons.Outlined.Pin,
                keyboardType = KeyboardType.NumberPassword,
                imeAction = ImeAction.Done,
                isError = state.codeError,
                onImeAction = { focus.clearFocus(); onVerify() }
            )
            Spacer(Modifier.height(14.dp))
            Text(
                text = "Reenviar código",
                style = MaterialTheme.typography.labelSmall,
                color = TextPrimary,
                modifier = Modifier
                    .clickable(onClick = onResend)
                    .padding(4.dp)
            )
            Spacer(Modifier.height(22.dp))
            PeakButton(
                text = "Verificar",
                onClick = { focus.clearFocus(); onVerify() },
                isLoading = state.isLoading,
                modifier = Modifier.fillMaxWidth(0.5f)
            )
            Spacer(Modifier.height(20.dp))
            PageIndicator(selected = 1)
        }
    }
}

// ---------- 3. Nueva contraseña ----------

@Composable
fun NewPasswordScreen(
    viewModel: ForgotPasswordViewModel,
    state: ForgotUiState,
    onBack: () -> Unit,
    onSaved: () -> Unit
) {
    ForgotEffects(state, viewModel, ForgotEvent.PASSWORD_SAVED, onSaved)
    NewPasswordContent(
        state, viewModel::onNewPasswordChange, viewModel::onConfirmChange, viewModel::savePassword, onBack
    )
}

@Composable
private fun NewPasswordContent(
    state: ForgotUiState,
    onPasswordChange: (String) -> Unit,
    onConfirmChange: (String) -> Unit,
    onSave: () -> Unit,
    onBack: () -> Unit
) {
    val focus = LocalFocusManager.current
    ForgotLayout(onBack = onBack, icon = { LockCheckIcon() }) {
        AuthCard {
            Spacer(Modifier.height(8.dp))
            CardMessage("Tu nueva contraseña debe ser diferente de tu contraseña anterior.")
            Spacer(Modifier.height(24.dp))
            PeakTextField(
                value = state.newPassword,
                onValueChange = onPasswordChange,
                placeholder = "Nueva contraseña",
                leadingIcon = Icons.Outlined.Lock,
                isPassword = true,
                isError = state.passwordError
            )
            Spacer(Modifier.height(12.dp))
            PeakTextField(
                value = state.confirmPassword,
                onValueChange = onConfirmChange,
                placeholder = "Confirmar contraseña",
                leadingIcon = Icons.Outlined.Lock,
                isPassword = true,
                isError = state.confirmError,
                imeAction = ImeAction.Done,
                onImeAction = { focus.clearFocus(); onSave() }
            )
            Spacer(Modifier.height(28.dp))
            PeakButton(
                text = "Guardar",
                onClick = { focus.clearFocus(); onSave() },
                isLoading = state.isLoading,
                modifier = Modifier.fillMaxWidth(0.5f)
            )
            Spacer(Modifier.height(20.dp))
            PageIndicator(selected = 2)
        }
    }
}

// ---------- Íconos ----------

@Composable
private fun BigIcon(content: @Composable () -> Unit) {
    Box(Modifier.size(104.dp)) { content() }
}

/** Candado con insignia de check (pantalla de nueva contraseña). */
@Composable
private fun LockCheckIcon() {
    Box(Modifier.size(104.dp)) {
        Icon(Icons.Filled.Lock, null, tint = PeakRed, modifier = Modifier.fillMaxSize())
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 8.dp, y = 2.dp)
                .size(46.dp)
                .background(PeakBackground, CircleShape)
                .padding(4.dp)
                .background(PeakRed, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.Check, null, tint = PeakBackground, modifier = Modifier.size(28.dp))
        }
    }
}

// ---------- Previews ----------

@Preview(showBackground = true, widthDp = 360, heightDp = 760)
@Composable
private fun ForgotEmailPreview() {
    YourPeakTheme { ForgotEmailContent(ForgotUiState(), {}, {}, {}) }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 760)
@Composable
private fun VerifyCodePreview() {
    YourPeakTheme { VerifyCodeContent(ForgotUiState(), {}, {}, {}, {}) }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 760)
@Composable
private fun NewPasswordPreview() {
    YourPeakTheme { NewPasswordContent(ForgotUiState(), {}, {}, {}, {}) }
}
