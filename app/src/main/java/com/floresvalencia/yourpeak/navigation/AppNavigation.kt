package com.floresvalencia.yourpeak.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.floresvalencia.yourpeak.ui.screens.forgot.ForgotEmailScreen
import com.floresvalencia.yourpeak.ui.screens.forgot.ForgotPasswordViewModel
import com.floresvalencia.yourpeak.ui.screens.forgot.NewPasswordScreen
import com.floresvalencia.yourpeak.ui.screens.forgot.VerifyCodeScreen
import com.floresvalencia.yourpeak.ui.screens.login.LoginScreen
import com.floresvalencia.yourpeak.ui.screens.register.RegisterScreen
import com.floresvalencia.yourpeak.ui.screens.splash.SplashScreen

object Routes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val FORGOT_GRAPH = "forgot_graph"
    const val FORGOT_EMAIL = "forgot_email"
    const val FORGOT_CODE = "forgot_code"
    const val FORGOT_NEW_PASSWORD = "forgot_new_password"
}

@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = Routes.SPLASH) {

        composable(Routes.SPLASH) {
            SplashScreen(
                onLoginClick = { navController.navigate(Routes.LOGIN) },
                onRegisterClick = { navController.navigate(Routes.REGISTER) }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(Routes.REGISTER) { launchSingleTop = true }
                },
                onNavigateToForgot = { navController.navigate(Routes.FORGOT_GRAPH) },
                onLoginSuccess = {
                    // Aquí navegarías a tu Home
                    Toast.makeText(context, "¡Bienvenido!", Toast.LENGTH_SHORT).show()
                }
            )
        }

        composable(Routes.REGISTER) {
            val goToLogin = {
                navController.navigate(Routes.LOGIN) {
                    popUpTo(Routes.SPLASH)
                    launchSingleTop = true
                }
            }
            RegisterScreen(onNavigateToLogin = goToLogin, onRegisterSuccess = goToLogin)
        }

        // Sub-grafo: las 3 pantallas comparten el mismo ForgotPasswordViewModel
        navigation(startDestination = Routes.FORGOT_EMAIL, route = Routes.FORGOT_GRAPH) {
            composable(Routes.FORGOT_EMAIL) { entry ->
                val vm = forgotViewModel(entry, navController)
                val state by vm.uiState.collectAsStateWithLifecycle()
                ForgotEmailScreen(
                    viewModel = vm,
                    state = state,
                    onBack = { navController.popBackStack() },
                    onCodeSent = { navController.navigate(Routes.FORGOT_CODE) }
                )
            }
            composable(Routes.FORGOT_CODE) { entry ->
                val vm = forgotViewModel(entry, navController)
                val state by vm.uiState.collectAsStateWithLifecycle()
                VerifyCodeScreen(
                    viewModel = vm,
                    state = state,
                    onBack = { navController.popBackStack() },
                    onVerified = { navController.navigate(Routes.FORGOT_NEW_PASSWORD) }
                )
            }
            composable(Routes.FORGOT_NEW_PASSWORD) { entry ->
                val vm = forgotViewModel(entry, navController)
                val state by vm.uiState.collectAsStateWithLifecycle()
                NewPasswordScreen(
                    viewModel = vm,
                    state = state,
                    onBack = { navController.popBackStack() },
                    onSaved = {
                        navController.popBackStack(Routes.FORGOT_GRAPH, inclusive = true)
                    }
                )
            }
        }
    }
}

@Composable
private fun forgotViewModel(
    entry: NavBackStackEntry,
    navController: NavHostController
): ForgotPasswordViewModel {
    val parentEntry = remember(entry) { navController.getBackStackEntry(Routes.FORGOT_GRAPH) }
    return viewModel(parentEntry)
}
